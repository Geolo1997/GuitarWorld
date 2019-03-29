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
import java.util.Date;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.activity.WorksDetailActivity;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.base.BaseApplication;
import pers.geolo.guitarworld.base.BaseRecyclerViewAdapter;
import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.WorksAPI;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.presenter.WorksPresenter;
import pers.geolo.guitarworld.util.DateUtils;
import pers.geolo.guitarworld.view.WorksItemOptionView;
import pers.geolo.guitarworld.view.WorksItemView;

public class WorksListAdapter extends BaseRecyclerViewAdapter<Works, WorksListAdapter.MyViewHolder> {

    public WorksListAdapter(BaseActivity activity) {
        super(activity);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        WorksPresenter.loadingWorksItem(myViewHolder);
    }

    @Override
    public int getItemViewId() {
        return R.layout.item_works_view;
    }

    public class MyViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder implements WorksItemView, WorksItemOptionView {
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.ll_works_item)
        public void onViewClicked() {
            Intent intent = new Intent(BaseApplication.getContext(), WorksDetailActivity.class);
            intent.putExtra("id", getDataList().get(getAdapterPosition()).getId());
            getActivity().startActivity(intent);
        }

        @OnLongClick(R.id.ll_works_item)
        public boolean option() {
            WorksPresenter.showWorksItemOption(this);
            return true;
        }

        @Override
        public Works getWorks() {
            return getDataList().get(getAdapterPosition());
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
        public void removeWorks() {
            getDataList().remove(getAdapterPosition());
            notifyDataSetChanged();
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
    }
}
