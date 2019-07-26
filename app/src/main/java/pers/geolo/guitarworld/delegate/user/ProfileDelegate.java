package pers.geolo.guitarworld.delegate.user;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.OnClick;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import me.shaohui.bottomdialog.BottomDialog;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BeanFactory;
import pers.geolo.guitarworld.base.SwipeBackDelegate;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.FileListener;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.model.FileModel;
import pers.geolo.guitarworld.model.UserModel;
import pers.geolo.guitarworld.util.*;

import java.io.File;

public class ProfileDelegate extends SwipeBackDelegate{

    private static final String USERNAME = "USERNAME";

    @BindView(R.id.title_bar)
    TitleBar titleBar;
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
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;

    UserModel userModel = BeanFactory.getBean(UserModel.class);
    FileModel fileModel = BeanFactory.getBean(FileModel.class);

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
        return R.layout.profile;
    }

    @Override
    protected View getStatueBarTopView() {
        return titleBar;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTitleBar();
        if (getArguments() != null) {
            user = new User();
            user.setUsername(getArguments().getString(USERNAME));
            initProfile();
        }
    }

    private void initTitleBar() {
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                pop();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                saveProfile();
            }
        });
    }

    private void initProfile() {
        userModel.getPublicProfile(user.getUsername(), new DataListener<User>() {
            @Override
            public void onReturn(User user) {
                ProfileDelegate.this.user = user;
                tvUsername.setText(user.getUsername());
                etPassword.setText(user.getPassword());
                etEmail.setText(user.getEmail());
                // TODO 完整赋值
                String avatarPath = user.getAvatarUrl();
                if (avatarPath != null && !"".equals(avatarPath)) {
                    GlideUtils.load(getContext(), avatarPath, ivAvatar);
                } else {
                    // TODO 区分性别
                    ivAvatar.setImageResource(R.drawable.male_default_avatar);
                }
            }

            @Override
            public void onError(String message) {

            }
        });
    }


    public void saveProfile() {
        user.setPassword(etPassword.getText().toString().trim());
        user.setEmail(etEmail.getText().toString().trim());
        userModel.updateMyProfile(user, new DataListener<Void>() {
            @Override
            public void onReturn(Void aVoid) {
                Toast.makeText(getContext(), "保存成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.iv_avatar)
    public void onIvAvatarClicked() {
//        start(ImageDetailDelegate.newInstance(user.getAvatarUrl()));
        BottomDialog.create(getChildFragmentManager())
                .setViewListener(v -> {
                    v.findViewById(R.id.take_photo_button).setOnClickListener(view -> {
                        takePhoto();
                    });
                    v.findViewById(R.id.select_photo_button).setOnClickListener(view -> {
                        selectPhoto();
                    });
                })
                .setLayoutRes(R.layout.upload_photo_option)      // dialog layout
                .show();
    }

    public void takePhoto() {
        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!PermissionUtils.hasPermissions(getContext(), permissions)) {
            PermissionUtils.requestPermissions(getContainerActivity(), permissions, new PermissionUtils.Callback() {
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
        MediaUtils.openCamera(getContainerActivity(), file, new ActivityUtils.Callback() {
            @Override
            public void onSuccess(@Nullable Intent intent) {
                uploadAvatar(file);
            }

            @Override
            public void onFailure(@Nullable Intent intent) {
                Toast.makeText(getContext(), "拍照失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void selectPhoto() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!PermissionUtils.hasPermissions(getContext(), permissions)) {
            PermissionUtils.requestPermissions(getContainerActivity(), permissions, new PermissionUtils.Callback() {
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

    public void openAlbum() {
        MediaUtils.openAlbum(getContainerActivity(), new ActivityUtils.Callback() {
            @Override
            public void onSuccess(Intent intent) {
                String filePath = GetPhotoFromPhotoAlbum.getRealPathFromUri(getContext(), intent.getData());
                uploadAvatar(new File(filePath));
            }

            @Override
            public void onFailure(Intent intent) {

            }
        });
    }

    public void uploadAvatar(File avatarFile) {
        userModel.updateAvatar(avatarFile, new FileListener<String>() {
            @Override
            public void onProgress(long currentLength, long totalLength) {

            }

            @Override
            public void onFinish(String s) {
                user.setAvatarUrl(s);
                GlideUtils.load(getContext(), s, ivAvatar);
                Toast.makeText(getContext(), "上传成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
