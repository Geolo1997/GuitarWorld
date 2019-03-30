package pers.geolo.guitarworld.network.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.network.ResponseBody;

public interface CommentApi {


    @POST("comment")
    Call<ResponseBody<Void>> addComment(@Body Comment comment);

    @DELETE("comment/{id}")
    Call<ResponseBody<Void>> removeComment(@Path("id") int id);

    @GET("comment/{id}")
    Call<ResponseBody<Comment>> getComment(@Path("id") int id);

    @GET("comment")
    Call<ResponseBody<List<Comment>>> listCommentOfWorks(@Query("worksId") int worksId);
}
