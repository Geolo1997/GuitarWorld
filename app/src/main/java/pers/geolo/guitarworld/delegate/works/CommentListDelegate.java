package pers.geolo.guitarworld.delegate.works;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.FileListener;
import pers.geolo.guitarworld.model.CommentModel;
import pers.geolo.guitarworld.model.ImageModel;
import pers.geolo.guitarworld.model.listener.GetImageListener;
import pers.geolo.guitarworld.util.RecyclerViewUtils;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-01
 */
public class CommentListDelegate extends BaseDelegate {

    private static final String FILTER = "FILTER";

    //    @BindView(R.id.srl_refresh)
//    SwipeRefreshLayout srlRefresh;
    @BindView(R.id.rv_comment_list)
    RecyclerView rvCommentList;

    private List<Comment> commentList = new ArrayList<>();
    private HashMap<String, Object> filter = new HashMap<>();
    private Adapter adapter = new Adapter();

    public static CommentListDelegate newInstance(HashMap<String, Object> filter) {
        Bundle args = new Bundle();
        args.putSerializable(FILTER, filter);
        CommentListDelegate fragment = new CommentListDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_comment_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initRecyclerView();
        if (getArguments() != null) {
            //noinspection unchecked
            filter = (HashMap<String, Object>) getArguments().getSerializable(FILTER);
            initCommentList();
        }
    }

    private void initCommentList() {
        CommentModel.getCommentList(filter, new DataListener<List<Comment>>() {
            @Override
            public void onReturn(List<Comment> comments) {
                commentList = comments;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void initRecyclerView() {
        RecyclerViewUtils.setDefaultConfig(getContext(), rvCommentList);
        rvCommentList.setAdapter(adapter);
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_comment_view, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Comment comment = commentList.get(i);
            viewHolder.tvAuthor.setText(comment.getAuthor());
            viewHolder.tvComment.setText(comment.getContent());
            viewHolder.tvCreateTime.setText(comment.getCreateTime().toString());
            ImageModel.getAvatar(comment.getAuthor(), new FileListener<Bitmap>() {
                @Override
                public void onProgress(int currentLength, int totalLength) {

                }

                @Override
                public void onReturn(Bitmap bitmap) {
                    viewHolder.civAvatar.setImageBitmap(bitmap);
                }

                @Override
                public void onError(String message) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return commentList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_avatar)
        CircleImageView civAvatar;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;
        @BindView(R.id.tv_comment)
        TextView tvComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
