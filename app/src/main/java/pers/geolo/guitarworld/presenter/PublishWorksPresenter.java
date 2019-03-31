package pers.geolo.guitarworld.presenter;

import java.util.Date;

import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.WorksApi;
import pers.geolo.guitarworld.network.callback.MvpNetworkCallBack;
import pers.geolo.guitarworld.view.PublishWorksView;

public class PublishWorksPresenter extends BasePresenter<PublishWorksView> {


    /**
     * 发布创作
     */
    public void publishWorks() {
        Works works = new Works();
        String username = DAOService.getInstance().getCurrentLogInfo().getUsername();
        works.setAuthor(username);
        works.setCreateTime(new Date());
        works.setTitle(getView().getWorksTitle());
        works.setContent(getView().getContent());
        // 发送发布请求
        HttpService.getInstance().getAPI(WorksApi.class)
                .publishWorks(works)
                .enqueue(new MvpNetworkCallBack<Void>(getView()) {
                    @Override
                    public void onSuccess(Void responseData) {
                        getView().showToast("发布成功");
                        // 跳转至主视图
                        getView().toMainView();
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
