package pers.geolo.guitarworld.model;

import java.util.List;
import java.util.Map;

import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.HttpClient;
import pers.geolo.guitarworld.network.api.WorksApi;
import pers.geolo.guitarworld.network.callback.DataCallback;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-08
 */
public class WorksModel {

    private static WorksApi worksApi = HttpClient.getService(WorksApi.class);

    public static void publishWorks(Works works, DataListener<Void> listener) {
        worksApi.publishWorks(works).enqueue(new DataCallback<Void>(listener) {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onReturn(aVoid);
            }
        });
    }

    public static void getWorks(Map<String, Object> filter, DataListener<List<Works>> listener) {
        worksApi.getWorks(filter).enqueue(new DataCallback<List<Works>>(listener) {
            @Override
            public void onSuccess(List<Works> worksList) {
                listener.onReturn(worksList);
            }
        });
    }

    public static void deleteWorks(Map<String, Object> filter, DataListener<Void> listener) {
        worksApi.deleteWorks(filter).enqueue(new DataCallback<Void>(listener) {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onReturn(aVoid);
            }
        });
    }
}
