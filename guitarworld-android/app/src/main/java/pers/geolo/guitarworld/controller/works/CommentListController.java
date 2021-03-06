package pers.geolo.guitarworld.controller.works;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import de.hdodenhof.circleimageview.CircleImageView;
import org.greenrobot.eventbus.EventBus;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.base.BaseController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.controller.user.ProfileController;
import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.entity.DataCallback;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.entity.event.Event;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.CommentModel;
import pers.geolo.guitarworld.model.UserModel;
import pers.geolo.guitarworld.util.DateUtils;
import pers.geolo.guitarworld.util.GlideUtils;
import pers.geolo.guitarworld.util.RecyclerViewUtils;
import pers.geolo.guitarworld.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-01
 */
public class CommentListController extends BaseController {

    private static final String FILTER = "FILTER";

    //    @BindView(R.id.srl_refresh)
//    SwipeRefreshLayout srlRefresh;
    @BindView(R.id.rv_comment_list)
    RecyclerView rvCommentList;

    private List<Comment> commentList = new ArrayList<>();
    private HashMap<String, Object> filter = new HashMap<>();
    private Adapter adapter = new Adapter();

    UserModel userModel = BeanFactory.getBean(UserModel.class);
    AuthModel authModel = BeanFactory.getBean(AuthModel.class);

    public static CommentListController newInstance(HashMap<String, Object> filter) {
        Bundle args = new Bundle();
        args.putSerializable(FILTER, filter);
        CommentListController fragment = new CommentListController();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object getLayoutView() {
        return R.layout.controller_comment_list;
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
        CommentModel.getCommentList(filter, new DataCallback<List<Comment>>() {
            @Override
            public void onReturn(List<Comment> comments) {
                commentList = comments;
                adapter.notifyDataSetChanged();
                EventBus.getDefault().post(new Event(Event.Const.GET_COMMENT_LIST_SUCCESS.name(), commentList));
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
            viewHolder.tvCreateTime.setText(DateUtils.toFriendlyString(comment.getCreateTime()));
            userModel.getPublicProfile(comment.getAuthor(), new DataCallback<User>() {
                @Override
                public void onReturn(User user) {
                    GlideUtils.load(getContext(), user.getAvatarUrl(), viewHolder.civAvatar);
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

        @BindView(R.id.avatar_image)
        CircleImageView civAvatar;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        String[] options;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.avatar_image, R.id.tv_author})
        public void toProfileController() {
            String username = commentList.get(getAdapterPosition()).getAuthor();
//            getContainerActivity().start(ProfileController.newInstance(username));
        }

        @OnLongClick(R.id.ll_comment_item)
        public boolean showOptions() {

            Comment comment = commentList.get(getAdapterPosition());
            String username = comment.getAuthor();
            if (username.equals(authModel.getLoginUser().getUsername())) {
                options = new String[]{"删除"};
            }
            //添加列表
            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle("选项")
                    .setItems(options, (dialogInterface, i) -> {
                        String text = options[i];
                        switch (text) {
                            case "删除":
                                HashMap<String, Object> filter = new HashMap<>();
                                filter.put("id", comment.getId());
                                CommentModel.deleteCommentList(filter, new DataCallback<Void>() {
                                    @Override
                                    public void onReturn(Void aVoid) {
                                        commentList.remove(comment);
                                        adapter.notifyItemRemoved(getAdapterPosition());
                                        ToastUtils.showSuccessToast(getContext(), "删除成功");
                                    }

                                    @Override
                                    public void onError(String message) {
                                        ToastUtils.showErrorToast(getContext(), message);
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
    }
}
