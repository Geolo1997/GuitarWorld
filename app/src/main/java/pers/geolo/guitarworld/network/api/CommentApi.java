package pers.geolo.guitarworld.network.api;

import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.network.ResponseBody;

public interface CommentApi {

    @POST("comment")
    Call<ResponseBody<Void>> addComment(@Body Comment comment);

    @DELETE("comment")
    Call<ResponseBody<Void>> removeComment(@QueryMap HashMap<String, Object> filter);

    @GET("comment")
    Call<ResponseBody<List<Comment>>> getComments(@QueryMap HashMap<String, Object> filter);
}
