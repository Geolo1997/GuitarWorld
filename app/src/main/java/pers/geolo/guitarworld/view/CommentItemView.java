package pers.geolo.guitarworld.view;

import java.util.Date;

import pers.geolo.guitarworld.view.list.ListItemView;

public interface CommentItemView  extends ListItemView {
    
    String getAuthor();
    
    void setAuthor(String author);

    void setCreateTime(Date createTime);

    void setContent(String content);

    void showOptions(String[] options);
}
