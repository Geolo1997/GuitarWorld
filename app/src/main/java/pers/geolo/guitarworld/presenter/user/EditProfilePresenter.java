package pers.geolo.guitarworld.presenter.user;

import okhttp3.MultipartBody;

import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.FileApi;
import pers.geolo.guitarworld.network.api.UserApi;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.presenter.base.BasePresenter;
import pers.geolo.guitarworld.ui.base.CustomContext;
import pers.geolo.guitarworld.util.FileUtils;
import pers.geolo.guitarworld.view.EditProfileView;
import pers.geolo.guitarworld.view.PhotoAlbumViewCallBack;

public class EditProfilePresenter extends BasePresenter<EditProfileView> {

    /**
     * 保存资料
     */
    public void saveProfile() {
        User user = new User();
        user.setUsername(CustomContext.getInstance().getLogInfo().getUsername());
        user.setPassword(getView().getPassword());
        user.setEmail(getView().getEmail());
        HttpService.getInstance().getAPI(UserApi.class)
                .updateUserInfo(user)
                .enqueue(new BaseCallback<Void>() {
                    @Override
                    public void onSuccess(Void responseData) {
                        getView().disableEdit();
                        getView().showToast("保存成功！");
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        getView().showToast(errorMessage);
                    }

                    @Override
                    public void onFailure() {
                        getView().showToast("网络错误");
                    }
                });
    }

    /**
     * 从相册选取照片,获取图片路径
     */
    public void chooseImageFromPhotoAlbum() {
        getView().toPhotoAlbumView(new PhotoAlbumViewCallBack() {
            @Override
            public void onSuccess(String filePath) {
                // 上传图片
                uploadAvatar(filePath);
            }

            @Override
            public void onFailure() {
                getView().showToast("获取图片失败！");
            }
        });
    }

    /**
     * 上传图片
     */
    public void uploadAvatar(String avatarFilePath) {
        MultipartBody.Part body = FileUtils.createMultipartBodyPart(avatarFilePath, "avatar");
        HttpService.getInstance().getAPI(FileApi.class)
                .uploadProfilePicture(body).enqueue(new BaseCallback<Void>() {
            @Override
            public void onSuccess(Void responseData) {
                getView().setAvatar(avatarFilePath);
                getView().showToast("保存成功！");
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                getView().showToast("网络错误");
            }

            @Override
            public void onFailure() {
                getView().showToast("网络错误");
            }
        });
    }
}
