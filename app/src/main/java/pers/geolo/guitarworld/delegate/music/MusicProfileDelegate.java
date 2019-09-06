package pers.geolo.guitarworld.delegate.music;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.base.BeanFactory;
import pers.geolo.guitarworld.delegate.works.ImageDetailDelegate;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.Music;
import pers.geolo.guitarworld.model.MusicModel;
import pers.geolo.guitarworld.ui.ImageFilter;
import pers.geolo.guitarworld.util.GlideUtils;
import pers.geolo.guitarworld.util.ToastUtils;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/10
 */
public class MusicProfileDelegate extends BaseDelegate {

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
    public Object getLayoutView() {
        return R.layout.music_profile;
    }

    public static MusicProfileDelegate newInstance(int musicId) {
        Bundle args = new Bundle();
        args.putInt(MUSIC_ID, musicId);
        MusicProfileDelegate fragment = new MusicProfileDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            int musicId = arguments.getInt(MUSIC_ID);
            loadMusicProfile(musicId);
        }
        musicImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (music != null) {
                    String imageUrl = music.getImageUrl();
                    getContainerActivity().start(ImageDetailDelegate.newInstance(imageUrl));
                }
            }
        });
    }


    private static final String MUSIC_ID = "MUSIC_ID";
    private MusicModel musicModel = BeanFactory.getBean(MusicModel.class);

    private void loadMusicProfile(int musicId) {
        musicModel.getMusic(musicId, new DataListener<Music>() {
            @Override
            public void onReturn(Music music) {
                MusicProfileDelegate.this.music = music;
                musicNameText.setText(music.getName());
                musicAuthorText.setText(music.getAuthor());
                musicProfileText.setText(music.getProfile());
                GlideUtils.load(getContext(), music.getImageUrl(), musicImage);
                GlideUtils.getBitmap(getContext(), music.getImageUrl(), new DataListener<Bitmap>() {
                    @Override
                    public void onReturn(Bitmap bitmap) {
                        bitmap = ImageFilter.blurBitmap(getContext(), bitmap, 25f);
                        bgLayout.setBackground(new BitmapDrawable(bitmap));
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }

            @Override
            public void onError(String message) {
                ToastUtils.showErrorToast(getContext(), message);
            }
        });
    }

    public String getMusicName() {
        return musicNameText.getText().toString();
    }
}
