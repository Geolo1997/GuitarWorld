package pers.geolo.guitarworld.delegate.works;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
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
import java.util.ArrayList;
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
    @BindView(R.id.images_layout)
    LinearLayout imagesLayout;
    @BindView(R.id.progress_text)
    TextView progressText;
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void publish(UploadImageSuccessEvent event) {
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

    private List<String> imageUrls;

    private void uploadImageFiles() {
        List<File> imageFiles = addImageManagerDelegate.getImageFiles();
        imageUrls = new ArrayList<>(imageFiles.size());
        worksModel.uploadImageUrls(imageFiles, new FileListener<List<String>>() {
            @Override
            public void onProgress(long currentLength, long totalLength) {
                progressText.setText("" + (currentLength * 100 / totalLength) + "%");
            }

            @Override
            public void onFinish(List<String> strings) {
                progressText.setText("上传成功！");
                imageUrls = strings;
                EventBus.getDefault().post(new UploadImageSuccessEvent());
            }

            @Override
            public void onError(String message) {
                progressText.setText(message);
            }
        });
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
                uploadImageFiles();
            }
        });
    }
}
