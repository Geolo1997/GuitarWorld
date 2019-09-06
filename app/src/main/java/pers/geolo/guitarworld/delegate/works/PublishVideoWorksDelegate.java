package pers.geolo.guitarworld.delegate.works;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import cn.jzvd.JzvdStd;
import com.bumptech.glide.Glide;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BeanFactory;
import pers.geolo.guitarworld.delegate.base.SwipeBackDelegate;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.FileListener;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.entity.WorksType;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.WorksModel;
import pers.geolo.guitarworld.util.GlideUtils;
import pers.geolo.guitarworld.util.PhotoUtils;
import pers.geolo.guitarworld.util.ToastUtils;

import java.io.File;
import java.util.Date;

/**
 * 发布视频创作页面
 *
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/15
 */
public class PublishVideoWorksDelegate extends SwipeBackDelegate {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.progress_bar)
    NumberProgressBar progressBar;
    @BindView(R.id.title_text)
    EditText titleEditText;
    @BindView(R.id.preview_video)
    JzvdStd previewVideo;

    private WorksModel worksModel = BeanFactory.getBean(WorksModel.class);
    private AuthModel authModel = BeanFactory.getBean(AuthModel.class);
    private File video;
    private Works videoWorks;

    @Override
    public Object getLayoutView() {
        return R.layout.publish_video_works;
    }

    @Override
    protected View getStatueBarTopView() {
        return titleBar;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTitleBar();
        videoWorks = new Works();
        openCamera();
    }

    private void initTitleBar() {
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                pop();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                updateVideo();
            }
        });
    }

    private void openCamera() {
        PhotoUtils.openVideoCamera(getContainerActivity(), new PhotoUtils.Callback() {
            @Override
            public void onSuccess(File photo) {
                video = photo;
                Bitmap bitmap = PhotoUtils.getVideoThumb(photo.getPath());
                previewVideo.setVisibility(View.VISIBLE);
                previewVideo.setUp(photo.getPath(), "");
                Glide.with(getContext()).load(bitmap).into(previewVideo.thumbImageView);
            }

            @Override
            public void onFailure(PhotoUtils.FailureType failureType) {

            }
        });
    }

    private void updateVideo() {
        progressBar.setVisibility(View.VISIBLE);
        worksModel.publishVideoWorks(video, new FileListener<String>() {
            @Override
            public void onProgress(long currentLength, long totalLength) {
                int percent = (int) (currentLength * 100 / totalLength);
                progressBar.setProgress(percent);
            }

            @Override
            public void onFinish(String s) {
                videoWorks.setVideoUrl(s);
                publishWorks();
            }

            @Override
            public void onError(String message) {
                ToastUtils.showErrorToast(getContext(), message);
            }
        });
    }

    public void publishWorks() {
        String title = titleEditText.getText().toString().trim();
        videoWorks.setType(WorksType.VIDEO);
        videoWorks.setAuthor(authModel.getCurrentLoginUser().getUsername());
        videoWorks.setCreateTime(new Date());
        videoWorks.setTitle(title);
        worksModel.publishWorks(videoWorks, new DataListener<Void>() {
            @Override
            public void onReturn(Void aVoid) {
                ToastUtils.showSuccessToast(getContext(), "发布成功");
                pop();
            }

            @Override
            public void onError(String message) {
                ToastUtils.showErrorToast(getContext(), message);
            }
        });
    }
}
