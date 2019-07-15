package pers.geolo.guitarworld.delegate.works;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.entity.FileListener;
import pers.geolo.guitarworld.model.WorksModel;
import pers.geolo.guitarworld.util.ActivityUtils;
import pers.geolo.guitarworld.util.MediaUtils;
import pers.geolo.guitarworld.util.PermissionUtils;

import java.io.File;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/15
 */
public class PublishVideoWorksDelegate extends BaseDelegate {

    File video;

    @BindView(R.id.progress_text)
    TextView progressText;

    @Override
    public Object getLayout() {
        return R.layout.publish_video_works;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick(R.id.update_video_button)
    public void onUpdateVideoButton() {
        // 申请权限
        String[] writeStoragePermission = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!PermissionUtils.hasPermissions(getContext(), writeStoragePermission)) {
            PermissionUtils.requestPermissions(getContainerActivity(), writeStoragePermission, new PermissionUtils.Callback() {
                @Override
                public void onSuccess(String[] permissions, int[] grantResults) {
                    toCamara();
                }

                @Override
                public void onFailure(String[] permissions, int[] grantResults) {

                }
            });
        } else {
            toCamara();
        }
    }

    private void toCamara() {
        // 打开拍摄视频Activity（打开视频文件选择Activity）
        video = new File(Environment.getExternalStorageDirectory().getPath()
                + "/guitarworld/" + System.currentTimeMillis() + ".mp4");
        MediaUtils.openVideoCamera(getContainerActivity(), video, new ActivityUtils.Callback() {
            @Override
            public void onSuccess(Intent intent) {
                updateVideo();
            }

            @Override
            public void onFailure(Intent intent) {

            }
        });
    }

    private void updateVideo() {
        WorksModel.publishVideoWorks(video, new FileListener<String>() {
            @Override
            public void onProgress(long currentLength, long totalLength) {
                int percent = (int) (currentLength * 100 / totalLength);
                progressText.setText(percent + "%");
            }

            @Override
            public void onFinish(String s) {
                progressText.setText("上传成功！");
            }

            @Override
            public void onError(String message) {
                progressText.setText(message);
            }
        });
    }

    @OnClick(R.id.publish_works)
    public void publishWorks() {

    }
}
