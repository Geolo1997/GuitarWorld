package pers.geolo.guitarworld.delegate.works;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;
import butterknife.BindView;
import butterknife.OnClick;
import com.dd.processbutton.iml.ActionProcessButton;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BeanFactory;
import pers.geolo.guitarworld.base.SwipeBackDelegate;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.FileListener;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.entity.WorksType;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.WorksModel;
import pers.geolo.guitarworld.util.ActivityUtils;
import pers.geolo.guitarworld.util.MediaUtils;
import pers.geolo.guitarworld.util.PermissionUtils;

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

    @BindView(R.id.title_text)
    EditText titleEditText;
    @BindView(R.id.preview_video)
    VideoView previewVideo;
    @BindView(R.id.publish_works_button)
    ActionProcessButton publishWorksButton;

    private WorksModel worksModel = BeanFactory.getBean(WorksModel.class);
    private AuthModel authModel = BeanFactory.getBean(AuthModel.class);
    private File video;
    private Works videoWorks;

    @Override
    public Object getLayout() {
        return R.layout.publish_video_works;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        videoWorks = new Works();
    }

    @OnClick(R.id.update_video_button)
    public void onUpdateVideoButton() {
        // 申请权限
        String[] writeStoragePermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!PermissionUtils.hasPermissions(getContext(), writeStoragePermission)) {
            PermissionUtils.requestPermissions(getContainerActivity(), writeStoragePermission, new PermissionUtils.Callback() {
                @Override
                public void onSuccess(String[] permissions, int[] grantResults) {
                    openCamera();
                }

                @Override
                public void onFailure(String[] permissions, int[] grantResults) {

                }
            });
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        // 打开拍摄视频Activity（打开视频文件选择Activity）
        video = new File(Environment.getExternalStorageDirectory().getPath()
                + "/guitarworld/" + System.currentTimeMillis() + ".mp4");
        MediaUtils.openVideoCamera(getContainerActivity(), video, new ActivityUtils.Callback() {
            @Override
            public void onSuccess(Intent intent) {
                previewVideo.setVisibility(View.VISIBLE);
                previewVideo.setVideoPath(video.getPath());
                previewVideo.start();
            }

            @Override
            public void onFailure(Intent intent) {

            }
        });
    }

    private void updateVideo() {
        publishWorksButton.setMode(ActionProcessButton.Mode.PROGRESS);
        worksModel.publishVideoWorks(video, new FileListener<String>() {
            @Override
            public void onProgress(long currentLength, long totalLength) {
                int percent = (int) (currentLength * 100 / totalLength);
                publishWorksButton.setProgress(percent);
            }

            @Override
            public void onFinish(String s) {
                publishWorksButton.setProgress(100);
                videoWorks.setVideoUrl(s);
                publishWorks();
            }

            @Override
            public void onError(String message) {
                publishWorksButton.setProgress(-1);
            }
        });
    }

    @OnClick(R.id.publish_works_button)
    public void onPublishWorksButtonClick() {
        updateVideo();
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
                Toast.makeText(getContext(), "发布成功！", Toast.LENGTH_SHORT).show();
                pop();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
