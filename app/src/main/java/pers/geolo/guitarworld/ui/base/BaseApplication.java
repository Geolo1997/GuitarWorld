package pers.geolo.guitarworld.ui.base;

import android.app.Application;
import android.content.Context;
import org.litepal.LitePal;

import pers.geolo.guitarworld.http.HttpClient;
import pers.geolo.guitarworld.http.retrofit.RetrofitHandler;

public class BaseApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(getApplicationContext());

        HttpClient.init()
                .handler(new RetrofitHandler())
                .configure();
    }
}
