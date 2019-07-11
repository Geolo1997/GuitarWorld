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
import pers.geolo.guitarworld.delegate.music.MusicDetailDelegate;
import pers.geolo.guitarworld.delegate.music.MusicDiscoverNavigationDelegate;

public class DiscoverDelegate extends BaseDelegate {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.bt_search)
    Button btSearch;

    @OnClick(R.id.bt_search)
    public void onBtSearchClicked() {
        // 获取输入文本
        String searchText = etSearch.getText().toString();
        getContainerActivity().start(new SearchResultDelegate());
    }

    // TODO test
    @OnClick(R.id.bt_show_music)
    public void show() {
        getContainerActivity().start(new MusicDetailDelegate());
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_discover;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        loadRootFragment(R.id.music_discover_navigation, new MusicDiscoverNavigationDelegate());
    }
}
