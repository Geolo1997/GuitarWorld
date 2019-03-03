package pers.geolo.guitarworld.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.adapter.UserAdapter;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.network.BaseCallback;
import pers.geolo.guitarworld.service.UserService;
import pers.geolo.guitarworld.util.SingletonHolder;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends BaseActivity {

    List<User> userList;
    UserAdapter adapter;
    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        userList = new ArrayList<>();
        adapter = new UserAdapter(userList);
        rv.setAdapter(adapter);

        update();
    }

    private void update() {
        SingletonHolder.getInstance(UserService.class).getAllUsers(new BaseCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> data) {
                userList.clear();
                userList.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {

            }

            @Override
            public void onFailure() {

            }
        });
    }
}
