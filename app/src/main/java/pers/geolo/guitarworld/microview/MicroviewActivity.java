package pers.geolo.guitarworld.microview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/9/5
 */
public abstract class MicroviewActivity extends AppCompatActivity {

    private ViewGroup rootContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootContainer = new FrameLayout(this);
        setContentView(rootContainer);
        ViewManager.setActivity(this);
        init();
    }

    public ViewGroup getRootContainer() {
        return rootContainer;
    }

    protected abstract void init();

    @Override
    public void onBackPressed() {
        ControllerStack controllerStack = ViewManager.getControllerStack(getRootContainer());
        if (controllerStack != null && controllerStack.size() != 0) {
            ViewController controller = controllerStack.get(controllerStack.size() - 1);
            controller.onBackPressed();
            if (controllerStack.size() == 0) {
                finish();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewManager.setActivity(null);
    }
}
