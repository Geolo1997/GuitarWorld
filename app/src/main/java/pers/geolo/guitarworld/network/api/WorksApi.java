package pers.geolo.guitarworld.network.api;

import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface WorksApi {

    @POST("works")
    Call<ResponseBody<Void>> publishWorks(@Body Works works);

    @DELETE("works/{id}")
    Call<ResponseBody<Void>> removeWorks(@Path("id") int id);

    @GET("works")
    Call<ResponseBody<List<Works>>> getWorksList(@Query("author") String author);

    @GET("works/{id}")
    Call<ResponseBody<Works>> getWorks(@Path("id") int id);

    @GET("works/all")
    Call<ResponseBody<List<Works>>> getAllWorks();
}
