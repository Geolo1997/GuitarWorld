package pers.geolo.guitarworld.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.*;
import butterknife.BindView;
import butterknife.OnClick;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.ui.base.BaseActivity;
import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.FileApi;
import pers.geolo.guitarworld.network.api.UserApi;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.ui.temp.ActivityCallback;
import pers.geolo.guitarworld.ui.temp.PermissionCallback;
import pers.geolo.guitarworld.ui.temp.PermissionRequestCode;
import pers.geolo.guitarworld.util.FileUtils;
import pers.geolo.guitarworld.util.GetPhotoFromPhotoAlbum;
import pers.geolo.guitarworld.util.PermissionUtils;
import pers.geolo.guitarworld.util.ViewUtils;

public class MyProfileActivity extends BaseActivity {

    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_sex)
    EditText etSex;
    @BindView(R.id.et_birthday)
    EditText etBirthday;
    @BindView(R.id.et_hometown)
    EditText etHometown;
    @BindView(R.id.edit_texts)
    LinearLayout linearLayout;
    @BindView(R.id.iv_profile_picture)
    ImageView ivProfilePicture;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 更新个人资料
        updateProfile();
        updateProfilePicture();
        // 禁用编辑控件
        ViewUtils.setViewGroupEnabled(linearLayout, false);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_my_profile;
    }

//    protected void updateProfilePicture() {
//        HttpService.getInstance().getAPI(FileApi.class)
//                .
//    }

    protected void updateProfile() {
        String username = DAOService.getInstance().getCurrentLogInfo().getUsername();
        HttpService.getInstance().getAPI(UserApi.class)
                .getUserInfo(username).enqueue(new BaseCallback<User>() {
            @Override
            public void onSuccess(User responseData) {
                tvUsername.setText(responseData.getUsername());
                etEmail.setText(responseData.getEmail());
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
            }

            @Override
            public void onFailure() {
                startActivity(NetworkErrorActivity.class);
            }
        });
    }

    void updateProfilePicture() {
        String currentUsername = DAOService.getInstance().getCurrentLogInfo().getUsername();
//        HttpService.getInstance().setBaseUrl("https://img.pc841.com/2018/0815/");
        HttpService.getInstance().getAPI(FileApi.class)
                .getProfilePicture(currentUsername).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "获取头像成功...");
                InputStream inputStream = response.body().byteStream();
                new Thread(() -> {
                    try {
                        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.jpg";
//                        FileUtils.saveFile(filePath, inputStream);
//                        byte[] bytes = toByteArray(inputStream);
//                        System.err.println("-----------------------------");
//                        for (int i = 0; i < bytes.length; i++) {
//                            System.out.print(bytes[i]);
//                        }
//                        System.err.println("\n-----------------------------");
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        runOnUiThread(() -> {
                            Log.d(TAG, "bitmap : " + String.valueOf(bitmap == null));
                            ivProfilePicture.setImageBitmap(bitmap);
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    @OnClick(R.id.bt_edit)
    public void onBtEditClicked() {
        ViewUtils.setViewGroupEnabled(linearLayout, true);
    }

    // TODO
    @OnClick(R.id.bt_save)
    public void onBtSaveClicked() {
        LogInfo logInfo = DAOService.getInstance().getCurrentLogInfo();
        User user = new User();
        user.setUsername(logInfo.getUsername());
        user.setPassword(logInfo.getPassword());
        user.setEmail(etEmail.getText().toString());
        HttpService.getInstance().getAPI(UserApi.class)
                .updateUserInfo(user).enqueue(new BaseCallback<Void>() {
            @Override
            public void onSuccess(Void responseData) {
                ViewUtils.setViewGroupEnabled(linearLayout, false);
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                showToast("保存失败！");
            }

            @Override
            public void onFailure() {
                startActivity(NetworkErrorActivity.class);
            }
        });
    }

    //获取权限
    private void getPermission() {
        if (EasyPermissions.hasPermissions(this, permissions)) {
            //已经打开权限
            Toast.makeText(this, "已经申请相关权限", Toast.LENGTH_SHORT).show();
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(this, "需要获取您的相册、照相使用权限", 1, permissions);
        }
    }

    @OnClick(R.id.bt_take_photo)
    public void takePhoto() {
//        requestPermission(PermissionRequestCode.USE_CAMERA, );
    }

//    private File cameraSavePath;//拍照照片路径
//    private Uri uri;//照片uri
//
//    //激活相机操作
//    private void goCamera() {
//        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System
//        .currentTimeMillis() + ".jpg");
//
//
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            //第二个参数为 包名.fileprovider
//            uri = FileProvider.getUriForFile(this, "pers.geolo.guitarworld  .fileprovider", cameraSavePath);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        } else {
//            uri = Uri.fromFile(cameraSavePath);
//        }
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//
//
//        ActivityCallback callback = new ActivityCallback() {
//            @Override
//            public void onSuccess(Intent data) {
//
//            }
//
//            @Override
//            public void onFailure() {
//
//            }
//        }
//        startActivityForResult(intent, ActivityRequestCode.TAKE_PHOTO.ordinal());
//    }

    @OnClick(R.id.bt_update_profile_picture)
    public void onBtUpdateProfilePictureClicked() {
//        if (havePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        requestPermission(PermissionRequestCode.USE_PHOTO_ALBUM, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                new PermissionCallback() {
                    @Override
                    public void onSuccess() {
                        PermissionUtils.choosePhoto(MyProfileActivity.this, new ActivityCallback() {
                            @Override
                            public void onSuccess(Intent data) {
                                String filePath = GetPhotoFromPhotoAlbum.getRealPathFromUri(MyProfileActivity.this,
                                        data.getData());

                                MultipartBody.Part body = FileUtils.createMultipartBodyPart(filePath, "profilePicture");
                                HttpService.getInstance().getAPI(FileApi.class)
                                        .uploadProfilePicture(body).enqueue(new BaseCallback<Void>() {
                                    @Override
                                    public void onSuccess(Void responseData) {
                                        showToast("保存成功！");
                                        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                                        ivProfilePicture.setImageBitmap(bitmap);
                                    }

                                    @Override
                                    public void onError(int errorCode, String errorMessage) {
                                        showToast("网络错误");
                                    }

                                    @Override
                                    public void onFailure() {
                                        showToast("网络错误");
                                    }
                                });
                            }

                            @Override
                            public void onFailure() {
                                showToast("读取失败");
                            }
                        });
                    }

                    @Override
                    public void onFailure() {
                        showToast("没有权限");
                    }
                });
//        } else {
//            Log.d(TAG, "没有权限");
//        }
    }
}