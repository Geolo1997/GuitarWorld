package pers.geolo.guitarworld.controller.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import java.util.HashMap;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.user.UserListController;
import pers.geolo.guitarworld.controller.base.BaseController;

public class SearchResultController extends BaseController {

    @Override
    public Object getLayoutView() {
        return R.layout.controller_search_result;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        HashMap<String, Object> filter = new HashMap<>();
        filter.put("all", true);
        loadRootFragment(R.id.works_list_layout, UserListController.newInstance(filter));
    }
}
