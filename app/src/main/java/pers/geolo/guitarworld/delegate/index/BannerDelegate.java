package pers.geolo.guitarworld.delegate.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import butterknife.BindView;
import com.freegeek.android.materialbanner.MaterialBanner;
import com.freegeek.android.materialbanner.view.indicator.CirclePageIndicator;
import com.thefinestartist.finestwebview.FinestWebView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.base.BeanFactory;
import pers.geolo.guitarworld.entity.DataListener;
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
public class BannerDelegate extends BaseDelegate {

    @BindView(R.id.banner)
    MaterialBanner<Information> banner;

    private InformationModel informationModel = BeanFactory.getBean(InformationModel.class);
    private NetBannerAdapter.NetImageHolderCreator viewHolderCreator;
    private List<Information> dataList;

    @Override
    public Object getLayout() {
        return R.layout.banner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        dataList = new ArrayList<>();
        viewHolderCreator = new NetBannerAdapter.NetImageHolderCreator();
        banner.setPages(viewHolderCreator, dataList)
                .setIndicator(new CirclePageIndicator(getContext()));
        banner.setOnItemClickListener(new MaterialBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                String url = dataList.get(i).getDetailUrl();
                new FinestWebView.Builder(getContext()).show(url);
            }
        });
        initDataList();
    }

    private void initDataList() {
        informationModel.getBannerInformation(new DataListener<List<Information>>() {
            @Override
            public void onReturn(List<Information> information) {
                dataList = information;
                banner.setPages(viewHolderCreator, dataList);
            }

            @Override
            public void onError(String message) {
                ToastUtils.showErrorToast(getContext(), message);
            }
        });
    }
}
