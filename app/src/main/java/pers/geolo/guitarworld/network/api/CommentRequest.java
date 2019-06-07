package pers.geolo.guitarworld.network.api;

import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.*;

import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.network.ResponseBody;

public interface CommentRequest {

    @POST("comment")
    Call<ResponseBody<Void>> addComment(@Body Comment comment);

    @DELETE("comment")
    Call<ResponseBody<Void>> removeComment(@QueryMap Map<String, Object> filter);

    @GET("comment")
    Call<ResponseBody<List<Comment>>> getComments(@QueryMap Map<String, Object> filter);
}
