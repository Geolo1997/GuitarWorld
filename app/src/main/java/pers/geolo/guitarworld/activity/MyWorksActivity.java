package pers.geolo.guitarworld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import butterknife.BindView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.adapter.WorksViewAdapter;
import pers.geolo.guitarworld.helper.LinerListViewHelper;
import pers.geolo.guitarworld.model.Works;
import pers.geolo.guitarworld.network.BaseCallback;
import pers.geolo.guitarworld.network.HttpUtils;
import pers.geolo.guitarworld.service.UserService;
import pers.geolo.guitarworld.util.SingletonHolder;

import java.util.List;

public class MyWorksActivity extends BaseActivity {

    private WorksViewAdapter worksViewAdapter;

    @BindView(R.id.vg_liner_list)
    View linerListView;

//    @BindView(R.id.rv_myWorks)
//    RecyclerView rvMyWorks;
//    @BindView(R.id.srl_refresh)
//    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_works);


        LinerListViewHelper<Works> helper = new LinerListViewHelper<Works>(linerListView) {
            @Override
            protected void onItemClick(View view, int position) {
                // 跳转到作品详情页
                Intent intent = new Intent(MyWorksActivity.this, WorksDetailActivity.class);
                intent.putExtra("id", getList().get(position).getId());
                startActivity(intent);
            }

            @Override
            protected void onUpdate(BaseCallback<List<Works>> callback) {
                String author = SingletonHolder.getInstance(UserService.class).getUsername();
                HttpUtils.getWorksList(author, callback);
            }
        };
        helper.setAdapter(worksViewAdapter.getClass());
        helper.update();
//        updateWorksList();
    }
//
//    public void updateWorksList() {
//        String anthor = SingletonHolder.getInstance(LoginManager.class).getLoginUser().getUsername();
//        HttpUtils.getWorksList(anthor, new BaseCallback<List<Works>>() {
//            @Override
//            public void onSuccess(List<Works> data) {
//                worksList.clearPassword();
//                worksList.addAll(data);
//                worksViewAdapter.notifyDataSetChanged();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//
//            @Override
//            public void onError(String message) {
//                Log.d(TAG, message);
//                toast(message);
//            }
//
//            @Override
//            public void onFailure() {
//            }
//        });
//    }

}
