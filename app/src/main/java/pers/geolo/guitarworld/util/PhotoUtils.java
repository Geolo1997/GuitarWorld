package pers.geolo.guitarworld.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-03
 */
public class PhotoUtils {

    public static final String[] CAMERA_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static final String[] PICK_PHOTO_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Deprecated
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

    public static void openCamera(Activity activity, Callback callback) {
        // 申请权限
        PermissionUtils.requestPermissions(activity, CAMERA_PERMISSIONS, new PermissionUtils.Callback() {
            @Override
            public void onSuccess(String[] permissions, int[] grantResults) {
                // 拍摄照片
                File file = new File(Environment.getExternalStorageDirectory().getPath()
                        + "/guitarworld/" + System.currentTimeMillis() + ".jpg");
                openCamera(activity, file, new ActivityUtils.Callback() {
                    @Override
                    public void onSuccess(Intent intent) {
                        callback.onSuccess(file);
                    }

                    @Override
                    public void onFailure(Intent intent) {
                        //
                        callback.onFailure(FailureType.USER_CANCEL);
                    }
                });
            }

            @Override
            public void onFailure(String[] permissions, int[] grantResults) {
                // 没有权限
                callback.onFailure(FailureType.PERMISSION_DENIED);
            }
        });
    }

    public static void openAlbum(Activity activity, ActivityUtils.Callback callback) {
        // 申请权限
        PermissionUtils.requestPermissions(activity, PICK_PHOTO_PERMISSIONS, new PermissionUtils.Callback() {
            @Override
            public void onSuccess(String[] permissions, int[] grantResults) {
                // 选择照片
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                ActivityUtils.startActivity(activity, intent, null, callback);
            }

            @Override
            public void onFailure(String[] permissions, int[] grantResults) {
                callback.onFailure(null);
            }
        });
    }

    public static void openAlbum(Activity activity, Callback callback) {
        // 申请权限
        PermissionUtils.requestPermissions(activity, PICK_PHOTO_PERMISSIONS, new PermissionUtils.Callback() {
            @Override
            public void onSuccess(String[] permissions, int[] grantResults) {
                // 选择照片
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                ActivityUtils.startActivity(activity, intent, null, new ActivityUtils.Callback() {
                    @Override
                    public void onSuccess(Intent intent) {
                        String filePath = GetPhotoFromPhotoAlbum.getRealPathFromUri(activity, intent.getData());
                        callback.onSuccess(new File(filePath));
                    }

                    @Override
                    public void onFailure(Intent intent) {
                        callback.onFailure(FailureType.USER_CANCEL);
                    }
                });
            }

            @Override
            public void onFailure(String[] permissions, int[] grantResults) {
                callback.onFailure(FailureType.PERMISSION_DENIED);
            }
        });
    }

    public static void openVideoCamera(Activity activity, File file, ActivityUtils.Callback callback) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
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

    public interface Callback {
        void onSuccess(File photo);

        void onFailure(FailureType failureType);
    }

    public enum FailureType {
        // 没有权限
        PERMISSION_DENIED,
        // 用户取消
        USER_CANCEL,
        // 相机错误
        CAMERA_ERROR,
        // 文件系统错误
        FILE_SYSTEM_ERROR
    }
}
