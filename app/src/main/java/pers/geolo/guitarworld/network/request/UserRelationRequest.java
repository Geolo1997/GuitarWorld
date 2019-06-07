package pers.geolo.guitarworld.network.request;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

import pers.geolo.guitarworld.entity.UserRelation;
import pers.geolo.guitarworld.network.ResponseBody;

/**
 * @author 桀骜(Geolo)
 * @date 2019-05-31
 */
public interface UserRelationRequest {
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
}
