package pers.geolo.guitarworld.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFatory {

    private Retrofit retrofit;

    public ServiceFatory() {
        String baseUrl = "http://localhost:8080";
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public <T> T getService(Class<T> clazz) {
        return retrofit.create(clazz);
    }

}
