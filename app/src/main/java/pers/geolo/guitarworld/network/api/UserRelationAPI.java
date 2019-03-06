package pers.geolo.guitarworld.network.api;

import pers.geolo.guitarworld.entity.UserRelation;
import pers.geolo.guitarworld.entity.UserRelationType;
import pers.geolo.guitarworld.network.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.util.List;

public interface UserRelationAPI {

    /**
     * 关注用户
     *
     * @param userRelation 被关注的用户名
     * @return
     */
    @POST("addRelation")
    Call<ResponseBody<Void>> addRelation(@Body UserRelation userRelation);

    /**
     * 取消关注用户
     *
     * @param userRelation 被取消关注的用户名
     * @return
     */
    @POST("removeRelation")
    Call<ResponseBody<Void>> removeRelation(@Body UserRelation userRelation);

    /**
     * 获取和其他用户的关系
     *
     * @param otherUsername 其他用户名
     * @return
     */
    @FormUrlEncoded
    @POST("getMyRelationTypeWith")
    Call<ResponseBody<UserRelationType>> getMyRelationTypeWith(@Field("otherUsername") String otherUsername);

    /**
     * 获取某用户粉丝
     *
     * @param username 该用户用户名
     * @return 该用户粉丝用户名列表
     */
    @FormUrlEncoded
    @POST("getFollowing")
    Call<ResponseBody<List<String>>> getFollowing(@Field("username") String username);

    /**
     * 获取某用户偶像
     *
     * @param username 该用户用户名
     * @return 该用户偶像用户名列表
     */
    @FormUrlEncoded
    @POST("getFollower")
    Call<ResponseBody<List<String>>> getFollower(@Field("username") String username);
}
