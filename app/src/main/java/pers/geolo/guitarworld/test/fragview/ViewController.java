package pers.geolo.guitarworld.test.fragview;

import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/30
 */
public interface ViewController {

    View getView();

    View createView();

    void init(Object param);

    void setContainer(ViewGroup container);
}
