package pers.geolo.guitarworld.presenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.WorksApi;
import pers.geolo.guitarworld.network.callback.MvpNetworkCallBack;
import pers.geolo.guitarworld.view.*;

public class WorksPresenter extends BasePresenter {

   public static List<Works> worksList = new ArrayList<>();
    /**
     * 发布创作
     *
     * @param publishWorksView 发布创作视图
     */
    public static void publishWorks(PublishWorksView publishWorksView) {
        Works works = new Works();
        String username = DAOService.getInstance().getCurrentLogInfo().getUsername();
        works.setAuthor(username);
        works.setCreateTime(new Date());
        works.setTitle(publishWorksView.getWorksTitle());
        works.setContent(publishWorksView.getContent());
        // 发送发布请求
        HttpService.getInstance().getAPI(WorksApi.class)
                .publishWorks(works)
                .enqueue(new MvpNetworkCallBack<Void>(publishWorksView) {
                    @Override
                    public void onSuccess(Void responseData) {
                        publishWorksView.showToast("发布成功");
                        // 跳转至主视图
                        publishWorksView.toMainView();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        publishWorksView.showToast(errorMessage);
                    }

                    @Override
                    public void onFailure() {
                        publishWorksView.showToast("网络错误");
                    }
                });
    }

    /**
     * 加载创作列表
     *
     * @param worksListView 创作列表视图
     */
    public static void loadingWorksList(WorksListView worksListView) {
        // 显示刷新控件
        worksListView.showRefreshing();
        // 发送获取创作列表请求
        HttpService.getInstance().getAPI(WorksApi.class)
                .getAllWorks().enqueue(new MvpNetworkCallBack<List<Works>>(worksListView) {
            @Override
            public void onSuccess(List<Works> responseData) {
                // 设置数据
                worksList = responseData;
                worksListView.onBindView(() -> worksList.size(), (view, index) -> {
                    Works works = worksList.get(index);
                    view.setId(works.getId());
                    view.setAuthor(works.getAuthor());
                    view.setCreateTime(works.getCreateTime());
                    view.setTitle(works.getTitle());
                    view.setContent(works.getContent());
                });
                // 隐藏刷新控件
                worksListView.hideRefreshing();
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                worksListView.showToast(errorMessage);
            }

            @Override
            public void onFailure() {
                worksListView.showToast("网络错误");
            }
        });
    }

    /**
     * 显示创作条目选项
     *
     * @param worksListItemView 创作条目选项视图
     */
    public static void showWorksItemOption(WorksListItemView worksListItemView) {
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
     * @param worksListItemView 创作条目选项视图
     */
    public static void removeWorks(WorksListItemView worksListItemView) {
        // 发送删除创作请求
        HttpService.getInstance().getAPI(WorksApi.class)
                .removeWorks(worksListItemView.getWorksId())
                .enqueue(new MvpNetworkCallBack<Void>(worksListItemView) {
                    @Override
                    public void onSuccess(Void responseData) {
                        worksListItemView.showToast("删除成功");
                        worksList.remove(worksListItemView.getIndex());
                        worksListItemView.remove();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        worksListItemView.showToast("删除失败");
                    }

                    @Override
                    public void onFailure() {
                        worksListItemView.showToast("网络错误");
                    }
                });
    }

    /**
     * 加载创作详情
     *
     * @param worksDetailView 创作详情视图
     */
    public static void loadingWorksDetail(WorksListDetailView worksDetailView) {
        HttpService.getInstance().getAPI(WorksApi.class)
                .getWorks(worksDetailView.getWorksId())
                .enqueue(new MvpNetworkCallBack<Works>(worksDetailView) {
                    @Override
                    public void onSuccess(Works responseData) {
//                        worksDetailView.setAuthor(responseData.getAuthor());
//                        worksDetailView.setCreateTime(responseData.getCreateTime());
//                        worksDetailView.setTitle(responseData.getTitle());
//                        worksDetailView.setContent(responseData.getContent().toString());
                        worksDetailView.hideRefreshing();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        worksDetailView.showToast("网络错误！");
                        worksDetailView.hideRefreshing();
                    }

                    @Override
                    public void onFailure() {
                        worksDetailView.showToast("网络错误！");
                        worksDetailView.hideRefreshing();
                    }
                });
    }
}
