package pers.geolo.guitarworld.presenter.works;

import java.util.Date;

import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.callback.MvpCallBack;
import pers.geolo.guitarworld.presenter.base.BasePresenter;
import pers.geolo.guitarworld.ui.base.CustomContext;
import pers.geolo.guitarworld.view.PublishWorksView;

public class PublishWorksPresenter extends BasePresenter<PublishWorksView> {

    Works works = new Works();

    /**
     * 发布创作
     */
    public void publishWorks() {
        String username = CustomContext.getInstance().getLogInfo().getUsername();
        works.setAuthor(username);
        works.setCreateTime(new Date());
        works.setTitle(getView().getWorksTitle());
        works.setContent(getView().getContent());
        // 发送发布请求
        worksApi.publishWorks(works).enqueue(new MvpCallBack<Void>(getView()) {
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

    public void addImage(String data) {
        works.addImage(data);
    }
}
