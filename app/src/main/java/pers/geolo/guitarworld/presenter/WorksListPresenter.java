package pers.geolo.guitarworld.presenter;

import java.util.List;

import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.WorksApi;
import pers.geolo.guitarworld.network.callback.MvpNetworkCallBack;
import pers.geolo.guitarworld.view.WorksDetailView;
import pers.geolo.guitarworld.view.WorksListItemView;
import pers.geolo.guitarworld.view.WorksListView;

public class WorksListPresenter extends BasePresenter<WorksListView> {

    private List<Works> worksList;

    /**
     * 加载创作列表
     */
    public void loadingWorksList() {
        // 显示刷新控件
        getView().showRefreshing();
        // 发送获取创作列表请求
        HttpService.getInstance().getAPI(WorksApi.class)
                .getAllWorks().enqueue(new MvpNetworkCallBack<List<Works>>(getView()) {
            @Override
            public void onSuccess(List<Works> responseData) {
                // 设置数据
                worksList = responseData;
                getView().onBindView(() -> worksList.size(), (view, index) -> {
                    Works works = worksList.get(index);
                    view.setId(works.getId());
                    view.setAuthor(works.getAuthor());
                    view.setCreateTime(works.getCreateTime());
                    view.setTitle(works.getTitle());
                    view.setContent(works.getContent());
                });
                // 隐藏刷新控件
                getView().hideRefreshing();
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

    public void toWorksDetail(int index) {
        int worksId = worksList.get(index).getId();
        getView().toWorksDetailView(worksId);
    }

    /**
     * 显示创作条目选项
     *
     * @param worksListItemView 创作条目选项视图
     */
    public void showWorksItemOption(WorksListItemView worksListItemView) {
        // 作者权限选项
        String[] authorOptions = new String[]{"删除"};
        // 浏览者权限选项
        String[] viewerOptions = new String[]{};

        String username = worksListItemView.getUsername();
        String currentUsername = DAOService.getInstance().getCurrentLogInfo().getUsername();

        String[] options;
        if (currentUsername.equals(username)) {
            options = authorOptions;
        } else {
            options = viewerOptions;
        }
        worksListItemView.showOptions(options);
    }

    /**
     * 删除创作
     *
     * @param index
     */
    public void removeWorks(int index) {
        // 发送删除创作请求
        HttpService.getInstance().getAPI(WorksApi.class)
                .removeWorks(worksList.get(index).getId())
                .enqueue(new MvpNetworkCallBack<Void>(getView()) {
                    @Override
                    public void onSuccess(Void responseData) {
                        worksList.remove(index);
                        getView().remove(index);
                        getView().showToast("删除成功");
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        getView().showToast("删除失败");
                    }

                    @Override
                    public void onFailure() {
                        getView().showToast("网络错误");
                    }
                });
    }


}
