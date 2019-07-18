package pers.geolo.guitarworld.delegate.dynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuGravity;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.base.BeanFactory;
import pers.geolo.guitarworld.delegate.works.AddWorksOptionDelegate;
import pers.geolo.guitarworld.delegate.works.WorksListDelegate;
import pers.geolo.guitarworld.model.AuthModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DynamicDelegate extends BaseDelegate {

//    @BindView(R.id.publish_works)
//    FloatingActionButton publishWorks;

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);

    @Override
    public Object getLayout() {
        return R.layout.delegate_dynamic;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        loadRootFragment(R.id.add_layout, new AddWorksOptionDelegate());
        initWorksListDelegate();
    }

    private void initWorksListDelegate() {
        HashMap<String, Object> filter = new HashMap<>();
        filter.put("username", authModel.getCurrentLoginUser().getUsername());
        loadRootFragment(R.id.ll_fragment, WorksListDelegate.newInstance(filter));
    }

//    @OnClick(R.id.publish_works)
//    public void onBtPublishWorksClick() {
////        getContainerActivity().start(new PublishDelegate());
//        showMenu();
//    }

    public void showMenu() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize(150);
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(true);
        menuParams.setGravity(MenuGravity.END);
        ContextMenuDialogFragment fragment = ContextMenuDialogFragment.newInstance(menuParams);
        fragment.show(getChildFragmentManager(), ContextMenuDialogFragment.TAG);
    }

    public List<MenuObject> getMenuObjects() {
        // init MenuObject
        List<MenuObject> menuObjects = new ArrayList<>();
        MenuObject articleMenu = new MenuObject("发动态");
        articleMenu.setBgDrawable(getContext().getDrawable(R.drawable.ic_dynamic));
        menuObjects.add(articleMenu);
        MenuObject teachMenu = new MenuObject("发教程");
        teachMenu.setDrawable(getContext().getDrawable(R.drawable.ic_dynamic));
        menuObjects.add(teachMenu);
        MenuObject showMenu = new MenuObject("发表演");
        showMenu.setDrawable(getContext().getDrawable(R.drawable.ic_dynamic));
        menuObjects.add(showMenu);
        return menuObjects;
    }
}
