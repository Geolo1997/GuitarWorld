package pers.geolo.guitarworld.presenter.user;

import android.util.Log;
import java.io.InputStream;
import java.util.List;
import retrofit2.Call;

import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.entity.UserListType;
import pers.geolo.guitarworld.entity.UserRelation;
import pers.geolo.guitarworld.entity.UserRelationType;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.ResponseBody;
import pers.geolo.guitarworld.network.api.FileApi;
import pers.geolo.guitarworld.network.api.UserApi;
import pers.geolo.guitarworld.network.api.UserRelationApi;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.network.callback.FileCallBack;
import pers.geolo.guitarworld.network.callback.MvpCallBack;
import pers.geolo.guitarworld.ui.base.BaseListPresenter;
import pers.geolo.guitarworld.ui.base.CustomContext;
import pers.geolo.guitarworld.view.UserItemView;
import pers.geolo.guitarworld.view.UserListView;

public class UserListPresenter extends BaseListPresenter<UserListView, UserItemView, User> {

    private static final String ADD_FOLLOWING = "+关注";
    private static final String FOLLOWED = "已关注";
    private static final String FOLLOW_EACH_OTHER = "互相关注";

    private UserRelation getRelation(int index) {
        String currentUsername = CustomContext.getInstance().getLogInfo().getUsername();
        String otherUsername = getDataList().get(index).getUsername();
        return new UserRelation(currentUsername, otherUsername);
    }

    @Override
    public void onBindItemView(UserItemView itemView, int index) {
        User user = getDataList().get(index);
        itemView.setUsername(user.getUsername());
        itemView.setEmail(user.getEmail());
        // 获取用户关系
        HttpService.getInstance().getAPI(UserRelationApi.class)
                .getMyRelationTypeWith(user.getUsername())
                .enqueue(new BaseCallback<UserRelationType>() {
                    @Override
                    public void onSuccess(UserRelationType responseData) {
                        switch (responseData) {
                            case FOLLOWER:
                            case UN_FOLLOW_EACH_OTHER:
                                itemView.setFollowState(ADD_FOLLOWING);
                                break;
                            case FOLLOWING:
                                itemView.setFollowState(FOLLOWED);
                                break;
                            case FOLLOW_EACH_OTHER:
                                itemView.setFollowState(FOLLOW_EACH_OTHER);
                                break;
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
        // 获取用户头像
        HttpService.getInstance().getAPI(FileApi.class)
                .getProfilePicture(itemView.getUsername())
                .enqueue(new FileCallBack() {
                    @Override
                    protected void onResponseInputStream(InputStream inputStream) {
                        itemView.setAvatar(inputStream);
                    }

                    @Override
                    protected void onError(int code, String message) {
                        Log.d(TAG, message);
                    }

                    @Override
                    public void onFailure(Call<okhttp3.ResponseBody> call, Throwable t) {

                    }
                });
    }

    public void loadUserList() {
        getView().showRefreshing();
        String username = (String) getFilter().get("currentUsername");
        String userType = (String) getFilter().get(UserListType.class.getSimpleName());
        UserApi userApi = HttpService.getInstance().getAPI(UserApi.class);
        Call<ResponseBody<List<User>>> call;
        if (UserListType.ALL.equals(userType)) { // 全部用户 （测试功能）
            call = userApi.getAllUsers();
        } else if (UserListType.FOLLOWER.equals(userType)) { // 我关注的
            call = userApi.getFollower(username);
        } else if (UserListType.FOLLOWING.equals(userType)) { // 我的粉丝
            call = userApi.getFollowing(username);
        } else {
            return;
        }
        call.enqueue(new MvpCallBack<List<User>>(getView()) {
            @Override
            public void onSuccess(List<User> responseData) {
                addAllItemView(responseData);
                getView().hideRefreshing();
            }

            @Override
            public void onError(int errorCode, String errorMessage) {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    public void changeFollowState(int index) {
        String text = getItemView(index).getFollowState();
        if (ADD_FOLLOWING.equals(text)) { // 未关注
            // 添加关注
            addRelation(index);
        } else { // 已经关注
            getItemView(index).showConfirmAlert(isConfirm -> {
                if (isConfirm) {
                    // 移除关注
                    removeRelation(index);
                }
            });
        }
    }

    public void addRelation(int index) {
        HttpService.getInstance().getAPI(UserRelationApi.class)
                .addRelation(getRelation(index))
                .enqueue(new MvpCallBack<Void>(getView()) {
                    @Override
                    public void onSuccess(Void responseData) {
                        getItemView(index).setFollowState(FOLLOWED);
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
    }

    public void removeRelation(int index) {
        HttpService.getInstance().getAPI(UserRelationApi.class)
                .removeRelation(getRelation(index))
                .enqueue(new MvpCallBack<Void>(getView()) {
                    @Override
                    public void onSuccess(Void responseData) {
                        getItemView(index).setFollowState(ADD_FOLLOWING);
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
    }
}
