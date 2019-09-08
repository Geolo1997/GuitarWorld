package pers.geolo.guitarworld.controller.works;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.base.BaseController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.entity.WorksType;
import pers.geolo.guitarworld.entity.event.Event;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.WorksModel;
import pers.geolo.guitarworld.util.KeyBoardUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/30
 */
public class WorksOptionController extends BaseController {


    private static final String WORKS_ID = "WORKS_ID";

    @BindView(R.id.like_button)
    View likeButton;
    @BindView(R.id.like_text)
    TextView likeText;
    @BindView(R.id.like_image)
    ImageView likeImage;
    @BindView(R.id.comment_edit)
    EditText commentEdit;
    @BindView(R.id.comment_count_text)
    TextView commentCountText;
    @BindView(R.id.comment_button)
    View commentButton;
    @BindView(R.id.share_button)
    View shareButton;

    private Works works;
    private int worksId;
    private int likeCount;
    private boolean isLike;
    private boolean isCommenting;

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);
    WorksModel worksModel = BeanFactory.getBean(WorksModel.class);

    @Override
    public Object getLayoutView() {
        return R.layout.works_option;
    }

    public static WorksOptionController newInstance(int worksId) {
        Bundle args = new Bundle();
        args.putInt(WORKS_ID, worksId);
        WorksOptionController fragment = new WorksOptionController();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        isCommenting = false;
        isLike = false;
        if (getArguments() != null) {
            worksId = getArguments().getInt(WORKS_ID);
        }
    }

    public void initLayoutStatus() {
        likeButton.setVisibility(View.VISIBLE);
        commentEdit.setVisibility(View.GONE);
        commentButton.setVisibility(View.VISIBLE);
        shareButton.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.like_button)
    public void onLikeButtonClicked() {
        if (isLike) {
            isLike = false;
            likeImage.setImageResource(R.drawable.ic_like_24);
            cancelLikeWorks();
        } else {
            isLike = true;
            likeImage.setImageResource(R.drawable.ic_like_selected_24);
            likeWorks();
            EventBus.getDefault().post(new Event(Event.Const.LIKE_WORKS.name(), new Object()));
        }
    }

    private void likeWorks() {
        worksModel.likeWorks(worksId, new DataListener<Void>() {
            @Override
            public void onReturn(Void aVoid) {
                likeCount++;
                likeText.setText("赞" + likeCount);
            }

            @Override
            public void onError(String message) {

            }
        });

    }

    private void cancelLikeWorks() {
        worksModel.cancelLikeWorks(worksId, new DataListener<Void>() {
            @Override
            public void onReturn(Void aVoid) {
                likeCount--;
                likeText.setText("赞" + likeCount);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @OnClick(R.id.comment_button)
    public void onCommentButtonClicked() {
        if (!isCommenting) {
            likeButton.setVisibility(View.GONE);
            shareButton.setVisibility(View.GONE);
            commentEdit.setVisibility(View.VISIBLE);
            isCommenting = true;
//            KeyBoardUtils.showKeyBoard(getContext(), commentEdit);
//            showSoftInput(commentEdit);
        } else {

            String commentText = commentEdit.getText().toString().trim();
            EventBus.getDefault().post(new Event("comment", commentText));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetCommentSuccess(Event event) {
        if (event.get(Event.Const.GET_COMMENT_SUCCESS.name()) != null) {
            commentEdit.setText("");
            initLayoutStatus();
            KeyBoardUtils.hideKeyBoard(getContext(), commentEdit);
        }
    }

    // TODO 文件分享不成功
    @OnClick(R.id.share_button)
    public void onShareButtonClicked() {
        if (works == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, works.getTitle() + "作者：" + works.getAuthor());
        WorksType worksType = works.getType();
        switch (worksType) {
            case IMAGE_TEXT:
                intent.putExtra(Intent.EXTRA_TEXT, works.getContent());
                List<Uri> imageUris = works.getImageUrls()
                        .stream()
                        .map(new Function<String, Uri>() {
                            @Override
                            public Uri apply(String s) {
                                return Uri.parse(s);
                            }
                        }).collect(Collectors.toList());
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, new ArrayList<>(imageUris));
                break;
            case VIDEO:
                Uri videoUri = Uri.parse(works.getVideoUrl());
                intent.putExtra(Intent.EXTRA_STREAM, videoUri);
                break;
        }
//        startActivity(Intent.createChooser(intent, "分享到"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetWorksSuccess(Event event) {
        Works works = (Works) event.get(Event.Const.GET_WORKS_SUCCESS.name());
        if (works != null && works.getId() == worksId) {
            this.works = works;
            likeCount = works.getLikeCount();
            likeText.setText("赞" + works.getLikeCount());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetCommentListEvent(Event event) {
        List<Comment> commentList = (List<Comment>) event.get(Event.Const.GET_COMMENT_LIST_SUCCESS.name());
        if (commentList == null) {
            return;
        }
        commentCountText.setText("" + commentList.size());
    }
}
