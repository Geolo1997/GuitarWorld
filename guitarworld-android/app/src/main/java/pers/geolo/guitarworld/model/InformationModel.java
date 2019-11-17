package pers.geolo.guitarworld.model;

import pers.geolo.guitarworld.entity.DataCallback;
import pers.geolo.guitarworld.entity.Information;
import pers.geolo.guitarworld.network.HttpClient;
import pers.geolo.guitarworld.network.api.InformationApi;

import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/8/1
 */
public class InformationModel {

    private InformationApi informationApi = HttpClient.getService(InformationApi.class);

    public void getBannerInformation(DataCallback<List<Information>> listener) {
        informationApi.getBannerInformation().enqueue(new pers.geolo.guitarworld.network.callback.DataCallback(listener));
    }
}
