package pers.geolo.guitarworld.delegate.works;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BaseDelegate;
import pers.geolo.guitarworld.entity.event.Event;
import pers.geolo.guitarworld.util.KeyBoardUtils;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/30
 */
public class WorksOptionDelegate extends BaseDelegate {


    @BindView(R.id.like_button)
    Button likeButton;
    @BindView(R.id.comment_edit)
    EditText commentEdit;
    @BindView(R.id.comment_button)
    Button commentButton;
    @BindView(R.id.share_button)
    Button shareButton;

    private boolean isCommenting;

    @Override
    public Object getLayout() {
        return R.layout.works_option;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        isCommenting = false;
    }

    public void initLayoutStatus() {
        likeButton.setVisibility(View.VISIBLE);
        commentEdit.setVisibility(View.GONE);
        commentButton.setVisibility(View.VISIBLE);
        shareButton.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.like_button)
    public void onLikeButtonClicked() {

    }

    @OnClick(R.id.comment_button)
    public void onCommentButtonClicked() {
        if (!isCommenting) {
            likeButton.setVisibility(View.GONE);
            shareButton.setVisibility(View.GONE);
            commentEdit.setVisibility(View.VISIBLE);
            isCommenting = true;
            KeyBoardUtils.showKeyBoard(getContext(), commentEdit);
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
        }
    }

    @OnClick(R.id.share_button)
    public void onShareButtonClicked() {

    }
}
