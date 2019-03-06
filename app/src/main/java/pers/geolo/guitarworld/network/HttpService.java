package pers.geolo.guitarworld.network;

import pers.geolo.util.SingletonHolder;

public class HttpService {

    private static volatile HttpService instance;

    private HttpService() {
    }

    public static HttpService getInstance() {
        if (instance == null) {
            synchronized (HttpService.class) {
                if (instance == null) {
                    instance = new HttpService();
                }
            }
        }
        return instance;
    }

    private final APIFactory apiFactory = SingletonHolder.getInstance(APIFactory.class);

    public <T> T getAPI(Class<T> clazz) {
        return apiFactory.getAPI(clazz);
    }
}
