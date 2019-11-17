package pers.geolo.guitarworld.controller.user;

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
import org.microview.core.ControllerManager;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.entity.DataCallback;
import pers.geolo.guitarworld.entity.FileListener;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.UserModel;
import pers.geolo.guitarworld.util.GlideUtils;
import pers.geolo.guitarworld.util.PhotoUtils;
import pers.geolo.guitarworld.util.ToastUtils;

import java.io.File;

public class ProfileController extends BaseController {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.edits_layout)
    ViewGroup editsLayout;
    @BindView(R.id.username_text)
    TextView usernameText;
    @BindView(R.id.password_text)
    EditText passwordText;
    @BindView(R.id.email_edit)
    EditText emailEdit;
    @BindView(R.id.sex_edit)
    EditText sexEdit;
    @BindView(R.id.birthday_edit)
    EditText birthdayEdit;
    @BindView(R.id.hometown_edit)
    EditText hometownEdit;
    @BindView(R.id.avatar_image)
    ImageView avatarImage;

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);
    UserModel userModel = BeanFactory.getBean(UserModel.class);

    private User user;
    private boolean isEditLayoutEnable;

    @Override
    protected int getLayout() {
        return R.layout.profile;
    }

    @Override
    public void initView(ViewParams viewParams) {
        String username = (String) viewParams.get("username");
        userModel.getPublicProfile(username, new DataCallback<User>() {
            @Override
            public void onReturn(User user) {
                ProfileController.this.user = user;
                usernameText.setText(user.getUsername());
                passwordText.setText(user.getPassword());
                emailEdit.setText(user.getEmail());
                // TODO 完整赋值
                String avatarPath = user.getAvatarUrl();
                if (avatarPath != null && !"".equals(avatarPath)) {
                    GlideUtils.load(getActivity(), avatarPath, avatarImage);
                } else {
                    // TODO 区分性别
                    avatarImage.setImageResource(R.drawable.male_default_avatar);
                }
            }

            @Override
            public void onError(String message) {

            }
        });
        // 初始化TitleBar
        String currentUsername = authModel.getLoginUser().getUsername();
        if (!currentUsername.equals(username)) {
            titleBar.setRightIcon(null);
        } else {
            titleBar.setRightTitle("编辑");
        }
        setEditsLayoutEnable(false);
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                ControllerManager.destroy(ProfileController.this);
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

    public void saveProfile() {
        user.setPassword(passwordText.getText().toString().trim());
        user.setEmail(emailEdit.getText().toString().trim());
        userModel.updateMyProfile(user, new DataCallback<Void>() {
            @Override
            public void onReturn(Void aVoid) {
                ToastUtils.showSuccessToast(getActivity(), "保存成功");
                setEditsLayoutEnable(false);
                titleBar.setRightTitle("编辑");
                titleBar.setRightIcon(null);
            }

            @Override
            public void onError(String message) {
                ToastUtils.showErrorToast(getActivity(), message);
            }
        });
    }

    @OnClick(R.id.avatar_image)
    public void onIvAvatarClicked() {
        String username = authModel.getLoginUser().getUsername();
        if (!username.equals(user.getUsername())) {
            return;
        }
//        start(ImageDetailController.newInstance(user.getAvatarUrl()));
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
        PhotoUtils.openCamera(getActivity(), new PhotoUtils.Callback() {
            @Override
            public void onSuccess(File photo) {
                uploadAvatar(photo);
            }

            @Override
            public void onFailure(PhotoUtils.FailureType failureType) {
                ToastUtils.showErrorToast(getActivity(), failureType.name());
            }
        });
    }

    public void selectPhoto() {
        PhotoUtils.openAlbum(getActivity(), new PhotoUtils.Callback() {
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
                GlideUtils.load(getActivity(), s, avatarImage);
                ToastUtils.showSuccessToast(getActivity(), "上传成功");
            }

            @Override
            public void onError(String message) {
                ToastUtils.showErrorToast(getActivity(), message);
            }
        });
    }
}
