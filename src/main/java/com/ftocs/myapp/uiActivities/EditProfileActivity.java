package com.ftocs.myapp.uiActivities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;


import com.ftocs.myapp.R;
import com.ftocs.myapp.dataModels.CityDM;
import com.ftocs.myapp.dataModels.LoginResponseDM;
import com.ftocs.myapp.retrofit.ApiClient;
import com.ftocs.myapp.retrofit.ApiInterface;
import com.ftocs.myapp.utils.Constants;
import com.bumptech.glide.Glide;
import com.daasuu.ahp.AnimateHorizontalProgressBar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {


    ApiInterface apiInterface;
    Call<LoginResponseDM> callCustomerImage;

    Button btn_signup;
    EditText et_email, et_pass, et_number, et_name, et_address;
    Spinner spinner_code, spinner_city, spinner_typeOfAccount;
    ProgressBar progressBar;
    ImageView img_back;
    boolean gender = false;

    Call<List<CityDM>> callCity;
    Call<LoginResponseDM> callRegister;
    Call<LoginResponseDM> callGetProfile;
    ArrayList<String> stringArrayListCityName;
    ArrayList<String> stringArrayListUserType;
    String selectedCityName;
    String selectedUserType;
    SharedPreferences prefLogIn;
    SharedPreferences.Editor editor;
    String userId;

    HashMap<String, String> intituteHashMap = new HashMap<>();


    List<String> codeList;
    private String selectedCode;
    private int selectedCityId;
    ArrayAdapter<String> cityArrayadapter ;
    ArrayAdapter<String> userTypArrayadapter;
    ArrayAdapter<String> codeadapter;

    String prefUserId;
    public static  int count = 0;
    private String oldNumber;
    private String oldEmail;
    private String prefUseType;
    private CircleImageView profile_image;
    Uri imageUri1 = null;
    public MultipartBody.Part body1;
    ArrayList<String> dirPath = new ArrayList<>();

    ScrollView scroll_main;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile );
        img_back = findViewById(R.id.img_back);
        btn_signup = findViewById(R.id.btn_signup);
        progressBar = findViewById(R.id.progressBar);

        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        et_number = findViewById(R.id.et_number);
        et_name = findViewById(R.id.et_name);
        et_address = findViewById(R.id.et_address);
        scroll_main = findViewById(R.id.scroll_main);

        spinner_code = findViewById(R.id.spinner_code);
        spinner_city = findViewById(R.id.spinner_city);
        profile_image = findViewById(R.id.profile_image);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        prefLogIn = getSharedPreferences(Constants.MY_PREF_LOGIN, Context.MODE_PRIVATE);
       prefUserId =prefLogIn.getString(Constants.PREF_USER_ID_KEY,Constants.NULL);

        requestMultiplePermissions();


        sendingNetworkRequestCity();


        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder pictureDialog = new AlertDialog.Builder(EditProfileActivity.this);
                pictureDialog.setTitle("Select Action");
                String[] pictureDialogItems = {
                        "Select photo from gallery",
                        "Capture photo from camera"};
                pictureDialog.setItems(pictureDialogItems,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        imageUri1 = null;
                                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                        photoPickerIntent.setType("image/*");
                                        startActivityForResult(photoPickerIntent, 1);
                                        break;
                                    case 1:
                                        String fileName = "new-photo-name.jpg";
                                        ContentValues values = new ContentValues();
                                        values.put(MediaStore.Images.Media.TITLE, fileName);
                                        values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
                                        imageUri1 = EditProfileActivity.this.getContentResolver().insert(
                                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri1);
                                        startActivityForResult(intent, 1);
                                        break;
                                }
                            }
                        });
                pictureDialog.show();
            }
        });

        // call login activity
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });
        codeList = new ArrayList<>();
        codeList();
       codeadapter = new ArrayAdapter<>(EditProfileActivity.this, R.layout.spinner_item, codeList);
        codeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_code.setAdapter(codeadapter);

        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCityName = spinner_city.getItemAtPosition(position).toString();
                for (String key : intituteHashMap.keySet()) {

                    if (intituteHashMap.get(key).equals(selectedCityName)) {
                        selectedCityId = Integer.parseInt(key);


                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_code.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCode = spinner_code.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String password = et_pass.getText().toString();
                String phone1 = et_number.getText().toString();
                String address = et_address.getText().toString();

                if (et_name.getText().toString().isEmpty()) {
                    et_name.setError("Name is Empty");
                }

                if (et_pass.getText().toString().isEmpty()) {
                    et_pass.setError("Password is empty");
                }
                if (et_number.getText().toString().isEmpty()) {
                    et_number.setError("Number is empty");
                }
                if (et_address.getText().toString().isEmpty()) {
                    et_address.setError("Address is empty");
                }
                if (et_number.getText().length() > 7 || et_number.getText().length() < 7) {
                    et_number.setError("Number is invalid");
                }


                if (et_name.getText().toString().isEmpty()
                        || et_pass.toString().isEmpty() || et_number.getText().toString().isEmpty() || et_number.getText().length() > 7 || et_number.getText().length() < 7 || et_address.getText().toString().isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "Fill the empty fields", Toast.LENGTH_SHORT).show();
                } else {


                    final ProgressDialog progressDialog = new ProgressDialog(EditProfileActivity.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Saving Profile Data..");
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);

                    String isemailchange;
                    String isnumberchange;

                    if (oldNumber.equalsIgnoreCase(et_number.getText().toString()))
                    {
                        isnumberchange= "false";
                    }
                    else {
                        isnumberchange= "true";
                    }

                    if (oldEmail.equalsIgnoreCase(et_email.getText().toString()))
                    {
                        isemailchange= "false";
                    }
                    else {
                        isemailchange= "true";
                    }


                    callRegister = apiInterface.upDateUser(Integer.parseInt(prefUserId),isemailchange,isnumberchange,name, email, password, address, selectedCode, phone1, gender, selectedCityId);
                    callRegister.enqueue(new Callback<LoginResponseDM>() {
                        @Override
                        public void onResponse(Call<LoginResponseDM> call, Response<LoginResponseDM> response) {
                            LoginResponseDM login = response.body();




                            if (response.code() == 200) {


                                Toast.makeText(EditProfileActivity.this, "Record Saved", Toast.LENGTH_SHORT).show();

                                editor = prefLogIn.edit();
                                editor.putString(Constants.PREF_USER_ID_KEY, String.valueOf(login.getID()));
                                editor.putString(Constants.PREF_USER_NAME, login.getFullName());
                                editor.putString(Constants.PREF_USER_EMAIL, login.getEmail());
                                editor.putString(Constants.PREF_USER_PASSWORD, login.getPassword());
                                editor.putString(Constants.PREF_USER_ADDRESS, login.getAddress());
                                editor.putString(Constants.PREF_USER_ContactNo, login.getContactNo());
                                editor.putString(Constants.PREF_USER_user_Type, prefUseType);
                                editor.putString(Constants.PREF_USER_City, String.valueOf(login.getCityID()));
                                editor.commit();
                                editor.apply();
                                progressDialog.dismiss();
                                setImage();

                            } else if (response.code() == 400) {

                                JSONObject jsonObject = null;
                                String userMessage = "";
                                try {
                                    jsonObject = new JSONObject(response.errorBody().string());
                                    userMessage = jsonObject.getString("Message");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                Toast.makeText(EditProfileActivity.this, userMessage, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();


                            } else {
                                Toast.makeText(EditProfileActivity.this, "Somehing went wrong try again", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();


                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponseDM> call, Throwable t) {
                            Toast.makeText(EditProfileActivity.this, "Network Problem", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();

                        }
                    });

                }




            }

        });
    }

    public void codeList() {
        codeList.add("0300");
        codeList.add("0301");
        codeList.add("0302");
        codeList.add("0303");
        codeList.add("0304");
        codeList.add("0305");
        codeList.add("0306");
        codeList.add("0307");
        codeList.add("0308");
        codeList.add("0310");
        codeList.add("0311");
        codeList.add("0312");
        codeList.add("0313");
        codeList.add("0314");
        codeList.add("0315");
        codeList.add("0316");
        codeList.add("0317");
        codeList.add("0318");
        codeList.add("0320");
        codeList.add("0321");
        codeList.add("0322");
        codeList.add("0323");
        codeList.add("0324");
        codeList.add("0325");
        codeList.add("0326");
        codeList.add("0327");
        codeList.add("0328");
        codeList.add("0330");
        codeList.add("0331");
        codeList.add("0332");
        codeList.add("0333");
        codeList.add("0334");
        codeList.add("0335");
        codeList.add("0336");
        codeList.add("0337");
        codeList.add("0338");
        codeList.add("0340");
        codeList.add("0341");
        codeList.add("0342");
        codeList.add("0343");
        codeList.add("0344");
        codeList.add("0345");
        codeList.add("0346");
        codeList.add("0347");
        codeList.add("0348");
    }

    public void sendingNetworkRequestCity() {
        callCity = apiInterface.getMeCity();
        callCity.enqueue(new Callback<List<CityDM>>() {
            @Override
            public void onResponse(Call<List<CityDM>> call, Response<List<CityDM>> response) {
                if (response.isSuccessful()) {
                    List<CityDM> cityDMs = response.body();

                    if (cityDMs == null || cityDMs.size() == 0) {
//                        progressDialog.dismiss();
//                        linearLayout.setVisibility(View.GONE);
                        Toast.makeText(EditProfileActivity.this, "City Not Found ", Toast.LENGTH_SHORT).show();

                    } else {
//                        progressDialog.dismiss();


                        stringArrayListCityName = new ArrayList<>();
                        for (int i = 0; i < cityDMs.size(); i++) {

                            CityDM classTypeDataModel = cityDMs.get(i);
                            String cityName = classTypeDataModel.getCityName();
                            stringArrayListCityName.add(cityName);
                            intituteHashMap.put(String.valueOf(classTypeDataModel.getID()), classTypeDataModel.getCityName());

                        }
                        count++;
                        setDataCitySpinner(stringArrayListCityName);
                        Constants.stringArrayListCity.addAll(stringArrayListCityName);
                      profileData();


                    }
                } else {
                    Toast.makeText(EditProfileActivity.this, "Something went wrong ", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<CityDM>> call, Throwable t) {

            }
        });

    }

    private void setDataCitySpinner(ArrayList<String> stringArrayListCityName) {
    cityArrayadapter = new ArrayAdapter<String>(EditProfileActivity.this, android.R.layout.simple_dropdown_item_1line, stringArrayListCityName);
        spinner_city.setAdapter(cityArrayadapter);
    }



    private void setDataUserTypeSpinner(ArrayList<String> stringArrayListCityName) {
   userTypArrayadapter = new ArrayAdapter<String>(EditProfileActivity.this, android.R.layout.simple_dropdown_item_1line, stringArrayListCityName);
        spinner_typeOfAccount.setAdapter(userTypArrayadapter);
    }




   public void getProfileData(int id)
   {

       final ProgressDialog progressDialog = new ProgressDialog(EditProfileActivity.this);
       progressDialog.setIndeterminate(true);
       progressDialog.setMessage("Getting your profile..");
       progressDialog.show();
       progressDialog.setCancelable(false);
       progressDialog.setCanceledOnTouchOutside(false);

       callGetProfile = apiInterface.getProfile(id);
       callGetProfile.enqueue(new Callback<LoginResponseDM>() {
           @Override
           public void onResponse(Call<LoginResponseDM> call, Response<LoginResponseDM> response) {



               LoginResponseDM login = response.body();




               if (response.code() == 200) {

                   oldNumber = login.getContactNo();
                  oldEmail = login.getEmail();

                   et_name.setText(login.getFullName());
                   et_address.setText(login.getAddress());
                   et_pass.setText(login.getPassword());
                   et_email.setText(login.getEmail());
                   et_number.setText(login.getContactNo());

                   Glide.with(EditProfileActivity.this).load(login.getPhoto())
                           .thumbnail(0.5f)
                           .into(profile_image);



                   String cityName =intituteHashMap.get(String.valueOf(login.getCityID()));
                   int spinnerPositionCity = cityArrayadapter.getPosition(cityName);
                   spinner_city.setSelection(spinnerPositionCity);

                   int spinnerPositionCode = codeadapter.getPosition(login.getExt());
                   spinner_code.setSelection(spinnerPositionCode);



                   count = 0;

                   new DownloadImageTask(login.getPhoto()).execute();

                   progressDialog.dismiss();




               } else if (response.code() == 400) {

                   JSONObject jsonObject = null;
                   String userMessage = "";
                   try {
                       jsonObject = new JSONObject(response.errorBody().string());
                       userMessage = jsonObject.getString("Message");

                   } catch (JSONException e) {
                       e.printStackTrace();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }

                   count = 0;


                   Toast.makeText(EditProfileActivity.this, userMessage, Toast.LENGTH_SHORT).show();
                   progressDialog.dismiss();



               } else {
                   Toast.makeText(EditProfileActivity.this, "Somehing went wrong try again", Toast.LENGTH_SHORT).show();
                   progressDialog.dismiss();

                   count = 0;



               }
           }

           @Override
           public void onFailure(Call<LoginResponseDM> call, Throwable t) {

               Toast.makeText(EditProfileActivity.this, "Network Problem", Toast.LENGTH_LONG).show();
               progressDialog.dismiss();

               count = 0;



           }
       });
   }



public void profileData()
{
    if (count==1) {
        getProfileData(Integer.parseInt(prefUserId));
    }

}
    public void setImage()
    {

        final ProgressDialog progressDialog = new ProgressDialog(EditProfileActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Saving your Profile Picture..");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        callCustomerImage = apiInterface.setCustomerImage(Integer.parseInt(prefUserId),body1);
        callCustomerImage.enqueue(new Callback<LoginResponseDM>() {
            @Override
            public void onResponse(Call<LoginResponseDM> call, Response<LoginResponseDM> response) {
                LoginResponseDM login = response.body();




                if (response.code() == 200) {
                    progressBar.setVisibility(View.GONE);


                    Toast.makeText(EditProfileActivity.this, "Image Saved", Toast.LENGTH_SHORT).show();
progressDialog.dismiss();

                    Intent intent = new Intent(EditProfileActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);


                } else if (response.code() == 400) {

                    JSONObject jsonObject = null;
                    String userMessage = "";
                    try {
                        jsonObject = new JSONObject(response.errorBody().string());
                        userMessage = jsonObject.getString("Message");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(EditProfileActivity.this, userMessage, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();


                } else {
                    Toast.makeText(EditProfileActivity.this, "Somehing went wrong try again", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();


                }
            }

            @Override
            public void onFailure(Call<LoginResponseDM> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Network Problem", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();

            }
        });

    }
    private void requestMultiplePermissions() {
        Dexter.withActivity(EditProfileActivity.this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();

                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==  EditProfileActivity.this.RESULT_CANCELED) {
            return;
        }
        if (resultCode == EditProfileActivity.this.RESULT_OK) {

            if (requestCode == 1) {
                try {
                    final Uri imageUri;
                    File file;

                    if (imageUri1 != null) {
                        imageUri = imageUri1;
                        file = convertImageUriToFile(imageUri, EditProfileActivity.this);
                    } else {
                        imageUri = data.getData();
                        file = new File(getRealPathFromURI(imageUri, EditProfileActivity.this));
                    }
                    final InputStream imageStream = EditProfileActivity.this.getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    profile_image.setImageBitmap(selectedImage);
                 /*   Glide.with(EditProfileActivity.this)
                            .load(selectedImage)
                            .into(profile_image);*/
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    body1 = MultipartBody.Part.createFormData("image", file.getName(), requestFile);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(EditProfileActivity.this, "File not found ", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    profile_image.setImageResource(R.drawable.ic_person);
                }

            }
        }
    }

    private String getRealPathFromURI(Uri uri, Context context) {

        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = context.getContentResolver().query(uri, projection, null,
                null, null);
        if (cursor == null)
            return null;
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        if (cursor.moveToFirst()) {
            String s = cursor.getString(column_index);
            cursor.close();
            return s;
        }
        // cursor.close();
        return null;
    }

    public static File convertImageUriToFile(Uri imageUri, Activity activity) {
        Cursor cursor1 = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.ORIENTATION};
            cursor1 = activity.managedQuery(imageUri,
                    proj, // Which columns to return
                    null,       // WHERE clause; which rows to return (all rows)
                    null,       // WHERE clause selection arguments (none)
                    null); // Order-by clause (ascending by name)
            int file_ColumnIndex = cursor1.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            int orientation_ColumnIndex = cursor1.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);
            if (cursor1.moveToFirst()) {
                String orientation = cursor1.getString(orientation_ColumnIndex);
                return new File(cursor1.getString(file_ColumnIndex));
            }
            return null;
        } catch (Exception e) {
//            if (cursor1 != null) {
//                cursor1.close();
//            }
            return null;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, String> {

        String url ;
        public DownloadImageTask(String url) {
            this.url = url;
        }

        protected String doInBackground(String... urls) {

            ArrayList<String> strings = new ArrayList();
            strings.add(url);

            for (int i = 0 ;i< strings.size();i++) {


                URL downloadURL = null;
                HttpURLConnection connection = null;
                InputStream inputStream = null;
                FileOutputStream fileOutputStream = null;
                File file;

                file = null;
                try {
                    downloadURL = new URL(strings.get(i));
                    connection = (HttpURLConnection) downloadURL.openConnection();
                    inputStream = connection.getInputStream();

                    file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + Uri.parse(strings.get(i)).getLastPathSegment());
                    fileOutputStream = new FileOutputStream(file);
                    int read;
                    byte[] buffer = new byte[2048];

                    while ((read = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, read);
                    }
                    dirPath.add(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + Uri.parse(strings.get(i)).getLastPathSegment());


                } catch (Exception e) {
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return null;
        }
        protected void onPostExecute(String result) {
            Log.d("", "onPostExecute: "+result);

            scroll_main.setVisibility(View.VISIBLE);

            progressBar.setVisibility(View.GONE);

            if (dirPath!=null && dirPath.size()>0)
            {
                File file =  new File(dirPath.get(0));
                RequestBody requestFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                body1 = MultipartBody.Part.createFormData("image", file.getName(), requestFile3);
            }


        }
    }
}

