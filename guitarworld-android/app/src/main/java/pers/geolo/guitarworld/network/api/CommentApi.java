package pers.geolo.guitarworld.network.api;

import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.network.callback.ResponseBody;
import pers.geolo.guitarworld.network.param.CommentParam;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface CommentApi {

    @GET("comment")
    Call<ResponseBody<List<Comment>>> getComments(@QueryMap Map<String, Object> filter);

    @POST("comment")
    Call<ResponseBody<Void>> addComment(@Body Comment comment);

    @DELETE("comment")
    Call<ResponseBody<Void>> deleteComment(@QueryMap Map<String, Object> filter);

}
