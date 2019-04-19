package pers.geolo.guitarworld.presenter.base;

import pers.geolo.guitarworld.Config;
import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.entity.UserRelation;
import pers.geolo.guitarworld.network.api.*;
import pers.geolo.guitarworld.view.base.BaseView;

public class BasePresenter<V extends BaseView> {

    public static final String TAG = BasePresenter.class.getSimpleName();

    private V view;

    public void bind(V view) {
        this.view = view;
    }

    public void unBind() {
        this.view = null;
    }

    public V getView() {
        return view;
    }


    protected DAOService daoService = DAOService.getInstance();
    protected AuthApi authApi = Config.getService(AuthApi.class);
    protected UserApi userApi = Config.getService(UserApi.class);
    protected UserRelationApi userRelationApi = Config.getService(UserRelationApi.class);
    protected WorksApi worksApi = Config.getService(WorksApi.class);
    protected CommentApi commentApi = Config.getService(CommentApi.class);
    protected FileApi fileApi = Config.getService(FileApi.class);
}
