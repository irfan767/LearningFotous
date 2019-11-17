package com.ftocs.myapp.retrofit;

import com.ftocs.myapp.dataModels.AboutUsDm;
import com.ftocs.myapp.dataModels.CategoryDM;
import com.ftocs.myapp.dataModels.CityDM;
import com.ftocs.myapp.dataModels.ContactUsDm;
import com.ftocs.myapp.dataModels.DashboardDM;
import com.ftocs.myapp.dataModels.OrderObj;
import com.ftocs.myapp.dataModels.LoginResponseDM;
import com.ftocs.myapp.dataModels.OrderHistoryDM;
import com.ftocs.myapp.dataModels.ProductInfoDM;
import com.ftocs.myapp.dataModels.ProductListDM;
import com.ftocs.myapp.dataModels.SizeDM;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("SignIn")
    Call<LoginResponseDM> logInCall(
            @Query("Number") String contact,
            @Query("Email") String Email,
            @Query("Password") String password);

    @GET("ContactUs")
    Call<ContactUsDm> getContactUs();

    @GET("aboutus")
    Call<AboutUsDm> getAboutUs();

    @GET("bankdetail")
    Call<AboutUsDm> getPaymentInfo();

    @POST("forgetpassword")
    Call<LoginResponseDM> callForgetPassword(
     @Query("ext") String ext,
     @Query("number") String number
    );


    @GET("tblCities")
    Call<List<CityDM>> getMeCity();

    @FormUrlEncoded
    @POST("RequestForm")
    Call<LoginResponseDM> requestForm(@Field("Name") String memberName,
                                      @Field("Email") String email, @Field("AreainAcre") String AreainAcre,
                                      @Field("BusinessAddress") String Address, @Field("Ext") String Ext, @Field("Number") String phoneNo,
                                      @Field("CityID") int CityID);

    @GET("GetProfile/{id}")
    Call<LoginResponseDM> getProfile(@Path("id") int id);

    @FormUrlEncoded
    @POST("signup")
    Call<LoginResponseDM> registerUser(@Field("memberName") String memberName,
                                       @Field("email") String email, @Field("password") String password,
                                       @Field("Address") String Address, @Field("Ext") String Ext, @Field("phoneNo") String phoneNo,
                                       @Field("gender") boolean gender, @Field("CityID") int CityID);
    @FormUrlEncoded
    @PUT("signup/{id}")
    Call<LoginResponseDM> upDateUser(@Path("id") int id, @Query("isemailchange") String isemailchange, @Query("isnumberchange") String isnumberchange,
                                     @Field("memberName") String UserName,
                                     @Field("email") String Email, @Field("password") String Password,
                                     @Field("Address") String Address, @Field("Ext") String Ext, @Field("phoneNo") String ContactNo,
                                     @Field("gender") boolean gender, @Field("CityID") int CityID);

    @GET("Category")
    Call<CategoryDM> getProductCategories();

    @GET("Catalog")
    Call<ProductListDM> getProductList(
            @Query("CategoryID") int CategoryID
    );
    @GET("Catalog/{id}")
    Call<ProductInfoDM> getProductInfo(@Path("id") int id);

    @Multipart
    @POST("CustomerImage/{id}")
    Call<LoginResponseDM> setCustomerImage(@Path("id") int id,
                                         @Part MultipartBody.Part image);

    @GET("dashboard/{id}")
    Call<DashboardDM> getdashboard(@Path("id") int id);

    @GET("Sizes")
    Call<SizeDM> getSizes(
            @Query("ProductID") int ProductID);



    @POST("Orders/PostOrderData")
    Call<LoginResponseDM> userOrder(@Body OrderObj ListOrderProducts);



    @GET("Orders/{id}")
    Call<OrderHistoryDM> getOrders(@Path("id") int id);



/*


    @GET("api/tblCities")
    Call<List<CityDM>> getMeCity();


    @GET("api/UserTypes")
    Call<List<UserTypesDM>> getMeUserType();

    @GET("forgotpassword")
    Call<LoginResponseDM> callForgetPassword(

            @Query("Email") String Email
    );


    @FormUrlEncoded
    @POST("api/signup")
    Call<LoginResponseDM> registerUser(@Field("FullName") String FullName, @Field("UserName") String UserName,
                                       @Field("Email") String Email, @Field("Password") String Password,
                                       @Field("Address") String Address, @Field("Ext") String Ext, @Field("ContactNo") String ContactNo,
                                       @Field("user_Type") int user_Type, @Field("City") String City);



    @FormUrlEncoded
    @POST("api/signup/{id}")
    Call<LoginResponseDM> upDateUser(@Path("id") int id, @Query("isemailchange") String isemailchange, @Query("isnumberchange") String isnumberchange, @Field("FullName") String FullName, @Field("UserName") String UserName,
                                     @Field("Email") String Email, @Field("Password") String Password,
                                     @Field("Address") String Address, @Field("Ext") String Ext, @Field("ContactNo") String ContactNo,
                                     @Field("user_Type") int user_Type, @Field("City") String City);
*/


}
