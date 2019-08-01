package pers.geolo.guitarworld.delegate.works;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import butterknife.BindView;
import butterknife.OnClick;
import org.greenrobot.eventbus.EventBus;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BaseDelegate;
import pers.geolo.guitarworld.entity.event.GetImageEvent;
import pers.geolo.guitarworld.ui.PhotoOptionDialog;
import pers.geolo.guitarworld.util.GlideUtils;
import pers.geolo.guitarworld.util.PhotoUtils;
import pers.geolo.guitarworld.util.ToastUtils;

import java.io.File;

/**
 * 增加图片页面
 *
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/28
 */
public class AddImageDelegate extends BaseDelegate implements PhotoUtils.Callback {

    @BindView(R.id.add_image_button)
    ImageButton addImageButton;
    @BindView(R.id.close_button)
    ImageButton closeButton;

    private File image;

    @Override
    public Object getLayout() {
        return R.layout.add_image;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    public File getImage() {
        return image;
    }

    @OnClick(R.id.add_image_button)
    public void addImage() {
        if (image != null) { // 有图片
            // TODO 编辑图片
        } else { // 没有图片
            PhotoOptionDialog.show(getChildFragmentManager(), v -> {
                PhotoUtils.openCamera(getActivity(), this);
            }, v -> {
                PhotoUtils.openAlbum(getActivity(), this);
            });
        }
    }

    @OnClick(R.id.close_button)
    public void close() {
        image = null;
        addImageButton.setImageResource(R.drawable.ic_add_48);
        closeButton.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(File photo) {
        image = photo;
        GlideUtils.load(getContext(), photo, addImageButton);
        closeButton.setVisibility(View.VISIBLE);
        EventBus.getDefault().post(new GetImageEvent(image));
    }

    @Override
    public void onFailure(PhotoUtils.FailureType failureType) {
        ToastUtils.showErrorToast(getContext(), failureType.name());
    }
}
