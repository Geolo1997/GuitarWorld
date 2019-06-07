package pers.geolo.guitarworld.model;

import pers.geolo.guitarworld.constant.HttpConstants;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.http.HttpClient;
import pers.geolo.guitarworld.http.HttpMethod;
import pers.geolo.guitarworld.http.callback.ISuccess;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-08
 */
public class WorksModel {

    public static void publishWorks(Works works, ISuccess<Void> iSuccess) {
        HttpClient.newRequest()
                .url(HttpConstants.WORKS)
                .method(HttpMethod.POST)
                .body(works)
                .success(iSuccess)
                .execute();
    }
}
