package pers.geolo.guitarworld.delegate.dynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import java.util.HashMap;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.user.UserListDelegate;
import pers.geolo.guitarworld.base.BaseDelegate;

public class SearchResultDelegate extends BaseDelegate {

    @Override
    public Object getLayout() {
        return R.layout.delegate_search_result;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        HashMap<String, Object> filter = new HashMap<>();
        filter.put("all", true);
        loadRootFragment(R.id.ll_fragment, UserListDelegate.newInstance(filter));
    }
}
