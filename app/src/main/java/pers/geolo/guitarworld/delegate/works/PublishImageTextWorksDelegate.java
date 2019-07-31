package pers.geolo.guitarworld.delegate.works;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BeanFactory;
import pers.geolo.guitarworld.delegate.base.SwipeBackDelegate;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.FileListener;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.entity.WorksType;
import pers.geolo.guitarworld.entity.event.UploadImageSuccessEvent;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.WorksModel;
import pers.geolo.guitarworld.util.KeyBoardUtils;
import pers.geolo.guitarworld.util.ThreadUtils;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 发布图文页面
 *
 * @author 桀骜(Geolo)
 * @version 1.0
 */
public class PublishImageTextWorksDelegate extends SwipeBackDelegate {

    public static final int MAX_IMAGE_COUNT = 3;

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.progress_bar)
    NumberProgressBar progressBar;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.images_layout)
    LinearLayout imagesLayout;
    Works works = new Works();

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);
    WorksModel worksModel = BeanFactory.getBean(WorksModel.class);

    AddImageManagerDelegate addImageManagerDelegate;

    @Override
    public Object getLayout() {
        return R.layout.publish_image_text;
    }

    @Override
    protected View getStatueBarTopView() {
        return titleBar;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTitleBar();
        addImageManagerDelegate = new AddImageManagerDelegate();
        loadRootFragment(R.id.images_layout, addImageManagerDelegate);
    }

    public void publish() {
        works.setImageUrls(imageUrls);
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
                            Thread.sleep(1000);
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

    private List<String> imageUrls;

    private void uploadImageFiles() {
        progressBar.setVisibility(View.VISIBLE);
        List<File> imageFiles = addImageManagerDelegate.getImageFiles();
        imageUrls = imageFiles.stream()
                .map(new Function<File, String>() {
                    @Override
                    public String apply(File file) {
                        return file.getPath();
                    }
                })
                .collect(Collectors.toList());
        for (int i = 0; i < imageFiles.size(); i++) {
            File imageFile = imageFiles.get(i);
            int finalI = i;
            worksModel.uploadImage(imageFile, new FileListener<String>() {
                @Override
                public void onProgress(long currentLength, long totalLength) {

                }

                @Override
                public void onFinish(String s) {
                    imageUrls.set(finalI, s);
                    EventBus.getDefault().post(new UploadImageSuccessEvent());
                }

                @Override
                public void onError(String message) {
                    existFailure = true;
                }
            });
        }
    }

    private int uploadImageCount = 0;
    private boolean existFailure = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUploadImageSuccess(UploadImageSuccessEvent event) {
        uploadImageCount++;
        progressBar.setProgress(uploadImageCount * 100 / imageUrls.size());
        if (uploadImageCount == imageUrls.size()) {
            if (existFailure) {
                Toast.makeText(getContext(), "上传失败", Toast.LENGTH_SHORT).show();
            } else {
                publish();
            }
        }
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
                if (addImageManagerDelegate.getImageFiles().size() == 0) {
                    publish();
                } else {
                    uploadImageFiles();
                }
            }
        });
    }
}
