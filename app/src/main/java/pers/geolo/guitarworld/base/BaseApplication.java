package pers.geolo.guitarworld.base;

import android.app.Application;
import android.content.Context;
import org.litepal.LitePal;

public class BaseApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(context);
//        LitePal.deleteDatabase("GuitarWorld");
//        LitePal.getDatabase();

//        LitePal.deleteAll(LogInfo.class);
    }
}
