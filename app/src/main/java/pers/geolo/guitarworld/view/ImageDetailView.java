package pers.geolo.guitarworld.view;

import java.io.InputStream;

import pers.geolo.guitarworld.view.base.LoadingView;

public interface ImageDetailView extends LoadingView {
    void setImageView(InputStream inputStream);
}
