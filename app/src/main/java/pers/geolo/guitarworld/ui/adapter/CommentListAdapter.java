package pers.geolo.guitarworld.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnLongClick;
import java.util.Date;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.ui.base.BaseActivity;
import pers.geolo.guitarworld.presenter.comment.CommentListPresenter;
import pers.geolo.guitarworld.view.CommentItemView;
import pers.geolo.guitarworld.view.CommentListView;
import pers.geolo.util.DateUtils;

public class CommentListAdapter extends MvpRecyclerViewAdapter<CommentListAdapter.ViewHolder, CommentItemView>
        implements CommentListView {

    private CommentListPresenter commentListPresenter = new CommentListPresenter();

    public CommentListAdapter(BaseActivity activity) {
        super(activity);
        commentListPresenter.bind(this);
    }

    public CommentListPresenter getCommentListPresenter() {
        return commentListPresenter;
    }

    @Override
    public int getItemViewId() {
        return R.layout.item_comments_view;
    }

    @Override
    protected ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void onBindItemView(ViewHolder viewHolder, int i) {
        commentListPresenter.onBindItemView(viewHolder, i);
    }

    @Override
    public int getItemCount() {
        return commentListPresenter.getListSize();
    }

    public class ViewHolder extends MvpRecyclerViewAdapter.ViewHolder implements CommentItemView {

        @BindView(R.id.tv_commentAuthor)
        TextView tvCommentAuthor;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @OnLongClick(R.id.ll_comment_item)
        public boolean option() {
            commentListPresenter.showItemOptions(getIndex());
            return true;
        }

        @Override
        public String getAuthor() {
            return tvCommentAuthor.getText().toString().trim();
        }

        @Override
        public void setAuthor(String author) {
            tvCommentAuthor.setText(author);
        }

        @Override
        public void setCreateTime(Date createTime) {
            tvCreateTime.setText(DateUtils.toDefaultString(createTime));
        }

        @Override
        public void setContent(String content) {
            tvComment.setText(content);
        }

        @Override
        public void showOptions(String[] options) {
            //添加列表
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                    .setTitle("选项")
                    .setItems(options, (dialogInterface, i) -> {
                        String text = options[i];
                        switch (text) {
                            case "删除":
                                commentListPresenter.removeComment(getIndex());
                                break;
                            default:
                        }
                    })
                    .create();
            alertDialog.show();
        }
    }
}
