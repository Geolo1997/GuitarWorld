package pers.geolo.guitarworld.presenter;

import java.util.Date;
import java.util.List;

import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.WorksApi;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.network.callback.MvpNetworkCallBack;
import pers.geolo.guitarworld.view.*;

public class WorksPresenter extends BasePresenter {

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
                worksListView.setDataList(responseData);
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
     * 加载创作条目
     *
     * @param worksItemView 创作条目视图
     */
    public static void loadingWorksItem(WorksItemView worksItemView) {
        Works works = worksItemView.getWorks();
        worksItemView.setAuthor(works.getAuthor());
        worksItemView.setCreateTime(works.getCreateTime());
        worksItemView.setTitle(works.getTitle());
        worksItemView.setContent(works.getContent().toString());
    }

    /**
     * 显示创作条目选项
     *
     * @param worksItemOptionView 创作条目选项视图
     */
    public static void showWorksItemOption(WorksItemOptionView worksItemOptionView) {
        // 作者权限选项
        String[] authorOptions = new String[]{"删除"};
        // 浏览者权限选项
        String[] viewerOptions = new String[]{};

        Works works = worksItemOptionView.getWorks();
        String currentUsername = DAOService.getInstance().getCurrentLogInfo().getUsername();

        String[] options;
        if (currentUsername.equals(works.getAuthor())) {
            options = authorOptions;
        } else {
            options = viewerOptions;
        }
        worksItemOptionView.showOptions(options);
    }

    /**
     * 删除创作
     *
     * @param worksItemOptionView 创作条目选项视图
     */
    public static void removeWorks(WorksItemOptionView worksItemOptionView) {
        // 发送删除创作请求
        HttpService.getInstance().getAPI(WorksApi.class)
                .removeWorks(worksItemOptionView.getWorks().getId())
                .enqueue(new MvpNetworkCallBack<Void>(worksItemOptionView) {
                    @Override
                    public void onSuccess(Void responseData) {
                        worksItemOptionView.showToast("删除成功");
                        worksItemOptionView.removeWorks();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        worksItemOptionView.showToast("删除失败");
                    }

                    @Override
                    public void onFailure() {
                        worksItemOptionView.showToast("网络错误");
                    }
                });
    }

    /**
     * 加载创作详情
     * @param worksDetailView 创作详情视图
     */
    public static void loadingWorksDetail(WorksDetailView worksDetailView) {
        HttpService.getInstance().getAPI(WorksApi.class)
                .getWorks(worksDetailView.getWorksId())
                .enqueue(new MvpNetworkCallBack<Works>(worksDetailView) {
                    @Override
                    public void onSuccess(Works responseData) {
                        worksDetailView.setAuthor(responseData.getAuthor());
                        worksDetailView.setCreateTime(responseData.getCreateTime());
                        worksDetailView.setTitle(responseData.getTitle());
                        worksDetailView.setContent(responseData.getContent().toString());
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
