package pers.geolo.guitarworld.network.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.network.ResponseBody;

public interface CommentAPI {


    @POST("addComment")
    Call<ResponseBody<Void>> addComment(@Body Comment comment);

    @FormUrlEncoded
    @POST("removeComment")
    Call<ResponseBody<Void>> removeComment(@Field("id") int id);

    @FormUrlEncoded
    @POST("getComment")
    Call<ResponseBody<Comment>> getComment(@Field("id") int id);

    @FormUrlEncoded
    @POST("listCommentOfWorks")
    Call<ResponseBody<List<Comment>>> listCommentOfWorks(@Field("worksId") int worksId);
}
