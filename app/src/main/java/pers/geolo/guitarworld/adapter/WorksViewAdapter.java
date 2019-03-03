package pers.geolo.guitarworld.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.util.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WorksViewAdapter extends BaseRecyclerViewAdapter<WorksViewAdapter.ViewHolder> {

    private List<Works> worksList;

    public WorksViewAdapter(List<Works> worksList) {
        this.worksList = worksList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_works_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Works works = worksList.get(i);
        viewHolder.tvAuthor.setText(works.getAuthor());

        Date createTime = works.getCreateTime();
        String dateString = "";
        if (createTime != null) {
            dateString = DateUtils.toString(createTime);
        }
        viewHolder.tvCreateTime.setText(dateString);
        viewHolder.tvTitle.setText(works.getTitle());
        viewHolder.tvContent.setText((CharSequence) works.getContent());
        viewHolder.btDetail.setOnClickListener(v -> onItemListener.onItemClick(v, i));
    }

    @Override
    public int getItemCount() {
        return worksList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_createTime)
        TextView tvCreateTime;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.bt_detail)
        Button btDetail;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
