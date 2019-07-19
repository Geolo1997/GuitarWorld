package pers.geolo.guitarworld.delegate.works;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;
import butterknife.OnClick;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;

/**
 * 增加作品选项
 *
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/15
 */
public class AddWorksOptionDelegate extends BaseDelegate {

    @Override
    public Object getLayout() {
        return R.layout.add_works_option;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick(R.id.add_image_text_works_button)
    public void onAddImageTextWorksButtonClicked() {
        getContainerActivity().start(new PublishImageTextDelegate());
    }

    @OnClick(R.id.fab_2)
    public void onFab2Clicked() {
        getContainerActivity().start(new PublishVideoWorksDelegate());
    }

    @OnClick(R.id.fab_3)
    public void onFab3Clicked() {
        Toast.makeText(getContext(), "fab3", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fab_menu)
    public void onFabMenuClicked() {
        Toast.makeText(getContext(), "menu", Toast.LENGTH_SHORT).show();
    }
}
