package pers.geolo.guitarworld.view;

import java.util.Date;

import pers.geolo.guitarworld.view.base.BaseView;
import pers.geolo.guitarworld.view.list.ListItemView;

public interface WorksListItemView extends BaseView, ListItemView {

    void setAuthor(String author);

    void setCreateTime(Date createTime);

    void setTitle(String title);

    void setContent(Object content);

    String getUsername();

    void showOptions(String[] options);

    int getWorksId();

    void setId(int id);
}
