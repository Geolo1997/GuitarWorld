package pers.geolo.guitarworld.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.base.BaseRecyclerViewAdapter;
import pers.geolo.guitarworld.entity.Comment;

import java.util.List;

public class CommentViewAdapter extends BaseRecyclerViewAdapter<Comment, CommentViewAdapter.ViewHolder> {


    public CommentViewAdapter(BaseActivity activity) {
        super(activity);
    }

    @Override
    public int getItemViewId() {
        return R.layout.item_comments_view;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Comment comment = getDataList().get(i);
        viewHolder.tvCommentAuthor.setText(comment.getAuthor());
        viewHolder.tvComment.setText(comment.getComment());
    }

    public static class ViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder {

        @BindView(R.id.tv_commentAuthor)
        TextView tvCommentAuthor;
        @BindView(R.id.tv_comment)
        TextView tvComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
