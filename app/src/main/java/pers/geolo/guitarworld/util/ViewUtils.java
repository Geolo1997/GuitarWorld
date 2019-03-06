package pers.geolo.guitarworld.util;

import android.view.View;
import android.view.ViewGroup;

public class ViewUtils {

    public static void setViewGroupEnabled(View view, Boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View subView = viewGroup.getChildAt(i);
                subView.setEnabled(enabled);
            }
        }
    }
}
