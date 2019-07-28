package pers.geolo.guitarworld.delegate.works;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.OnClick;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BeanFactory;
import pers.geolo.guitarworld.delegate.base.SwipeBackDelegate;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.entity.WorksType;
import pers.geolo.guitarworld.entity.event.GetImageEvent;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.WorksModel;
import pers.geolo.guitarworld.util.*;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 发布图文页面
 *
 * @author 桀骜(Geolo)
 * @version 1.0
 */
public class PublishImageTextDelegate extends SwipeBackDelegate {

    public static final int MAX_IMAGE_COUNT = 3;

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.bt_upload)
    Button btUpload;
    @BindView(R.id.images_layout)
    LinearLayout imagesLayout;
    Works works = new Works();

    private int imageCount = 0;
    private List<File> imageFiles;

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);
    WorksModel worksModel = BeanFactory.getBean(WorksModel.class);

    @Override
    public Object getLayout() {
        return R.layout.delegate_publish;
    }

    @Override
    protected View getStatueBarTopView() {
        return titleBar;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTitleBar();
        loadRootFragment(R.id.images_layout, new AddImageManagerDelegate());
    }

    @OnClick(R.id.bt_publish)
    public void publish() {
        String username = authModel.getCurrentLoginUser().getUsername();
        works.setType(WorksType.IMAGE_TEXT);
        works.setAuthor(username);
        works.setCreateTime(new Date());
        works.setTitle(etTitle.getText().toString());
        works.setContent(etContent.getText().toString());
        worksModel.publishWorks(works, new DataListener<Void>() {
            @Override
            public void onReturn(Void aVoid) {
                Toast.makeText(getContext(), "发布成功", Toast.LENGTH_SHORT).show();
                KeyBoardUtils.hideKeyBoard(getContext(), etContent);
                ThreadUtils.runOnNewSubThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            pop();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
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
            if (!PermissionUtils.hasPermissions(getContainerActivity(), permissions)) {
                PermissionUtils.requestPermissions(getContainerActivity(), permissions, new PermissionUtils.Callback() {
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
        PhotoUtils.openAlbum(getContainerActivity(), new ActivityUtils.Callback() {
            @Override
            public void onSuccess(@Nullable Intent intent) {
                String filePath = GetPhotoFromPhotoAlbum.getRealPathFromUri(getContext(),
                        intent.getData());
                File file = new File(filePath);
                //TODO
//                works.(file, new DataListener<String>() {
//                    @Override
//                    public void onReturn(String s) {
//                        works.addImage(s);
//                        addImage(file.getAbsolutePath());
//                    }
//
//                    @Override
//                    public void onError(String message) {
//
//                    }
//                });
            }

            @Override
            public void onFailure(@Nullable Intent intent) {

            }
        });
    }

    public void addImage(String imagePath) {
        ImageView imageView = new ImageView(getContainerActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300, 300);
        imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        imageView.setLayoutParams(params);
        imagesLayout.addView(imageView);
        imageCount++;
    }

    public void initTitleBar() {
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                KeyBoardUtils.hideKeyBoard(getContext(), etContent);
                pop();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                publish();
            }
        });
    }
}
