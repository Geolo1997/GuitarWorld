package pers.geolo.guitarworld.controller;

import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import org.microview.support.XmlViewController;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/9/8
 */
public abstract class BaseController extends XmlViewController {

    @Override
    public View createView(ViewGroup container) {
        View view = super.createView(container);
        ButterKnife.bind(this, view);
        return view;
    }
}
