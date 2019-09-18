package pers.geolo.guitarworld.controller.index;

import android.content.Context;
import android.view.View;
import butterknife.BindView;
import com.freegeek.android.materialbanner.MaterialBanner;
import com.freegeek.android.materialbanner.holder.Holder;
import com.freegeek.android.materialbanner.holder.ViewHolderCreator;
import com.freegeek.android.materialbanner.view.indicator.CirclePageIndicator;
import org.microview.core.ViewController;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.entity.Information;
import pers.geolo.guitarworld.model.InformationModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/8/1
 */
public class BannerController extends BaseController {

    @BindView(R.id.banner)
    MaterialBanner<Information> banner;

    @Override
    protected int getLayout() {
        return R.layout.banner;
    }

    @Override
    public void initView(ViewParams viewParams) {
        List<Information> dataList = (List<Information>) viewParams.get("informationList");
        banner.setPages(new ViewHolderCreator<Holder<Information>>() {
            @Override
            public Holder<Information> createHolder() {
                return new Holder<Information>() {

                    private ViewController controller;

                    @Override
                    public View createView(Context context) {
                        controller = new BannerItemController();
                        controller.createView(banner);
                        return controller.getView();
                    }

                    @Override
                    public void updateUI(Context context, int i, Information information) {
                        controller.initView(new ViewParams("information", information));
                    }
                };
            }
        }, dataList)
                .setIndicator(new CirclePageIndicator(getActivity()));
    }
}
