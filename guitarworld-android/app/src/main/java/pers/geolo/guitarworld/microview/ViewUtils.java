package pers.geolo.guitarworld.microview;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/9/5
 */
public class ViewUtils {

    /**
     * 判断child是否是parent的子view
     * @param parent
     * @param child
     * @return
     */
    public static boolean isChildOf(View parent, View child) {
        if (parent instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) parent).getChildCount(); i++) {
                View subView = ((ViewGroup) parent).getChildAt(i);
                if (isChildOf(subView, child)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * 获取parent的所有直接或间接子view
     * @param parent
     * @return
     */
    public static List<View> getAllChildren(ViewGroup parent) {
        List<View> children = new ArrayList<>();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            children.add(child);
            if (child instanceof ViewGroup) {
                children.addAll(getAllChildren((ViewGroup) child));
            }
        }
        return children;
    }

    /**
     * 获取parent的所有直接或间接子ViewGroup
     * @param parent
     * @return
     */
    public static List<ViewGroup> getViewGroupChildren(ViewGroup parent) {
        List<ViewGroup> children = new ArrayList<>();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                children.add((ViewGroup) child);
                children.addAll(getViewGroupChildren((ViewGroup) child));
            }
        }
        return children;
    }
}
