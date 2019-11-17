package pers.geolo.guitarworld.controller.index;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import org.microview.core.ViewParams;
import org.microview.support.MicroviewRVAdapter;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.controller.user.UserItemController;
import pers.geolo.guitarworld.entity.DataCallback;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.model.UserModel;
import pers.geolo.guitarworld.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SearchResultController extends BaseController {

    @BindView(R.id.user_list_layout)
    RecyclerView userListLayout;

    private MicroviewRVAdapter adapter;
    private UserModel userModel = BeanFactory.getBean(UserModel.class);

    @Override
    protected int getLayout() {
        return R.layout.search_result;
    }

    @Override
    public void initView(ViewParams viewParams) {
        userListLayout.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MicroviewRVAdapter(UserItemController.class, new ArrayList<>());
        userListLayout.setAdapter(adapter);
        loadUserList();
    }

    private void loadUserList() {
        userModel.getAllUser(new DataCallback<List<User>>() {
            @Override
            public void onReturn(List<User> users) {
                List<ViewParams> params = users.stream()
                        .map(new Function<User, ViewParams>() {
                            @Override
                            public ViewParams apply(User user) {
                                return new ViewParams("user", user);
                            }
                        })
                        .collect(Collectors.toList());
                adapter.updateDataList(params);
            }

            @Override
            public void onError(String message) {
                ToastUtils.showErrorToast(getActivity(), message);
            }
        });
    }
}
