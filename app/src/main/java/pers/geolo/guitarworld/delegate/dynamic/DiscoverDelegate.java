package pers.geolo.guitarworld.delegate.dynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;

public class DiscoverDelegate extends BaseDelegate {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.bt_search)
    Button btSearch;

    @OnClick(R.id.bt_search)
    public void onBtSearchClicked() {
        // 获取输入文本
        String searchText = etSearch.getText().toString();
        getDelegateActivity().start(new SearchResultDelegate());
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_discover;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
