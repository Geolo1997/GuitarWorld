package pers.geolo.guitarworld.network.api;

import pers.geolo.guitarworld.entity.User;

import pers.geolo.guitarworld.network.ResponseBody;
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
    Call<ResponseBody<Void>> register(@Field("username") String username, @Field("password") String password, @Field(
            "email") String email);

    /**
     * 获取我的资料
     *
     * @return 响应体
     */
    @GET("getMyProfile")
    Call<ResponseBody<User>> getMyProfile();


    /**
     * 保存我的资料
     *
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

    // test

    /**
     * 获取所有注册用户
     *
     * @return
     */
    @GET("getAllUsers")
    Call<ResponseBody<List<User>>> getAllUsers();


}
