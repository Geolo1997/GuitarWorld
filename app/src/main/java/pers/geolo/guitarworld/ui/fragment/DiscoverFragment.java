package pers.geolo.guitarworld.ui.fragment;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.ui.activity.SearchResultActivity;
import pers.geolo.guitarworld.base.BaseFragment;

public class DiscoverFragment extends BaseFragment {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.bt_search)
    Button btSearch;

    @Override
    protected int getContentView() {
        return R.layout.fragment_discover;
    }

    @OnClick(R.id.bt_search)
    public void onBtSearchClicked() {
        // 获取输入文本
        String searchText = etSearch.getText().toString();
        Intent intent = new Intent(getActivity(), SearchResultActivity.class);
        //
        intent.putExtra("searchText", searchText);
        startActivity(intent);
    }
}
