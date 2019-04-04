package pers.geolo.guitarworld.network.api;

import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.*;

import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.ResponseBody;

public interface WorksApi {

    @POST("works")
    Call<ResponseBody<Void>> publishWorks(@Body Works works);

    @DELETE("works")
    Call<ResponseBody<Void>> removeWorks(@QueryMap Map<String, Object> filter);

    @GET("works")
    Call<ResponseBody<List<Works>>> getWorks(@QueryMap Map<String, Object> filter);
}
