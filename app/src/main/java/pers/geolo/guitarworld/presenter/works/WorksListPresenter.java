package pers.geolo.guitarworld.presenter.works;

import java.util.HashMap;
import java.util.List;

import pers.geolo.guitarworld.ui.base.BaseListPresenter;
import pers.geolo.guitarworld.ui.base.CustomContext;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.WorksApi;
import pers.geolo.guitarworld.network.callback.MvpCallBack;
import pers.geolo.guitarworld.view.WorksListItemView;
import pers.geolo.guitarworld.view.WorksListView;

public class WorksListPresenter extends BaseListPresenter<WorksListView, WorksListItemView, Works> {

    /**
     * 加载创作列表
     */
    public void loadWorksList() {
        // 显示刷新控件
        getView().showRefreshing();
        // 发送获取创作列表请求
        HttpService.getInstance().getAPI(WorksApi.class)
                .getWorks(getFilter())
                .enqueue(new MvpCallBack<List<Works>>(getView()) {
                    @Override
                    public void onSuccess(List<Works> responseData) {
                        // 设置数据
                        addAllItemView(responseData);
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
        int worksId = getDataList().get(index).getId();
        getView().toWorksDetailView(worksId);
    }

    /**
     * 显示创作条目选项
     *
     * @param index
     */
    public void showWorksItemOption(int index) {
        // 作者权限选项
        String[] authorOptions = new String[]{"删除"};
        // 浏览者权限选项
        String[] viewerOptions = new String[]{};

        String username = getItemView(index).getUsername();
        String currentUsername = CustomContext.getInstance().getLogInfo().getUsername();

        String[] options;
        if (currentUsername.equals(username)) {
            options = authorOptions;
        } else {
            options = viewerOptions;
        }
        getItemView(index).showOptions(options);
    }

    /**
     * 删除创作
     *
     * @param index
     */
    public void removeWorks(int index) {
        HashMap<String, Object> filter = new HashMap<>();
        filter.put("id", getDataList().get(index).getId());
        // 发送删除创作请求
        HttpService.getInstance().getAPI(WorksApi.class)
                .removeWorks(filter)
                .enqueue(new MvpCallBack<Void>(getView()) {
                    @Override
                    public void onSuccess(Void responseData) {
                        getDataList().remove(index);
                        getView().removeItemView(index);
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

    @Override
    public void onBindItemView(WorksListItemView itemView, int index) {
        Works works = getDataList().get(index);
        itemView.setAuthor(works.getAuthor());
        itemView.setCreateTime(works.getCreateTime());
        itemView.setTitle(works.getTitle());
        itemView.setContent(works.getContent());
    }
}
