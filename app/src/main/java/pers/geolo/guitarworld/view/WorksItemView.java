package pers.geolo.guitarworld.view;

import java.util.Date;

import pers.geolo.guitarworld.entity.Works;

public interface WorksItemView extends BaseView {
    Works getWorks();

    void setAuthor(String author);

    void setCreateTime(Date createTime);

    void setTitle(String title);

    void setContent(Object content);
}
