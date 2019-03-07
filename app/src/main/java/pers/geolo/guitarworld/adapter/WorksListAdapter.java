package pers.geolo.guitarworld.adapter;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.activity.WorksDetailActivity;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.base.BaseApplication;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.base.BaseRecyclerViewAdapter;
import pers.geolo.guitarworld.util.DateUtils;

import java.util.Date;

public class WorksListAdapter extends BaseRecyclerViewAdapter<Works, WorksListAdapter.MyViewHolder> {


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
        @BindView(R.id.tv_createTime)
        TextView tvCreateTime;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;

        @OnClick(R.id.bt_detail)
        public void onViewClicked() {
            Intent intent = new Intent(BaseApplication.getContext(), WorksDetailActivity.class);
            intent.putExtra("id", getDataList().get(getAdapterPosition()).getId());
            getActivity().startActivity(intent);
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
