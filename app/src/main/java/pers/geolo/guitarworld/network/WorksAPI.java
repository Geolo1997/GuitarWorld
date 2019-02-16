package pers.geolo.guitarworld.network;

import pers.geolo.guitarworld.model.Works;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.util.List;

public interface WorksAPI {

    @FormUrlEncoded
    @POST("getWorksList")
    Call<ResponseBody<List<Works>>> getWorksList(@Field("author") String author);

    @FormUrlEncoded
    @POST("getWorks")
    Call<ResponseBody<Works>> getWorks(@Field("id") int id);

    @POST("publishWorks")
    Call<ResponseBody<Void>> publishWorks(@Body Works works);
}
