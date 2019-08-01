package pers.geolo.guitarworld.delegate.index;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.freegeek.android.materialbanner.holder.Holder;
import com.freegeek.android.materialbanner.holder.ViewHolderCreator;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.entity.Information;
import pers.geolo.guitarworld.util.GlideUtils;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/8/1
 */
public class NetBannerAdapter {

    public static class NetImageHolder implements Holder<Information> {

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.title_text)
        TextView titleText;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            ButterKnife.bind(this, view);
            return view;
        }

        @Override
        public void updateUI(Context context, int i, Information information) {
            titleText.setText(information.getTitle());
            GlideUtils.load(context, information.getImageUrl(), image);
        }
    }

    public static class NetImageHolderCreator implements ViewHolderCreator<NetImageHolder> {

        @Override
        public NetImageHolder createHolder() {
            return new NetImageHolder();
        }
    }
}

