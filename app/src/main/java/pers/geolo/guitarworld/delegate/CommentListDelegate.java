package pers.geolo.guitarworld.delegate;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import java.util.Map;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.fragmentationtest.delegate.BaseDelegate;
import pers.geolo.guitarworld.network.HttpUtils;
import pers.geolo.guitarworld.network.callback.Response;
import pers.geolo.guitarworld.ui.base.BaseFragment;
import pers.geolo.guitarworld.util.ImageUtils;
import pers.geolo.guitarworld.util.RecyclerViewUtils;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-01
 */
public class CommentListDelegate extends BaseDelegate {
    private static final String WORKS_ID = "WORKS_ID";
    @BindView(R.id.rv_comment_list)
    RecyclerView rvCommentList;
//    @BindView(R.id.srl_refresh)
//    SwipeRefreshLayout srlRefresh;

    List<Comment> commentList = new ArrayList<>();
    Adapter adapter = new Adapter();
    Map<String, Object> filter = new HashMap<>();

    public static CommentListDelegate newInstance(int worksId) {
        Bundle args = new Bundle();
        args.putInt(WORKS_ID, worksId);
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

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        int worksId = getStartParameters().getInt("worksId");
        filter.put("worksId", worksId);
//        srlRefresh.setOnRefreshListener(() -> {
//            loadCommentList();
//            srlRefresh.setRefreshing(false);
//        });
        RecyclerViewUtils.setDefaultConfig(getContext(), rvCommentList);
        rvCommentList.setAdapter(adapter);
        loadCommentList();
        return view;
    }

    private void loadCommentList() {
        adapter.avatarsHolder = new HashMap<>();
        HttpUtils.getCommentList(filter, new Response<List<Comment>>(getBaseActivity()) {
            @Override
            public void onResponse(List<Comment> responseData) {
                commentList = responseData;
                adapter.notifyDataSetChanged();
            }
        });
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        Map<String, Bitmap> avatarsHolder = new HashMap<>();


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
            Bitmap avatarBitmap = avatarsHolder.get(comment.getAuthor());
            if (avatarBitmap != null) {
                viewHolder.civAvatar.setImageBitmap(avatarBitmap);
            } else {
                ImageUtils.getBitmap(getBaseActivity(), HttpUtils.getAavatarRooter(comment.getAuthor()), bitmap -> {
                    avatarsHolder.put(comment.getAuthor(), bitmap);
                    viewHolder.civAvatar.setImageBitmap(bitmap);
                });
            }
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
