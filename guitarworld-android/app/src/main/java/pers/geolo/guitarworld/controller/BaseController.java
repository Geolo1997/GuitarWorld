package pers.geolo.guitarworld.controller;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.microview.support.ActivityManager;
import org.microview.support.ActivitySupportController;
import pers.geolo.guitarworld.entity.event.Event;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/9/8
 */
public abstract class BaseController extends ActivitySupportController {

    private Unbinder unbinder;

    @Override
    public View createView(ViewGroup container) {
        View view = super.createView(container);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    public Activity getActivity() {
        return ActivityManager.getActivity();
    }

    @Override
    public void destroyView() {
        super.destroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(Event event) {

    }
}
