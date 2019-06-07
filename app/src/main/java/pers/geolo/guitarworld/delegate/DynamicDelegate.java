package pers.geolo.guitarworld.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.fragmentationtest.delegate.BaseDelegate;
import pers.geolo.guitarworld.model.AuthModel;

public class DynamicDelegate extends BaseDelegate {

    @BindView(R.id.publish_works)
    FloatingActionButton publishWorks;

    @Override
    public Object getLayout() {
        return R.layout.fragment_dynamic;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initWorksListDelegate();
    }

    private void initWorksListDelegate() {
        loadRootFragment(R.id.ll_fragment,
                WorksListDelegate.newInstance(AuthModel.getCurrentLoginUser().getUsername(), null));
    }

    @OnClick(R.id.publish_works)
    public void onBtPublishWorksClick() {
//        startActivity(PublishDelegate.class);
    }
}
