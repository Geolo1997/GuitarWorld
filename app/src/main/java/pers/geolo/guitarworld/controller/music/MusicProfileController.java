package pers.geolo.guitarworld.controller.music;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import org.microview.core.ControllerManager;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.controller.common.ImageDetailController;
import pers.geolo.guitarworld.entity.DataCallback;
import pers.geolo.guitarworld.entity.Music;
import pers.geolo.guitarworld.ui.ImageFilter;
import pers.geolo.guitarworld.util.GlideUtils;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/10
 */
public class MusicProfileController extends BaseController {

    @BindView(R.id.bg_layout)
    LinearLayout bgLayout;
    @BindView(R.id.music_image)
    ImageView musicImage;
    @BindView(R.id.music_name_text)
    TextView musicNameText;
    @BindView(R.id.music_author_text)
    TextView musicAuthorText;
    @BindView(R.id.music_profile_text)
    TextView musicProfileText;

    private Music music;

    @Override
    protected int getLayout() {
        return R.layout.music_profile;
    }

    @Override
    public void initView(ViewParams viewParams) {
        music = (Music) viewParams.get("music");
        musicNameText.setText(music.getName());
        musicAuthorText.setText(music.getAuthor());
        musicProfileText.setText(music.getProfile());
        GlideUtils.load(getActivity(), music.getImageUrl(), musicImage);
        GlideUtils.getBitmap(getActivity(), music.getImageUrl(), new DataCallback<Bitmap>() {
            @Override
            public void onReturn(Bitmap bitmap) {
                bitmap = ImageFilter.blurBitmap(getActivity(), bitmap, 25f);
                bgLayout.setBackground(new BitmapDrawable(bitmap));
            }

            @Override
            public void onError(String message) {

            }
        });
        musicImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imageUrl = music.getImageUrl();
                ControllerManager.start(new ImageDetailController(), new ViewParams("imageUrl", imageUrl));
            }
        });
    }
}
