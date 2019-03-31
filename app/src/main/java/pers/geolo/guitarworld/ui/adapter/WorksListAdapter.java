package pers.geolo.guitarworld.ui.adapter;

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
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.base.BaseApplication;
import pers.geolo.guitarworld.base.BaseRecyclerViewAdapter;
import pers.geolo.guitarworld.presenter.WorksListPresenter;
import pers.geolo.guitarworld.ui.activity.WorksDetailActivity;
import pers.geolo.guitarworld.util.DateUtils;
import pers.geolo.guitarworld.view.WorksListItemView;
import pers.geolo.guitarworld.view.WorksListView;

public class WorksListAdapter extends BaseRecyclerViewAdapter<WorksListAdapter.WorksViewHolder, WorksListItemView>
        implements WorksListView {

    WorksListPresenter worksListPresenter = new WorksListPresenter();

    public WorksListAdapter(BaseActivity activity) {
        super(activity);
        worksListPresenter.bind(this);
        worksListPresenter.loadingWorksList();
    }

    @Override
    public int getItemViewId() {
        return R.layout.item_works_view;
    }

    @Override
    public void toWorksDetailView(int worksId) {
        Intent intent = new Intent(BaseApplication.getContext(), WorksDetailActivity.class);
        intent.putExtra("id", worksId);
        getActivity().startActivity(intent);
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
            worksListPresenter.toWorksDetail(getIndex());
        }

        @OnLongClick(R.id.ll_works_item)
        public boolean option() {
            worksListPresenter.showWorksItemOption(this);
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
                                worksListPresenter.removeWorks(getIndex());
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
