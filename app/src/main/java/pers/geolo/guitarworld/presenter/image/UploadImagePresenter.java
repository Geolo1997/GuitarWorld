package pers.geolo.guitarworld.presenter.image;

import okhttp3.MultipartBody;

import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.presenter.base.BasePresenter;
import pers.geolo.guitarworld.util.FileUtils;
import pers.geolo.guitarworld.view.UploadImageView;

/**
 * @author 桀骜(Geolo)
 * @date 2019-05-29
 */
public class UploadImagePresenter extends BasePresenter<UploadImageView> {

    public void uploadImage(String imagePath) {
        MultipartBody.Part body = FileUtils.createMultipartBodyPart(imagePath, "avatar");
        fileApi.uploadPicture(body).enqueue(new BaseCallback<String>() {
            @Override
            public void onSuccess(String responseData) {
                getView().addImage(responseData);
                getView().showToast("上传成功！");
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
