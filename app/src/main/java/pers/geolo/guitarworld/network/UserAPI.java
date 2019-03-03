package pers.geolo.guitarworld.network;

import pers.geolo.guitarworld.entity.User;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * @author 桀骜(Geolo)
 */
public interface UserAPI {

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 响应体
     */
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody<Void>> login(@Field("username") String username, @Field("password") String password);


    /**
     * 注销登录
     *
     * @return 响应体
     */
    @GET("logout")
    Call<ResponseBody<Void>> logout();

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @param email    电子邮箱
     * @return 响应体
     */
    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody<Void>> register(@Field("username") String username, @Field("password") String password, @Field("email") String email);

    /**
     * 获取我的资料
     *
     * @return 响应体
     */
    @GET("getMyProfile")
    Call<ResponseBody<User>> getMyProfile();


    /**
     * 保存我的资料
     * @param user
     * @return
     */
    @POST("updateMyProfile")
    Call<ResponseBody<Void>> saveMyProfile(@Body User user);

    /**
     * 获取用户的公开资料
     *
     * @param username 用户名
     * @return 响应体
     */
    @FormUrlEncoded
    @POST("getPublicProfile/{username}")
    Call<ResponseBody<User>> getPublicProfile(@Path("username") String username);

    /**
     * 关注用户
     * @param username 被关注的用户
     * @return
     */
    @FormUrlEncoded
    @POST("following")
    Call<ResponseBody<Void>> following(@Field("username") String username);

    /**
     * 获取关注我的用户
     * @return
     */
    @FormUrlEncoded
    @GET
    Call<ResponseBody<List<User>>> getMyFollowing();


    // test

    /**
     * 获取所有注册用户
     * @return
     */
    @FormUrlEncoded
    @GET
    Call<ResponseBody<List<User>>> getAllUsers();


}
