package pers.geolo.guitarworld.model;

import java.util.Date;
import java.util.List;

/**
 * 创作类
 */
public class Works<T> {
    // 作品编号
    private int id;
    // 作者
    private String anthor;
    //创作时间
    private Date createTime;
    // 标题
    private String title;
    // 内容
    private T content;
    // 评论
    private List<Comment> comments;

    public Works() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnthor() {
        return anthor;
    }

    public void setAnthor(String anthor) {
        this.anthor = anthor;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
