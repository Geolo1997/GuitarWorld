package pers.geolo.guitarworld.util;

import android.content.Intent;

import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.temp.ActivityCallback;
import pers.geolo.guitarworld.temp.ActivityRequestCode;

public class PermissionUtils {

    public static void choosePhoto(BaseActivity baseActivity, ActivityCallback activityCallback) {
        baseActivity.addActivityRequest(ActivityRequestCode.CHOOSE_PHOTO, activityCallback);
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        baseActivity.startActivityForResult(intent, ActivityRequestCode.CHOOSE_PHOTO.ordinal());
    }
}
