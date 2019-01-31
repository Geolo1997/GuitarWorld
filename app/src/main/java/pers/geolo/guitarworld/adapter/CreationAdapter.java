package pers.geolo.guitarworld.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.activity.CreationDetailActivity;
import pers.geolo.guitarworld.base.BaseApplication;
import pers.geolo.guitarworld.model.Creation;

public class CreationAdapter extends RecyclerView.Adapter<CreationAdapter.ViewHolder> {

    private List<Creation> creationList;

    class ViewHolder extends RecyclerView.ViewHolder {

        //@BindView(R.id.tv_authorId)
        TextView tvAuthorId;
        //@BindView(R.id.tv_createTime)
        TextView tvCreateTime;
        //@BindView(R.id.tv_title)
        TextView tvTitle;
        //@BindView(R.id.tv_content)
        TextView tvContent;

        Button btItem;

        public ViewHolder(View view) {
            super(view);
            tvAuthorId = view.findViewById(R.id.tv_authorId);
            tvCreateTime = view.findViewById(R.id.tv_createTime);
            tvTitle = view.findViewById(R.id.tv_title);
            tvContent = view.findViewById(R.id.tv_content);
            btItem = view.findViewById(R.id.bt_creation_item);
        }

        public void setValue(Creation creation) {
            tvAuthorId.setText(creation.getAuthorId());
            tvCreateTime.setText(creation.getCreateTime().toString());
            tvTitle.setText(creation.getTitle());
            tvContent.setText(creation.getContent());
        }
    }

    public CreationAdapter(List<Creation> creationList) {
        this.creationList = creationList;
    }

    //创建ViewHolder实例保存子项
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_creation, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.btItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent(BaseApplication.getContext(), CreationDetailActivity.class);
                intent.putExtra("creation", creationList.get(position));
                BaseApplication.getContext().startActivity(intent);

            }
        });
        return holder;
    }

    //对子项数据赋值，滚动到屏幕内时调用
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Creation creation = creationList.get(i);
        viewHolder.setValue(creation);
    }



    @Override
    public int getItemCount() {
        return creationList.size();
    }
}