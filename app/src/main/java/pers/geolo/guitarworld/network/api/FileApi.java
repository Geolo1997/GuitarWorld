package pers.geolo.guitarworld.network.api;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

import pers.geolo.guitarworld.network.ResponseBody;

public interface FileApi {

    @Multipart
    @POST("avatar")
    Call<ResponseBody<Void>> uploadProfilePicture(@Part MultipartBody.Part file);

    @Multipart
    @POST("picture")
    Call<ResponseBody<String>> uploadPicture(@Part MultipartBody.Part file);

    @Streaming
    @GET("avatar")
    Call<okhttp3.ResponseBody> getAvatar(@Query("username") String username);

    @Streaming
    @GET("20180815101229911.jpg")
    Call<okhttp3.ResponseBody> testGetPic();

    @Streaming
    @GET("image")
    Call<okhttp3.ResponseBody> getImage(@Query("imagePath") String imagePath);
}
