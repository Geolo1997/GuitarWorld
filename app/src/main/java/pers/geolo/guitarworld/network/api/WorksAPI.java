package pers.geolo.guitarworld.network.api;

import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface WorksAPI {

    @FormUrlEncoded
    @POST("getWorksList")
    Call<ResponseBody<List<Works>>> getWorksList(@Field("author") String author);

    @FormUrlEncoded
    @POST("getWorks")
    Call<ResponseBody<Works>> getWorks(@Field("id") int id);

    @FormUrlEncoded
    @POST("removeWorks")
    Call<ResponseBody<Void>> removeWorks(@Field("id") int id);

    @POST("publishWorks")
    Call<ResponseBody<Void>> publishWorks(@Body Works works);


    @GET("getAllWorks")
    Call<ResponseBody<List<Works>>> getAllWorks();
}
