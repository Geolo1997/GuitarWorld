package pers.geolo.guitarworld.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.*;
import butterknife.BindView;
import butterknife.OnClick;
import java.io.InputStream;
import pub.devrel.easypermissions.EasyPermissions;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.presenter.user.EditProfilePresenter;
import pers.geolo.guitarworld.presenter.user.ProfilePresenter;
import pers.geolo.guitarworld.ui.base.BaseActivity;
import pers.geolo.guitarworld.ui.temp.ActivityCallback;
import pers.geolo.guitarworld.ui.temp.ActivityRequestCode;
import pers.geolo.guitarworld.ui.temp.PermissionCallback;
import pers.geolo.guitarworld.ui.temp.PermissionRequestCode;
import pers.geolo.guitarworld.util.GetPhotoFromPhotoAlbum;
import pers.geolo.guitarworld.util.ModuleMessage;
import pers.geolo.guitarworld.util.ViewUtils;
import pers.geolo.guitarworld.view.EditProfileView;
import pers.geolo.guitarworld.view.PhotoAlbumViewCallBack;
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
    EditProfilePresenter editProfilePresenter = new EditProfilePresenter();

    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected int getContentView() {
        return R.layout.activity_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profilePresenter.bind(this);
        editProfilePresenter.bind(this);
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

    @OnClick(R.id.bt_save)
    public void onBtSaveClicked() {
        editProfilePresenter.saveProfile();
    }

    @OnClick(R.id.iv_avatar)
    public void onIvAvatarClicked() {
        profilePresenter.toImageDetail();
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
        editProfilePresenter.chooseImageFromPhotoAlbum();
    }

    /**
     * 获取读写硬盘权限
     */
    private void requestWriteExternalStorage(PermissionCallback callback) {
        requestPermission(PermissionRequestCode.USE_PHOTO_ALBUM, Manifest.permission.WRITE_EXTERNAL_STORAGE, callback);
    }

    @Override
    public void setUsername(String username) {
        tvUsername.setText(username);
    }

    @Override
    public void setAvatar(InputStream inputStream) {
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        runOnUiThread(() -> {
            ivAvatar.setImageBitmap(bitmap);
        });
    }

    @Override
    public void toImageDetail(String imagePath) {
        Intent intent = new Intent(this, ImageDetailActivity.class);
        intent.putExtra(ModuleMessage.IMAGE_PATH, imagePath);
        startActivity(intent);
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString().trim();
    }

    @Override
    public void setPassword(String password) {
        etPassword.setText(password);
    }

    @Override
    public String getEmail() {
        return etEmail.getText().toString();
    }

    @Override
    public void setEmail(String email) {
        etEmail.setText(email);
    }

    @Override
    public void disableEdit() {
        ViewUtils.setViewGroupEnabled(linearLayout, false);
    }

    @Override
    public void toPhotoAlbumView(PhotoAlbumViewCallBack callBack) {
        requestWriteExternalStorage(new PermissionCallback() {
            @Override
            public void onSuccess() {
                // 添加活动请求
                addActivityRequest(ActivityRequestCode.CHOOSE_PHOTO, new ActivityCallback() {
                    @Override
                    public void onSuccess(Intent data) {
                        String filePath = GetPhotoFromPhotoAlbum.getRealPathFromUri(ProfileActivity.this,
                                data.getData());
                        callBack.onSuccess(filePath);
                    }

                    @Override
                    public void onFailure() {
                        callBack.onFailure();
                    }
                });
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, ActivityRequestCode.CHOOSE_PHOTO.ordinal());
            }

            @Override
            public void onFailure() {
                showToast("没有权限");
            }
        });
    }

    @Override
    public void setAvatar(String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        ivAvatar.setImageBitmap(bitmap);
    }
}