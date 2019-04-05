package pers.geolo.guitarworld.view;

import java.io.InputStream;

import pers.geolo.guitarworld.view.base.ToastView;

/**
 * 个人资料视图
 */
public interface ProfileView extends ToastView {
    void setUsername(String username);

    void setEmail(String email);

    void setAvatar(InputStream inputStream);

    void toImageDetail(String imagePath);

    void setPassword(String password);
}
