package pers.geolo.guitarworld.delegate.works;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.OnClick;
import java.io.File;
import java.util.Date;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.ImageModel;
import pers.geolo.guitarworld.model.WorksModel;
import pers.geolo.guitarworld.util.*;

public class PublishDelegate extends BaseDelegate {

    public static final int MAX_IMAGE_COUNT = 3;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.bt_upload)
    Button btUpload;
    @BindView(R.id.ll_images)
    LinearLayout llImages;
    Works works = new Works();

    private int imageCount = 0;

    @Override
    public Object getLayout() {
        return R.layout.delegate_publish;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick(R.id.bt_publish)
    public void publish() {
        String username = AuthModel.getCurrentLoginUser().getUsername();
        works.setAuthor(username);
        works.setCreateTime(new Date());
        works.setTitle(etTitle.getText().toString());
        works.setContent(etContent.getText().toString());
        WorksModel.publishWorks(works, new DataListener<Void>() {
            @Override
            public void onReturn(Void aVoid) {
                Toast.makeText(getContext(), "发布成功", Toast.LENGTH_SHORT).show();
                KeyBoardUtils.hideKeyBoard(getContext(), etContent);
                pop();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.bt_upload)
    public void onViewClicked() {
        if (imageCount >= MAX_IMAGE_COUNT) {
            Toast.makeText(getContext(), "最多上传" + MAX_IMAGE_COUNT + "张图片", Toast.LENGTH_SHORT).show();
        } else {
            String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (!PermissionUtils.hasPermissions(getDelegateActivity(), permissions)) {
                PermissionUtils.requestPermissions(getDelegateActivity(), permissions, new PermissionUtils.Callback() {
                    @Override
                    public void onSuccess(String[] permissions, int[] grantResults) {
                        toAlbum();
                    }

                    @Override
                    public void onFailure(String[] permissions, int[] grantResults) {

                    }
                });
            } else {
                toAlbum();
            }
        }
    }

    public void toAlbum() {
        MediaUtils.openAlbum(getDelegateActivity(), new ActivityUtils.Callback() {
            @Override
            public void onSuccess(@Nullable Intent intent) {
                String filePath = GetPhotoFromPhotoAlbum.getRealPathFromUri(getContext(),
                        intent.getData());
                File file = new File(filePath);
                ImageModel.upload(file, new DataListener<String>() {
                    @Override
                    public void onReturn(String s) {
                        works.addImage(s);
                        addImage(file.getAbsolutePath());
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }

            @Override
            public void onFailure(@Nullable Intent intent) {

            }
        });
    }

    public void addImage(String imagePath) {
        ImageView imageView = new ImageView(getDelegateActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300, 300);
        imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        imageView.setLayoutParams(params);
        llImages.addView(imageView);
        imageCount++;
    }
}
