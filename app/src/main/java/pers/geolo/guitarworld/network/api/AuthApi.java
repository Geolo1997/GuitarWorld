package pers.geolo.guitarworld.network.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import pers.geolo.guitarworld.network.callback.ResponseBody;

public interface AuthApi {

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @param email    电子邮箱
     * @return 响应体
     */
    @FormUrlEncoded
    @POST("auth/register")
    Call<ResponseBody<Void>> register(@Field("username") String username, @Field("password") String password,
                                      @Field("email") String email, @Field("verifyCode") String verifyCode);

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 响应体
     */
    @FormUrlEncoded
    @POST("auth/login")
    Call<ResponseBody<Void>> login(@Field("username") String username, @Field("password") String password);


    /**
     * 注销登录
     *
     * @return 响应体
     */
    @POST("auth/logout")
    Call<ResponseBody<Void>> logout();
}
