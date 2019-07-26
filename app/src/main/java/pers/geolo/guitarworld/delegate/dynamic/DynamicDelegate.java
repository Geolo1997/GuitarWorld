package pers.geolo.guitarworld.delegate.dynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.base.BeanFactory;
import pers.geolo.guitarworld.delegate.works.PublishImageTextDelegate;
import pers.geolo.guitarworld.delegate.works.PublishVideoWorksDelegate;
import pers.geolo.guitarworld.delegate.works.WorksListDelegate;
import pers.geolo.guitarworld.model.AuthModel;

import java.util.HashMap;

public class DynamicDelegate extends BaseDelegate {

//    @BindView(R.id.publish_works)
//    FloatingActionButton publishWorks;
    @BindView(R.id.floating_menu)
    FloatingActionMenu floatingMenu;

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);

    @Override
    public Object getLayout() {
        return R.layout.delegate_dynamic;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
//        loadRootFragment(R.id.add_layout, new PublishWorksMenuDelegate());
        initWorksListDelegate();
    }

    private void initWorksListDelegate() {
        HashMap<String, Object> filter = new HashMap<>();
        filter.put("username", authModel.getCurrentLoginUser().getUsername());
        loadRootFragment(R.id.ll_fragment, WorksListDelegate.newInstance(filter));
    }

    @OnClick(R.id.publish_image_text_works)
    public void onPublishImageTextWorksClicked() {
        getContainerActivity().start(new PublishImageTextDelegate());
    }

    @OnClick(R.id.publish_video_works)
    public void onPublishVideoWorksClicked() {
        getContainerActivity().start(new PublishVideoWorksDelegate());
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        floatingMenu.close(false);
    }
}
