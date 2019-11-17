package pers.geolo.guitarworld.microview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/9/3
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Class<? extends ViewController> controllerClass;
    private List<ViewParam> viewParamList;

    public RecyclerViewAdapter(Class<? extends  ViewController> controllerClass, List<ViewParam> viewParamList) {
        this.controllerClass = controllerClass;
        this.viewParamList = viewParamList;
    }

    public void updateDataList(List<ViewParam> viewParamList) {
        this.viewParamList = viewParamList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        try {
            ViewController controller = controllerClass.newInstance();
            ViewHolder viewHolder = new ViewHolder(controller.createView(viewGroup));
            viewHolder.viewController = controller;
            return viewHolder;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ViewController viewController = viewHolder.viewController;
        ViewParam viewParam = viewParamList.get(i);
        viewController.initView(viewParam);
    }

    @Override
    public int getItemCount() {
        return viewParamList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ViewController viewController;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
