package pers.geolo.guitarworld.presenter.user;

import java.io.InputStream;
import okhttp3.ResponseBody;
import retrofit2.Call;

import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.FileApi;
import pers.geolo.guitarworld.network.api.UserApi;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.network.callback.FileCallBack;
import pers.geolo.guitarworld.presenter.base.BasePresenter;
import pers.geolo.guitarworld.view.ProfileView;

/**
 * 个人资料Presenter
 */
public class ProfilePresenter extends BasePresenter<ProfileView> {

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 加载个人资料
     */
    public void loadProfile() {
        HttpService.getInstance().getAPI(UserApi.class)
                .getUserInfo(username)
                .enqueue(new BaseCallback<User>() {
                    @Override
                    public void onSuccess(User responseData) {
                        getView().setUsername(responseData.getUsername());
                        getView().setEmail(responseData.getEmail());
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
     * 加载头像
     */
    public void loadAvatar() {
        HttpService.getInstance().getAPI(FileApi.class)
                .getAvatar(username)
                .enqueue(new FileCallBack() {
                    @Override
                    protected void onResponseInputStream(InputStream inputStream) {
                        getView().setAvatar(inputStream);
                    }

                    @Override
                    protected void onError(int code, String message) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }
}
