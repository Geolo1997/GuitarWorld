package pers.geolo.guitarworld.network.api;

import pers.geolo.guitarworld.entity.Information;
import pers.geolo.guitarworld.network.callback.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/8/1
 */
public interface InformationApi {

    @GET("information")
    Call<ResponseBody<List<Information>>> getBannerInformation();
}
