package pers.geolo.guitarworld.util;

import android.content.Intent;
import java.io.File;

import pers.geolo.android.app.ActivityCallback;
import pers.geolo.android.app.SwitchActivity;
import pers.geolo.guitarworld.ui.base.BaseActivity;
import pers.geolo.guitarworld.ui.temp.ActivityRequestCode;

public class AndroidSystemUtils {

    public static void choosePhoto(BaseActivity baseActivity, ActivityCallback activityCallback) {
//        baseActivity.(ActivityRequestCode.CHOOSE_PHOTO, activityCallback);
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        baseActivity.startActivityForResult(intent, ActivityRequestCode.CHOOSE_PHOTO.ordinal());
    }

    public static void getLocalImage(SwitchActivity switchActivity, ISuccess iSuccess, IFailure iFailure) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        switchActivity.startActivity(intent, ActivityRequestCode.CHOOSE_PHOTO.ordinal(), new ActivityCallback() {
            @Override
            public void onSuccess(Intent intent) {
                String filePath = GetPhotoFromPhotoAlbum.getRealPathFromUri(switchActivity, intent.getData());
                File file = new File(filePath);
                iSuccess.onSuccess(file);
            }

            @Override
            public void onFailure(Intent intent) {
                if (iFailure != null) {
                    iFailure.onFailure("获取图片失败！");
                }
            }
        });
    }

    public interface ISuccess {
        void onSuccess(File file);
    }

    public interface IFailure {
        void onFailure(String message);
    }
}
