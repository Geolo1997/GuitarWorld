package pers.geolo.guitarworld.network;

import org.json.JSONObject;
import pers.geolo.guitarworld.model.User;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * @author 桀骜(Geolo)
 */
public interface UserService {

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
    @FormUrlEncoded
    @POST("saveMyProfile")
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
    @POST("attention")
    Call<ResponseBody<Void>> attention(@Field("username") String username);


}
