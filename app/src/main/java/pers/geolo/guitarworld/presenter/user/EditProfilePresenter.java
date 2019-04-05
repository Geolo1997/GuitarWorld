package pers.geolo.guitarworld.presenter.user;

import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.UserApi;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.presenter.base.BasePresenter;
import pers.geolo.guitarworld.view.EditProfileView;

public class EditProfilePresenter extends BasePresenter<EditProfileView> {

    /**
     * 保存资料
     */
    public void saveProfile() {
        User user = new User();
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
}
