package pers.geolo.guitarworld.delegate.dynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import java.util.HashMap;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.works.PublishDelegate;
import pers.geolo.guitarworld.delegate.works.WorksListDelegate;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.model.AuthModel;

public class DynamicDelegate extends BaseDelegate {

    @BindView(R.id.publish_works)
    FloatingActionButton publishWorks;

    @Override
    public Object getLayout() {
        return R.layout.delegate_dynamic;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initWorksListDelegate();
    }

    private void initWorksListDelegate() {
        HashMap<String, Object> filter = new HashMap<>();
        filter.put("username", AuthModel.getCurrentLoginUser().getUsername());
        loadRootFragment(R.id.ll_fragment, WorksListDelegate.newInstance(filter));
    }

    @OnClick(R.id.publish_works)
    public void onBtPublishWorksClick() {
        getDelegateActivity().start(new PublishDelegate());
    }
}
