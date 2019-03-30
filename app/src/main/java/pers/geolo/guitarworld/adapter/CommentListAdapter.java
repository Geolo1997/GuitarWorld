package pers.geolo.guitarworld.adapter;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnLongClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.base.BaseRecyclerViewAdapter;
import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.CommentApi;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.util.DateUtils;

public class CommentListAdapter extends BaseRecyclerViewAdapter<Comment, CommentListAdapter.ViewHolder> {

    private final String[] personlOptions = new String[]{"删除"};
    private final String[] viewerOptions = new String[]{};

    public CommentListAdapter(BaseActivity activity) {
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
        viewHolder.tvComment.setText(comment.getContent());
        viewHolder.tvCreateTime.setText(DateUtils.toString(comment.getCreateTime()));
    }

    public class ViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder {

        @BindView(R.id.tv_commentAuthor)
        TextView tvCommentAuthor;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;

        @OnLongClick(R.id.ll_comment_item)
        public boolean option() {
            Comment comment = getDataList().get(getAdapterPosition());
            String currentUsername = DAOService.getInstance().getCurrentLogInfo().getUsername();

            String[] alertDialogItems;
            if (currentUsername.equals(comment.getAuthor())) {
                alertDialogItems = personlOptions;
            } else {
                alertDialogItems = viewerOptions;
            }
            //添加列表
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                    .setTitle("选项")
                    .setItems(alertDialogItems, (dialogInterface, i) -> {
                        String text = alertDialogItems[i];
                        switch (text) {
                            case "删除":
                                HttpService.getInstance().getAPI(CommentApi.class)
                                        .removeComment(comment.getId()).enqueue(new BaseCallback<Void>() {
                                    @Override
                                    public void onSuccess(Void responseData) {
                                        getActivity().showToast("删除成功");
                                        getDataList().remove(getAdapterPosition());
                                        notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onError(int errorCode, String errorMessage) {
                                        getActivity().showToast("删除失败");
                                    }

                                    @Override
                                    public void onFailure() {
                                        getActivity().showToast("网络错误");
                                    }
                                });
                                break;
                            default:
                        }
                    })
                    .create();
            alertDialog.show();
            return true;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
