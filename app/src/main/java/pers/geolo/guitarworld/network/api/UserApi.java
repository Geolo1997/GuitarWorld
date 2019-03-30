package pers.geolo.guitarworld.network.api;

import pers.geolo.guitarworld.entity.User;

import pers.geolo.guitarworld.network.ResponseBody;
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
    @GET("user/{username}")
    Call<ResponseBody<User>> getUserInfo(@Path("username") String username);


    /**
     * 保存我的资料
     *
     * @param user
     * @return
     */
    @POST("user")
    Call<ResponseBody<Void>> updateUserInfo(@Body User user);

    /**
     * 获取用户的公开资料
     *
     * @param username 用户名
     * @return 响应体
     */
    @FormUrlEncoded
    @POST("user/profile/{username}")
    Call<ResponseBody<User>> getPublicProfile(@Path("username") String username);

    // test

    /**
     * 获取所有注册用户
     *
     * @return
     */
    @GET("user/all")
    Call<ResponseBody<List<User>>> getAllUsers();


}
