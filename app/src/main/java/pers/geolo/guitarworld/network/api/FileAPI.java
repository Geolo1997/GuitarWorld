package pers.geolo.guitarworld.network.api;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import pers.geolo.guitarworld.network.ResponseBody;

public interface FileAPI {

    @Multipart
    @POST("uploadProfilePicture")
    Call<ResponseBody<Void>> uploadProfilePicture(@Part MultipartBody.Part file);
}
