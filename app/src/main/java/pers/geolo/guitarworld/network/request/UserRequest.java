package pers.geolo.guitarworld.network.request;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.network.ResponseBody;

/**
 * @author 桀骜(Geolo)
 * @date 2019-05-31
 */
public interface UserRequest {

    /**
     * 获取所有注册用户
     *
     * @return
     */
    @GET("user/all")
    Call<ResponseBody<List<User>>> getAllUsers();

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
}
