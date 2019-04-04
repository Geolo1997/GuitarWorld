package pers.geolo.guitarworld.ui.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pers.geolo.guitarworld.presenter.base.BasePresenter;
import pers.geolo.guitarworld.view.list.ListItemView;
import pers.geolo.guitarworld.view.list.ListView;

/**
 * ListPresenter基类
 *
 * @param <V> List视图
 * @param <I> Item视图
 * @param <E> List数据类型
 * @author 桀骜 Geolo
 */
public abstract class BaseListPresenter<V extends ListView<I>, I extends ListItemView, E>
        extends BasePresenter<V> {

    private List<E> dataList = new ArrayList<>();

    private HashMap<String, Object> filter = new HashMap<>();

    protected List<E> getDataList() {
        return dataList;
    }

    public HashMap<String, Object> getFilter() {
        return filter;
    }

    protected I getItemView(int index) {
        return getView().getItemView(index);
    }

    protected void addAllItemView(List<E> dataList) {
        this.dataList = dataList;
        getView().addAllItemView();
    }

    protected void addItemView(E e) {
        getDataList().add(e);
        getView().addItemView(getListSize());
    }

    protected void addItemView(int index, E e) {
        getDataList().add(index, e);
        getView().addItemView(index);
    }

    protected void removeItemView(int index) {
        getDataList().remove(index);
        getView().removeItemView(index);
    }

    protected void clearItemView() {
        getDataList().clear();
        getView().clearItemView();
    }

    public abstract void onBindItemView(I itemView, int index);

    public int getListSize() {
        return getDataList().size();
    }

    public void setFilter(String field, Object value) {
        filter.put(field,value);
    }

    public void removeFilter(String field) {
        filter.remove(field);
    }
}
