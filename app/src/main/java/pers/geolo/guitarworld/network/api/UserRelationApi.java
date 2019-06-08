package pers.geolo.guitarworld.network.api;

import pers.geolo.guitarworld.entity.UserRelation;
import pers.geolo.guitarworld.entity.UserRelationType;
import pers.geolo.guitarworld.network.callback.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UserRelationApi {

    /**
     * 关注用户
     *
     * @param userRelation 被关注的用户名
     * @return
     */
    @POST("relation")
    Call<ResponseBody<Void>> addRelation(@Body UserRelation userRelation);

    /**
     * 取消关注用户
     *
     * @param userRelation 被取消关注的用户名
     * @return
     */
    @HTTP(method = "DELETE", path = "relation", hasBody = true)
    Call<ResponseBody<Void>> removeRelation(@Body UserRelation userRelation);

    /**
     * 获取和其他用户的关系
     *
     * @param otherUsername 其他用户名
     * @return
     */
    @GET("relation/with")
    Call<ResponseBody<UserRelationType>> getMyRelationTypeWith(@Query("otherUsername") String otherUsername);

    /**
     * 获取某用户粉丝
     *
     * @param username 该用户用户名
     * @return 该用户粉丝用户名列表
     */
    @GET("relation/{username}/following")
    Call<ResponseBody<List<String>>> getFollowing(@Path("username") String username);

    /**
     * 获取某用户偶像
     *
     * @param username 该用户用户名
     * @return 该用户偶像用户名列表
     */
    @GET("relation/{username}/follower")
    Call<ResponseBody<List<String>>> getFollower(@Path("username") String username);
}
