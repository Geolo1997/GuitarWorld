package pers.geolo.guitarworld.delegate.user;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import java.io.File;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.image.ImageDetailDelegate;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.FileListener;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.model.ImageModel;
import pers.geolo.guitarworld.model.UserModel;
import pers.geolo.guitarworld.util.*;

public class ProfileDelegate extends BaseDelegate {

    private static final String USERNAME = "USERNAME";

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

    private User user;

    public static ProfileDelegate newInstance(String username) {
        Bundle args = new Bundle();
        args.putString(USERNAME, username);
        ProfileDelegate fragment = new ProfileDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_profile;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        if (getArguments() != null) {
            // 禁用编辑控件
            ViewUtils.setViewGroupEnabled(linearLayout, false);
            user = new User();
            user.setUsername(getArguments().getString(USERNAME));
            initAvatar();
            initProfile();
        }
    }

    private void initProfile() {
        UserModel.getPublicProfile(user.getUsername(), new DataListener<User>() {
            @Override
            public void onReturn(User user) {
                ProfileDelegate.this.user = user;
                tvUsername.setText(user.getUsername());
                etPassword.setText(user.getPassword());
                etEmail.setText(user.getEmail());
                // TODO 完整赋值
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void initAvatar() {
        ImageModel.getAvatar(user.getUsername(), new FileListener<Bitmap>() {
            @Override
            public void onProgress(int currentLength, int totalLength) {

            }

            @Override
            public void onReturn(Bitmap bitmap) {
                ivAvatar.setImageBitmap(bitmap);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @OnClick(R.id.bt_edit)
    public void onBtEditClicked() {
        ViewUtils.setViewGroupEnabled(linearLayout, true);
    }

    @OnClick(R.id.bt_save)
    public void onBtSaveClicked() {
//        String
    }

    @OnClick(R.id.iv_avatar)
    public void onIvAvatarClicked() {
        start(ImageDetailDelegate.newInstance(user.getAvatarPath()));
    }

    @OnClick(R.id.bt_take_photo)
    public void takePhoto() {
        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!PermissionUtils.hasPermissions(getContext(), permissions)) {
            PermissionUtils.requestPermissions(getDelegateActivity(), permissions, new PermissionUtils.Callback() {
                @Override
                public void onSuccess(String[] permissions, int[] grantResults) {
                    toCamera();
                }

                @Override
                public void onFailure(String[] permissions, int[] grantResults) {

                }
            });
        } else {
            toCamera();
        }
    }

    public void toCamera() {
        File file = new File(Environment.getExternalStorageDirectory().getPath()
                + "/guitarworld/" + System.currentTimeMillis() + ".jpg");
        MediaUtils.openCamera(getDelegateActivity(), file, new ActivityUtils.Callback() {
            @Override
            public void onSuccess(@Nullable Intent intent) {
                Glide.with(ProfileDelegate.this).load(file).into(ivAvatar);
            }

            @Override
            public void onFailure(@Nullable Intent intent) {
                Toast.makeText(getContext(), "打开相机失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.bt_update_profile_picture)
    public void onBtUpdateProfilePictureClicked() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!PermissionUtils.hasPermissions(getContext(), permissions)) {
            PermissionUtils.requestPermissions(getDelegateActivity(), permissions, new PermissionUtils.Callback() {
                @Override
                public void onSuccess(String[] permissions, int[] grantResults) {
                    openAlbum();
                }

                @Override
                public void onFailure(String[] permissions, int[] grantResults) {

                }
            });
        } else {
            openAlbum();
        }
    }


    public void disableEdit() {
        ViewUtils.setViewGroupEnabled(linearLayout, false);
    }

    public void openAlbum() {
        MediaUtils.openAlbum(getDelegateActivity(), new ActivityUtils.Callback() {
            @Override
            public void onSuccess(Intent intent) {
                String filePath = GetPhotoFromPhotoAlbum.getRealPathFromUri(getContext(), intent.getData());
                ImageModel.uploadAvatar(new File(filePath), new DataListener<String>() {
                    @Override
                    public void onReturn(String s) {
                        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                        ivAvatar.setImageBitmap(bitmap);
                        Toast.makeText(getContext(), "上传成功！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }

            @Override
            public void onFailure(Intent intent) {

            }
        });
    }
}
