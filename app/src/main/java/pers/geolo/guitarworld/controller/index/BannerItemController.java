package pers.geolo.guitarworld.controller.index;

import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.thefinestartist.finestwebview.FinestWebView;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.entity.Information;
import pers.geolo.guitarworld.util.GlideUtils;

import java.io.File;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/9/18
 */
public class BannerItemController extends BaseController {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.title_text)
    TextView titleText;

    private Information information;

    @Override
    protected int getLayout() {
        return R.layout.banner_item;
    }

    @Override
    public void initView(ViewParams viewParams) {
        information = (Information) viewParams.get("information");
        titleText.setText(information.getTitle());
        GlideUtils.load(getActivity(), information.getImageUrl(), image);
    }

    @OnClick(R.id.banner_item_layout)
    public void onBannerItemClicked() {
        String detailUrl = information.getDetailUrl();
        new FinestWebView.Builder(getActivity()).show(detailUrl);
    }
}
