package pers.geolo.guitarworld.presenter;

import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.WorksApi;
import pers.geolo.guitarworld.network.callback.MvpNetworkCallBack;
import pers.geolo.guitarworld.view.WorksDetailView;

public class WorksDetailPresenter extends BasePresenter<WorksDetailView> {

    private int worksId;

    public void setWorksId(int worksId) {
        this.worksId = worksId;
    }

    /**
     * 加载创作详情
     */
    public void loadingWorksDetail() {
        HttpService.getInstance().getAPI(WorksApi.class)
                .getWorks(worksId)
                .enqueue(new MvpNetworkCallBack<Works>(getView()) {
                    @Override
                    public void onSuccess(Works responseData) {
                        getView().setAuthor(responseData.getAuthor());
                        getView().setCreateTime(responseData.getCreateTime());
                        getView().setTitle(responseData.getTitle());
                        getView().setContent(responseData.getContent().toString());
                        getView().hideRefreshing();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        getView().showToast("网络错误！");
                        getView().hideRefreshing();
                    }

                    @Override
                    public void onFailure() {
                        getView().showToast("网络错误！");
                        getView().hideRefreshing();
                    }
                });
    }
}
