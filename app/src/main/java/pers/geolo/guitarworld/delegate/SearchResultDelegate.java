package pers.geolo.guitarworld.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.fragmentationtest.delegate.BaseDelegate;

public class SearchResultDelegate extends BaseDelegate {

    @Override
    public Object getLayout() {
        return R.layout.delegate_search_result;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        start(UserListDelegate.newInstance("true", null, null));
    }
}
