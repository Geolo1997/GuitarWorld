package pers.geolo.guitarworld.network.api;

import pers.geolo.guitarworld.entity.Music;
import pers.geolo.guitarworld.entity.MusicScore;
import pers.geolo.guitarworld.network.callback.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import java.util.HashMap;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/11
 */
public interface MusicApi {

    @GET("music")
    Call<ResponseBody<List<Music>>> getMusicList(@QueryMap HashMap<String, Object> queryMap);

    @GET("music/score")
    Call<ResponseBody<List<MusicScore>>> getMusicScore(@QueryMap HashMap<String, Object> queryMap);

    @GET("music/score/image")
    Call<ResponseBody<List<String>>> getMusicScoreImages(@QueryMap HashMap<String, Object> queryMap);
}
