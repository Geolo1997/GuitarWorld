package pers.geolo.guitarworld.test;

import android.widget.ImageButton;
import butterknife.BindView;
import butterknife.OnClick;
import pers.geolo.guitarworld.R;

import java.io.File;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/28
 */
public class AddImageComponent extends AndroidComponent {

    @BindView(R.id.add_image_button)
    ImageButton addImageButton;
    @BindView(R.id.close_button)
    ImageButton closeButton;

    private File image;

    @Override
    public int getLayout() {
        return R.layout.add_image;
    }

    @Override
    public void init() {

    }

    @OnClick(R.id.add_image_button)
    public void addImage() {
        if (image != null) { // 有图片
            // TODO 编辑图片
        } else { // 没有图片
//            start(new PhotoOptionComponent(), );
        }
    }

    @OnClick(R.id.close_button)
    public void close() {

    }
}
