package pers.geolo.guitarworld.ui;

import android.support.v4.app.FragmentManager;
import android.view.View;
import me.shaohui.bottomdialog.BottomDialog;
import pers.geolo.guitarworld.R;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/28
 */
public class PhotoOptionDialog {

    public static BottomDialog show(FragmentManager fragmentManager, View.OnClickListener takePhotoListener,
                                    View.OnClickListener selectPhotoListener) {
        return (BottomDialog) BottomDialog.create(fragmentManager)
                .setViewListener(v -> {
                    v.findViewById(R.id.take_photo_button).setOnClickListener(takePhotoListener);
                    v.findViewById(R.id.select_photo_button).setOnClickListener(selectPhotoListener);
                })
                .setLayoutRes(R.layout.upload_photo_option)      // dialog layout
                .show();
    }
}
