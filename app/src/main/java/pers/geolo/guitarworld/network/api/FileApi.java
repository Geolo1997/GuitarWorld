package pers.geolo.guitarworld.network.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.Query;

public interface FileApi {

    @Multipart
    @GET("file")
    Call<ResponseBody> getFile(@Query("filePath") String filePath);
}
