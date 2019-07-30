package pers.geolo.guitarworld.test.fragview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/30
 */
public abstract class BaseController implements ViewController {

    private ViewGroup container;
    private View view;

    private List<ViewController> controllerStack;

    public BaseController() {
        controllerStack = new ArrayList<>();
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public View createView() {
        view = LayoutInflater.from(container.getContext())
                .inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setContainer(ViewGroup container) {
        this.container = container;
    }

    public abstract int getLayout();

    public Context getContext() {
        return container.getContext();
    }

    public void load(ViewController controller) {
        controller.createView();
        container.removeView(getView());
        container.addView(controller.getView());
    }

    public void load(ViewGroup container, ViewController controller) {
        controllerStack.add(controller);
        controller.createView();
        container.addView(controller.getView());
    }

    private List<ViewController> getControllerStack() {
        return controllerStack;
    }

//    private List<ViewController> getContainerControllerStack() {
//        return
//    }

}
