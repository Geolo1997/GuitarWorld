package pers.geolo.guitarworld.presenter.works;

import java.util.HashMap;
import java.util.List;

import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.WorksApi;
import pers.geolo.guitarworld.network.callback.MvpCallBack;
import pers.geolo.guitarworld.presenter.base.BasePresenter;
import pers.geolo.guitarworld.view.WorksDetailView;

public class WorksDetailPresenter extends BasePresenter<WorksDetailView> {

    private HashMap<String, Object> filter = new HashMap<>();

    public void setWorksId(int worksId) {
        filter.put("id", worksId);
    }

    /**
     * 加载创作详情
     */
    public void loadWorksDetail() {
        HttpService.getInstance().getAPI(WorksApi.class)
                .getWorks(filter)
                .enqueue(new MvpCallBack<List<Works>>(getView()) {
                    @Override
                    public void onSuccess(List<Works> responseData) {
                        Works works = responseData.get(0);
                        getView().setAuthor(works.getAuthor());
                        getView().setCreateTime(works.getCreateTime());
                        getView().setTitle(works.getTitle());
                        getView().setContent(works.getContent().toString());
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
