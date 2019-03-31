package pers.geolo.guitarworld.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import java.util.Date;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.activity.WorksListDetailActivity;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.base.BaseApplication;
import pers.geolo.guitarworld.base.BaseRecyclerViewAdapter;
import pers.geolo.guitarworld.presenter.WorksPresenter;
import pers.geolo.guitarworld.util.DateUtils;
import pers.geolo.guitarworld.view.WorksListItemView;

public class WorksListAdapter extends BaseRecyclerViewAdapter<WorksListAdapter.WorksViewHolder> {


    public WorksListAdapter(BaseActivity activity) {
        super(activity);
    }

    @Override
    public int getItemViewId() {
        return R.layout.item_works_view;
    }

    public class WorksViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder implements WorksListItemView {
        @BindView(R.id.tv_id)
        TextView tvId;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;

        public WorksViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @OnClick(R.id.ll_works_item)
        public void onViewClicked() {
            Intent intent = new Intent(BaseApplication.getContext(), WorksListDetailActivity.class);
            intent.putExtra("id", Integer.valueOf(tvId.getText().toString()));
            getActivity().startActivity(intent);
        }

        @OnLongClick(R.id.ll_works_item)
        public boolean option() {
            WorksPresenter.showWorksItemOption(this);
            return true;
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
                                WorksPresenter.removeWorks(this);
                                break;
                            default:
                        }
                    })
                    .create();
            alertDialog.show();
        }

        @Override
        public int getWorksId() {
            return Integer.valueOf(tvId.getText().toString());
        }

        @Override
        public void setId(int id) {
            tvId.setText(String.valueOf(id));
        }

        @Override
        public void setAuthor(String author) {
            tvAuthor.setText(author);
        }

        @Override
        public void setCreateTime(Date createTime) {
            String dateString = null;
            if (createTime != null) {
                dateString = DateUtils.toString(createTime);
            }
            tvCreateTime.setText(dateString);
        }

        @Override
        public void setTitle(String title) {
            tvTitle.setText(title);
        }

        @Override
        public void setContent(Object content) {
            tvContent.setText(content.toString());
        }

        @Override
        public String getUsername() {
            return tvAuthor.getText().toString().trim();
        }
    }
}
