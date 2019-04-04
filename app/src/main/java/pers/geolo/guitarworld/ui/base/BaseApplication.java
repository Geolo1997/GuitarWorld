package pers.geolo.guitarworld.ui.base;

import android.app.Application;
import org.litepal.LitePal;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(getApplicationContext());
//        LitePal.deleteDatabase("GuitarWorld");
//        LitePal.getDatabase();

//        LitePal.deleteAll(LogInfo.class);
    }
}
