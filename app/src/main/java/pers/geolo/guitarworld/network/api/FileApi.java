package pers.geolo.guitarworld.network.api;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

import pers.geolo.guitarworld.network.ResponseBody;

public interface FileApi {

    @Multipart
    @POST("profilePicture")
    Call<ResponseBody<Void>> uploadProfilePicture(@Part MultipartBody.Part file);

    @Streaming
    @GET("profilePicture")
    Call<okhttp3.ResponseBody> getProfilePicture(@Query("username") String username);

    @Streaming
    @GET("20180815101229911.jpg")
    Call<okhttp3.ResponseBody> testGetPic();
}
