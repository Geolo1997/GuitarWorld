package pers.geolo.guitarworldserver.entity;

import java.util.Date;
import java.util.List;

/**
 * 创作类
 */
public class Works {
    // 作品编号
    private int id;
    // 作者
    private String author;
    // 创作时间
    private Date createTime;
    // 创作类型
    private WorksType type;
    // 标题
    private String title;
    // 内容
    private String content;
    // 图片url
    private List<String> imageUrls;
    // 视频url
    private String videoUrl;
    // 视频预览图url
    private String videoPreviewUrl;
    // 评论
    private List<Comment> comments;
    // 点赞数
    private int likeCount;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public WorksType getType() {
        return type;
    }

    public void setType(WorksType type) {
        this.type = type;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getVideoPreviewUrl() {
        return videoPreviewUrl;
    }

    public void setVideoPreviewUrl(String videoPreviewUrl) {
        this.videoPreviewUrl = videoPreviewUrl;
    }

    @Override
    public String toString() {
        return "Works{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", createTime=" + createTime +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imageUrls=" + imageUrls +
                ", videoUrl='" + videoUrl + '\'' +
                ", comments=" + comments +
                '}';
    }
}
