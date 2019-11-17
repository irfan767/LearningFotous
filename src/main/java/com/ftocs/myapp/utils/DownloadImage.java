package com.ftocs.myapp.utils;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public  class DownloadImage extends AsyncTask<String,Void,Boolean> {

    private ProgressDialog progressBar;
    private Context context;
    private boolean successful;
    private String error="";
    Intent intent;
    private File file;
    String dirpath;

    public DownloadImage(Context context){
        this.context=context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressBar = new ProgressDialog(context);
        progressBar.setMessage("Please wait...");
        progressBar.setCancelable(false);
        progressBar.show();

    }

    @Override
    protected Boolean doInBackground(String... params) {

        successful=false;
        URL downloadURL=null;
        HttpURLConnection connection=null;
        InputStream inputStream=null;
        FileOutputStream fileOutputStream=null;
         file=null;
        try {
            downloadURL=new URL(params[0]);
            connection= (HttpURLConnection) downloadURL.openConnection();
            inputStream=connection.getInputStream();

            file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/"+ Uri.parse(params[0]).getLastPathSegment());
            fileOutputStream=new FileOutputStream(file);
            int read;
            byte[] buffer=new byte[2048];

            while ((read = inputStream.read(buffer))!=-1){
                fileOutputStream.write(buffer,0,read);
            }
            dirpath=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/"+ Uri.parse(params[0]).getLastPathSegment();

            addImageToGallery(dirpath,context);
            successful=true;

        }catch (Exception e){
            error= e.getMessage();
        }
        finally {
            if (connection!=null){
                connection.disconnect();
            }
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return successful;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);


    }

    private static void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
}