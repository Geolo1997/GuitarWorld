package pers.geolo.guitarworld.controller.index;

import butterknife.BindView;
import com.freegeek.android.materialbanner.MaterialBanner;
import com.freegeek.android.materialbanner.view.indicator.CirclePageIndicator;
import com.thefinestartist.finestwebview.FinestWebView;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.entity.DataCallback;
import pers.geolo.guitarworld.entity.Information;
import pers.geolo.guitarworld.model.InformationModel;
import pers.geolo.guitarworld.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/8/1
 */

// TODO Banner使用Controller替换
public class BannerController extends BaseController {

    @BindView(R.id.banner)
    MaterialBanner<Information> banner;

    private InformationModel informationModel = BeanFactory.getBean(InformationModel.class);
    private NetBannerAdapter.NetImageHolderCreator viewHolderCreator;
    private List<Information> dataList;

    @Override
    protected int getLayout() {
        return R.layout.banner;
    }

    @Override
    public void initView(ViewParams viewParams) {
        dataList = new ArrayList<>();
        viewHolderCreator = new NetBannerAdapter.NetImageHolderCreator();
        banner.setPages(viewHolderCreator, dataList)
                .setIndicator(new CirclePageIndicator(getActivity()));
        banner.setOnItemClickListener(new MaterialBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                String url = dataList.get(i).getDetailUrl();
                new FinestWebView.Builder(getActivity()).show(url);
            }
        });
        informationModel.getBannerInformation(new DataCallback<List<Information>>() {
            @Override
            public void onReturn(List<Information> information) {
                dataList = information;
                banner.setPages(viewHolderCreator, dataList);
            }

            @Override
            public void onError(String message) {
                ToastUtils.showErrorToast(getActivity(), message);
            }
        });
    }

}
