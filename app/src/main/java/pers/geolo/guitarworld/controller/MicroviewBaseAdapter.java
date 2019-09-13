package pers.geolo.guitarworld.controller;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import org.microview.core.ViewController;
import org.microview.core.ViewParams;

import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/9/12
 */
public class MicroviewBaseAdapter extends BaseAdapter {

    private List<ViewController> controllers;
    private List<ViewParams> params;

    public MicroviewBaseAdapter(List<ViewController> controllers, List<ViewParams> params) {
        if (controllers.size() != params.size()) {
            throw new IllegalArgumentException("the size of controllers must equals to the size of params");
        }
        this.controllers = controllers;
        this.params = params;
    }

    @Override
    public int getCount() {
        return controllers.size();
    }

    @Override
    public Object getItem(int position) {
        return params.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewController controller = controllers.get(position);
        controller.createView(parent);
        controller.initView(params.get(position));
        return controller.getView();
    }
}
