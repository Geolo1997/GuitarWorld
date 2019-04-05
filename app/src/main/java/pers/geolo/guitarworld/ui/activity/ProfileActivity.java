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
import pers.geolo.guitarworld.presenter.user.ProfilePresenter;
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
import pers.geolo.guitarworld.util.*;
import pers.geolo.guitarworld.view.EditProfileView;
import pers.geolo.guitarworld.view.ProfileView;

public class ProfileActivity extends BaseActivity implements ProfileView, EditProfileView {

    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
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
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;

    ProfilePresenter profilePresenter = new ProfilePresenter();

    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected int getContentView() {
        return R.layout.activity_my_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profilePresenter.bind(this);
        // 设置当前用户
        String username = getIntent().getStringExtra(ModuleMessage.CURRENT_USERNAME);
        profilePresenter.setUsername(username);
        // 更新个人资料
        profilePresenter.loadAvatar();
        profilePresenter.loadProfile();
        // 禁用编辑控件
        ViewUtils.setViewGroupEnabled(linearLayout, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        profilePresenter.unBind();
    }

    @OnClick(R.id.bt_edit)
    public void onBtEditClicked() {
        ViewUtils.setViewGroupEnabled(linearLayout, true);
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
                        PermissionUtils.choosePhoto(ProfileActivity.this, new ActivityCallback() {
                            @Override
                            public void onSuccess(Intent data) {
                                String filePath = GetPhotoFromPhotoAlbum.getRealPathFromUri(ProfileActivity.this,
                                        data.getData());

                                MultipartBody.Part body = FileUtils.createMultipartBodyPart(filePath, "profilePicture");
                                HttpService.getInstance().getAPI(FileApi.class)
                                        .uploadProfilePicture(body).enqueue(new BaseCallback<Void>() {
                                    @Override
                                    public void onSuccess(Void responseData) {
                                        showToast("保存成功！");
                                        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                                        ivAvatar.setImageBitmap(bitmap);
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

    @Override
    public void setUsername(String username) {
        tvUsername.setText(username);
    }

    @Override
    public void setEmail(String email) {
        etEmail.setText(email);
    }

    @Override
    public void setAvatar(InputStream inputStream) {
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        runOnUiThread(()-> {
            ivAvatar.setImageBitmap(bitmap);
        });
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString().trim();
    }

    @Override
    public String getEmail() {
        return etEmail.getText().toString();
    }

    @Override
    public void disableEdit() {
        ViewUtils.setViewGroupEnabled(linearLayout, false);
    }
}