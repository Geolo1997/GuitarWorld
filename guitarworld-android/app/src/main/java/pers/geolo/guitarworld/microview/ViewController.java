package pers.geolo.guitarworld.microview;

import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/30
 */
public interface ViewController extends Serializable {

    View createView(ViewGroup container);

    View getView();

    void initView(ViewParam viewParam);

    void onBackPressed();
}
