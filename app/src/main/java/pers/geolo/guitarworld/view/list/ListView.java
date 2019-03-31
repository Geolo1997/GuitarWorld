package pers.geolo.guitarworld.view.list;

public interface ListView<V> {

    void onBindView(SizeListener sizeListener, OnBindViewListener<V> onBindViewListener);


    void remove(int index);
}
