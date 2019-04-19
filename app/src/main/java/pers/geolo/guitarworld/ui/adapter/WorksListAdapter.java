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
import pers.geolo.guitarworld.ui.base.BaseActivity;
import pers.geolo.guitarworld.presenter.works.WorksListPresenter;
import pers.geolo.guitarworld.ui.activity.WorksDetailActivity;
import pers.geolo.guitarworld.util.ModuleMessage;
import pers.geolo.guitarworld.view.WorksListItemView;
import pers.geolo.guitarworld.view.WorksListView;
import pers.geolo.util.DateUtils;

public class WorksListAdapter extends MvpRecyclerViewAdapter<WorksListAdapter.ViewHolder, WorksListItemView>
        implements WorksListView {

    private WorksListPresenter worksListPresenter = new WorksListPresenter();

    public WorksListAdapter(BaseActivity activity) {
        super(activity);
        worksListPresenter.bind(this);
    }

    public WorksListPresenter getWorksListPresenter() {
        return worksListPresenter;
    }

    @Override
    protected int getItemViewId() {
        return R.layout.item_works_view;
    }

    @Override
    protected ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void onBindItemView(ViewHolder viewHolder, int i) {
        worksListPresenter.onBindItemView(viewHolder, i);
    }

    @Override
    public int getItemCount() {
        return worksListPresenter.getListSize();
    }

    @Override
    public void toWorksDetailView(int worksId) {
        Intent intent = new Intent(getActivity(), WorksDetailActivity.class);
        intent.putExtra(ModuleMessage.WORKS_ID, worksId);
        getActivity().startActivity(intent);
    }

    public class ViewHolder extends MvpRecyclerViewAdapter.ViewHolder implements WorksListItemView {

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @OnClick(R.id.ll_works_item)
        public void onViewClicked() {
            worksListPresenter.toWorksDetail(getAdapterPosition());
        }

        @OnLongClick(R.id.ll_works_item)
        public boolean onLongClick() {
            worksListPresenter.showWorksItemOption(getIndex());
            return true;
        }

        @Override
        public void setAuthor(String author) {
            tvAuthor.setText(author);
        }

        @Override
        public void setCreateTime(Date createTime) {
            tvCreateTime.setText(DateUtils.toDefaultString(createTime));
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
        public int getIndex() {
            return getAdapterPosition();
        }
    }
}
