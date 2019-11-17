package pers.geolo.guitarworld.microview;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/9/3
 */
public class ViewManager {

    private static MicroviewActivity activity;
    private static ViewGroup rootContainer;
    private static Map<ViewGroup, ControllerStack> controllerStackMap;

    static {
        controllerStackMap = new HashMap<>();
    }

    public static void setActivity(MicroviewActivity activity) {
        ViewManager.activity = activity;
        if (activity == null) {
            ViewManager.rootContainer = null;
        } else {
            ViewManager.rootContainer = activity.getRootContainer();
        }
    }

    public static Activity getActivity() {
        return activity;
    }

    public  static ControllerStack getControllerStack(ViewGroup container) {
        return controllerStackMap.get(container);
    }

    public static void load(ViewGroup container, ViewController controller, ViewParam param) {
        if (container == null) {
            throw new NullPointerException("container can not be null");
        }
        ControllerStack controllerStack = controllerStackMap.get(container);
        if (controllerStack == null) {
            controllerStack = new ControllerStack();
        }
        if (controllerStack.size() > 0) {
            ViewController lastController = controllerStack.get(controllerStack.size() - 1);
            lastController.getView().setVisibility(View.GONE);
        }
        controllerStack.add(controller);
        controllerStackMap.put(container, controllerStack);
        container.addView(controller.createView(container));
        controller.initView(param);
    }

    public static void load(ViewGroup container, ViewController controller) {
        load(container, controller, null);
    }

    public static void start(ViewController controller, ViewParam param) {
        load(rootContainer, controller, param);
    }

    public static void start(ViewController controller) {
        load(rootContainer, controller, null);
    }

    public static void destroy(ViewController controller) {
        ViewGroup container = (ViewGroup) controller.getView().getParent();
        ControllerStack controllerStack = controllerStackMap.get(container);
        NullPointerException notFoundControllerException =
                new NullPointerException("can not found controller in controller stack");
        if (controllerStack == null) {
            throw notFoundControllerException;
        }
        boolean removeSuccess = controllerStack.remove(controller);
        if (!removeSuccess) {
            throw notFoundControllerException;
        }
        controllerStackMap.put(container, controllerStack);
        container.removeView(controller.getView());
        if (controllerStack.size() != 0) {
            ViewController lastController = controllerStack.get(controllerStack.size() - 1);
            lastController.getView().setVisibility(View.VISIBLE);
        }
        // 删除container的子ViewGroup的栈
        if (controller.getView() instanceof ViewGroup) {
            List<ViewGroup> children = ViewUtils.getViewGroupChildren((ViewGroup) controller.getView());
            for (int i = 0; i < children.size(); i++) {
                controllerStackMap.remove(children.get(i));
            }
        }
    }
}
