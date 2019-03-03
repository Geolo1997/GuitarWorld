package pers.geolo.guitarworld.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.activity.SearchResultActivity;
import pers.geolo.guitarworld.service.SearchService;
import pers.geolo.guitarworld.util.SingletonHolder;

public class DiscoverFragment extends BaseFragment {

    private SearchService searchService;

    @BindView(R.id.et_search)
    EditText etSearch;

    @Override
    protected int getContentView() {
        return R.layout.fragment_discover;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        searchService = SingletonHolder.getInstance(SearchService.class);
        return super.onCreateView(inflater, container, savedInstanceState);
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
