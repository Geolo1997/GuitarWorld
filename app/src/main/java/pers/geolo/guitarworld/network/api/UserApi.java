package pers.geolo.guitarworld.network.api;

import pers.geolo.guitarworld.entity.User;

import pers.geolo.guitarworld.network.callback.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * @author 桀骜(Geolo)
 */
public interface UserApi {

    /**
     * 获取我的资料
     *
     * @return 响应体
     */
    @GET("user")
    Call<ResponseBody<User>> getMyProfile();

    /**
     * 获取用户的公开资料
     *
     * @param username 用户名
     * @return 响应体
     */
    @GET("user/{username}")
    Call<ResponseBody<User>> getPublicProfile(@Path("username") String username);

    /**
     * 保存我的资料
     *
     * @param user
     * @return
     */
    @POST("user")
    Call<ResponseBody<Void>> updateMyProfile(@Body User user);

    /**
     * 获取某用户粉丝
     *
     * @param username 该用户用户名
     * @return 该用户粉丝用户名列表
     */
    @GET("user/{username}/following")
    Call<ResponseBody<List<User>>> getFollowing(@Path("username") String username);

    /**
     * 获取某用户偶像
     *
     * @param username 该用户用户名
     * @return 该用户偶像用户名列表
     */
    @GET("user/{username}/follower")
    Call<ResponseBody<List<User>>> getFollower(@Path("username") String username);



    // test

    /**
     * 获取所有注册用户
     *
     * @return
     */
    @GET("user/all")
    Call<ResponseBody<List<User>>> getAllUsers();
}
