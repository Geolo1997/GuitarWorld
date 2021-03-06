package pers.geolo.guitarworld.network.api;

import okhttp3.MultipartBody;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.callback.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface WorksApi {

    @GET("works")
    Call<ResponseBody<List<Works>>> getWorks(@QueryMap Map<String, Object> queryMap);

    @POST("works")
    Call<ResponseBody<Void>> publishWorks(@Body Works works);

    @DELETE("works")
    Call<ResponseBody<Void>> deleteWorks(@QueryMap Map<String, Object> queryMap);

    @Multipart
    @POST("works/image")
    Call<ResponseBody<String>> uploadImage(@Part MultipartBody.Part image);

    @POST("works/like")
    Call<ResponseBody<Void>> likeWorks(@Query("username") String username, @Query("worksId") int worksId);

    @DELETE("works/like")
    Call<ResponseBody<Void>> cancelLikeWorks(@Query("username") String username, @Query("worksId") int worksId);
}
