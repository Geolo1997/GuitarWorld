package pers.geolo.guitarworld.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.util.NomenclatureUtils;
import pers.geolo.guitarworld.util.ResourceUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class LinerLayoutRecyclerViewAdapter<T> extends RecyclerView.Adapter<LinerLayoutRecyclerViewAdapter.ViewHolder> {

    protected List<T> mList;
    protected int mLayoutId;
    private ViewHolder mViewHolder;

    public LinerLayoutRecyclerViewAdapter(List<T> list, int layoutId) {
        mList = list;
        mLayoutId = layoutId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(mLayoutId, viewGroup, false);
        return new LinerLayoutRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//        Class clazz = R.id.class;
        T bean = mList.get(i);
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String fieldTypeName = field.getType().getSimpleName();
            if ("String".equals(fieldTypeName)) {
                int id = ResourceUtils.getIdValue("it_rv_" + NomenclatureUtils.toUnderScore(fieldName));
                View view = viewHolder.getLinearLayout().findViewById(id);
                if (view instanceof TextView) {
                    try {
                        field.setAccessible(true);
                        ((TextView) view).setText((String) field.get(bean));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


//        LinearLayout layout = viewHolder.getLinearLayout();
//        for (int index = 0; index < layout.getChildCount(); index++) {
//            View view = layout.getChildAt(index);
//            view.getId();
//
//            ResourceUtils.getIdValue("tv_" + );
//            if (view instanceof TextView) {
//                ((TextView) view).setText(bean.getClass().toString());
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView;
        }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }
    }
}
