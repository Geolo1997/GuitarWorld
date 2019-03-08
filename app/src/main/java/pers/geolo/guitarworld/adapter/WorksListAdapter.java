package pers.geolo.guitarworld.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.activity.WorksDetailActivity;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.base.BaseApplication;
import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.base.BaseRecyclerViewAdapter;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.CommentAPI;
import pers.geolo.guitarworld.network.api.WorksAPI;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.util.DateUtils;

import java.util.Date;

public class WorksListAdapter extends BaseRecyclerViewAdapter<Works, WorksListAdapter.MyViewHolder> {

    private final String[] personlOptions = new String[]{"删除"};
    private final String[] viewerOptions = new String[]{};

    public WorksListAdapter(BaseActivity activity) {
        super(activity);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Works works = getDataList().get(i);
        myViewHolder.tvAuthor.setText(works.getAuthor());

        Date createTime = works.getCreateTime();
        String dateString = "";
        if (createTime != null) {
            dateString = DateUtils.toString(createTime);
        }
        myViewHolder.tvCreateTime.setText(dateString);
        myViewHolder.tvTitle.setText(works.getTitle());
        myViewHolder.tvContent.setText((CharSequence) works.getContent());
    }

    @Override
    public int getItemViewId() {
        return R.layout.item_works_view;
    }

    public class MyViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder {
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;

        @OnClick(R.id.ll_works_item)
        public void onViewClicked() {
            Intent intent = new Intent(BaseApplication.getContext(), WorksDetailActivity.class);
            intent.putExtra("id", getDataList().get(getAdapterPosition()).getId());
            getActivity().startActivity(intent);
        }

        @OnLongClick(R.id.ll_works_item)
        public boolean option() {
            Works works = getDataList().get(getAdapterPosition());
            String currentUsername = DAOService.getInstance().getCurrentLogInfo().getUsername();

            String[] alertDialogItems;
            if (currentUsername.equals(works.getAuthor())) {
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
                                HttpService.getInstance().getAPI(WorksAPI.class)
                                        .removeWorks(works.getId()).enqueue(new BaseCallback<Void>() {
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
