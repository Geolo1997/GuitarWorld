package pers.geolo.guitarworld.network.request;

import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.*;

import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.ResponseBody;

/**
 * @author 桀骜(Geolo)
 * @date 2019-05-31
 */
public interface WorksRequest {

    @POST("works")
    Call<ResponseBody<Void>> publishWorks(@Body Works works);

    @GET("works")
    Call<ResponseBody<List<Works>>> getWorks(@QueryMap Map<String, Object> filter);

    @DELETE("works")
    Call<ResponseBody<Void>> removeWorks(@QueryMap Map<String, Object> filter);
}
