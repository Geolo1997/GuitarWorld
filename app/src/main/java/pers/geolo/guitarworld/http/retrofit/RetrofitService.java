package pers.geolo.guitarworld.http.retrofit;

import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-06
 */
public interface RetrofitService {
    @GET
    Call<String> get(@Url String url, @HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, Object> params);

    @POST
    Call<String> postRaw(@Url String url, @HeaderMap Map<String, String> headers, @Body RequestBody body);

    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, Object> params);

    @PUT
    Call<String> putRaw(@Url String url, @HeaderMap Map<String, String> headers, @Body RequestBody body);

    @DELETE
    Call<String> delete(@Url String url, @HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> params);

    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url, @HeaderMap Map<String, String> headers, @QueryMap Map<String,
            Object> params);

    @Multipart
    @POST
    Call<String> upload(@Url String url, @HeaderMap Map<String, String> headers, @Part MultipartBody.Part file);
}
