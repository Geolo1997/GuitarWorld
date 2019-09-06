package pers.geolo.guitarworld.delegate.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import me.shaohui.bottomdialog.BottomDialog;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BeanFactory;
import pers.geolo.guitarworld.delegate.base.SwipeBackDelegate;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.FileListener;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.UserModel;
import pers.geolo.guitarworld.util.GlideUtils;
import pers.geolo.guitarworld.util.PhotoUtils;
import pers.geolo.guitarworld.util.ToastUtils;

import java.io.File;

public class ProfileDelegate extends SwipeBackDelegate {

    private static final String USERNAME = "USERNAME";

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.edits_layout)
    ViewGroup editsLayout;
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

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);
    UserModel userModel = BeanFactory.getBean(UserModel.class);

    private User user;
    private boolean isEditLayoutEnable;

    public static ProfileDelegate newInstance(String username) {
        Bundle args = new Bundle();
        args.putString(USERNAME, username);
        ProfileDelegate fragment = new ProfileDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object getLayoutView() {
        return R.layout.profile;
    }

    @Override
    protected View getStatueBarTopView() {
        return titleBar;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        if (getArguments() != null) {
            user = new User();
            user.setUsername(getArguments().getString(USERNAME));
            initTitleBar();
            initProfile();
        }
    }

    private void initTitleBar() {
        String currentUsername = authModel.getCurrentLoginUser().getUsername();
        if (!currentUsername.equals(user.getUsername())) {
            titleBar.setRightIcon(null);
        } else {
            titleBar.setRightTitle("编辑");
        }
        setEditsLayoutEnable(false);
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
                if (isEditLayoutEnable) {
                    saveProfile();
                } else {
                    if (titleBar.getRightTitle().equals("编辑")) {
                        titleBar.setRightTitle("");
                        titleBar.setRightIcon(R.drawable.ic_save_24);
                        setEditsLayoutEnable(true);
                    }
                }
            }
        });
    }

    private void setEditsLayoutEnable(boolean isEnable) {
        isEditLayoutEnable = isEnable;
        setViewGroupEnabled(editsLayout, isEnable);
    }

    public static void setViewGroupEnabled(View view, Boolean enabled) {
        if (view.getClass() == AppCompatEditText.class) {
            view.setEnabled(enabled);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View subView = viewGroup.getChildAt(i);
                setViewGroupEnabled(subView, enabled);
            }
        }
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
                ToastUtils.showSuccessToast(getContext(), "保存成功");
                setEditsLayoutEnable(false);
                titleBar.setRightTitle("编辑");
                titleBar.setRightIcon(null);
            }

            @Override
            public void onError(String message) {
                ToastUtils.showErrorToast(getContext(), message);
            }
        });
    }

    @OnClick(R.id.iv_avatar)
    public void onIvAvatarClicked() {
        String username = authModel.getCurrentLoginUser().getUsername();
        if (!username.equals(user.getUsername())) {
            return;
        }
//        start(ImageDetailDelegate.newInstance(user.getAvatarUrl()));
//        BottomDialog.create(getChildFragmentManager())
//                .setViewListener(v -> {
//                    v.findViewById(R.id.take_photo_button).setOnClickListener(view -> {
//                        takePhoto();
//                    });
//                    v.findViewById(R.id.select_photo_button).setOnClickListener(view -> {
//                        selectPhoto();
//                    });
//                })
//                .setLayoutRes(R.layout.upload_photo_option)      // dialog layout
//                .show();
    }

    public void takePhoto() {
        PhotoUtils.openCamera(getContainerActivity(), new PhotoUtils.Callback() {
            @Override
            public void onSuccess(File photo) {
                uploadAvatar(photo);
            }

            @Override
            public void onFailure(PhotoUtils.FailureType failureType) {
                ToastUtils.showErrorToast(getContext(), failureType.name());
            }
        });
    }

    public void selectPhoto() {
        PhotoUtils.openAlbum(getContainerActivity(), new PhotoUtils.Callback() {
            @Override
            public void onSuccess(File photo) {
                uploadAvatar(photo);
            }

            @Override
            public void onFailure(PhotoUtils.FailureType failureType) {

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
                ToastUtils.showSuccessToast(getContext(), "上传成功");
            }

            @Override
            public void onError(String message) {
                ToastUtils.showErrorToast(getContext(), message);
            }
        });
    }
}
