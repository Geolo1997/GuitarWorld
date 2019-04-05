package pers.geolo.guitarworld.view;

import pers.geolo.guitarworld.view.base.ToastView;

public interface EditProfileView extends ToastView {
    String getPassword();

    String getEmail();

    void disableEdit();

    void toPhotoAlbumView(PhotoAlbumViewCallBack callBack);

    void setAvatar(String filePath);
}
