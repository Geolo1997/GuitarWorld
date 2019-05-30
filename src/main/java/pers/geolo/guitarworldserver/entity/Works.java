package pers.geolo.guitarworldserver.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 创作类
 */
public class Works<T> {
    // 作品编号
    private int id;
    // 作者
    private String author;
    //创作时间
    private Date createTime;
    // 标题
    private String title;
    // 内容
    private T content;
    // 评论
    private List<Comment> comments;

    private List<String> imagePaths = new ArrayList<>();

    public void addImage(String imagePath) {
        imagePaths.add(imagePath);
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public Works() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    @Override
    public String toString() {
        return "Works{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", createTime=" + createTime +
                ", title='" + title + '\'' +
                ", content=" + content +
                ", comments=" + comments +
                '}';
    }
}
