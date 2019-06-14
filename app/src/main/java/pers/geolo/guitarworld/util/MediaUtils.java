package pers.geolo.guitarworld.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import java.io.File;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-03
 */
public class MediaUtils {
    public static void openCamera(Activity activity, File file, ActivityUtils.Callback callback) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File directory = FileUtils.getDirectory(file);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(activity, "pers.geolo.guitarworld.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        ActivityUtils.startActivity(activity, intent, null, callback);
    }

    public static void openAlbum(Activity activity, ActivityUtils.Callback callback) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        ActivityUtils.startActivity(activity, intent, null, callback);
    }
}
