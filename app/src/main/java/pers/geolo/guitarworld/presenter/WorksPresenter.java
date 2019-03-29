package pers.geolo.guitarworld.presenter;

import java.util.List;

import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.WorksAPI;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.view.WorksItemOptionView;
import pers.geolo.guitarworld.view.WorksItemView;
import pers.geolo.guitarworld.view.WorksListView;
import pers.geolo.guitarworld.view.WorsDetailView;

public class WorksPresenter extends BasePresenter {

    public static void loadingWorksList(WorksListView worksListView) {
        worksListView.showRefreshing();
        HttpService.getInstance().getAPI(WorksAPI.class)
                .getAllWorks().enqueue(new BaseCallback<List<Works>>() {
            @Override
            public void onSuccess(List<Works> responseData) {
                worksListView.setDataList(responseData);
                worksListView.hideRefreshing();
            }

            @Override
            public void onError(int errorCode, String errorMessage) {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    public static void loadingWorksItem(WorksItemView worksItemView) {
        Works works = worksItemView.getWorks();
        worksItemView.setAuthor(works.getAuthor());
        worksItemView.setCreateTime(works.getCreateTime());
        worksItemView.setTitle(works.getTitle());
        worksItemView.setContent(works.getContent().toString());
    }

    public static void showWorksItemOption(WorksItemOptionView worksItemOptionView) {
        String[] authorOptions = new String[]{"删除"};
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

    public static void removeWorks(WorksItemOptionView worksItemOptionView) {
        HttpService.getInstance().getAPI(WorksAPI.class)
                .removeWorks(worksItemOptionView.getWorks().getId()).enqueue(new BaseCallback<Void>() {
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

    public static void loadingWorksDetail(WorsDetailView worsDetailView) {
        HttpService.getInstance().getAPI(WorksAPI.class)
                .getWorks(worsDetailView.getWorksId()).enqueue(new BaseCallback<Works>() {
            @Override
            public void onSuccess(Works responseData) {
                worsDetailView.setAuthor(responseData.getAuthor());
                worsDetailView.setCreateTime(responseData.getCreateTime());
                worsDetailView.setTitle(responseData.getTitle());
                worsDetailView.setContent(responseData.getContent().toString());
                worsDetailView.loadingWorksDetailOnSuccess();
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                worsDetailView.showToast("网络错误！");
                worsDetailView.hideRefreshing();
            }

            @Override
            public void onFailure() {
                worsDetailView.showToast("网络错误！");
                worsDetailView.hideRefreshing();
            }
        });
    }
}
