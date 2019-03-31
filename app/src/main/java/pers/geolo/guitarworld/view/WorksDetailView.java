package pers.geolo.guitarworld.view;

import java.util.Date;

import pers.geolo.guitarworld.view.base.RefreshView;
import pers.geolo.guitarworld.view.base.ToastView;

public interface WorksDetailView extends RefreshView, ToastView {

    void setAuthor(String author);

    void setCreateTime(Date createTime);

    void setTitle(String title);

    void setContent(Object content);
}
