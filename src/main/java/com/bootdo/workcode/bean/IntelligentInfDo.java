package com.bootdo.workcode.bean;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/06/18 16:33 <br>
 * @ 数据库实体类  ， es 对象
 * @see com.bootdo.workcode.bean <br>
 */
public class IntelligentInfDo {

    private long id;

    private String infId;

    private String content;

    private String publishAt;

    private String title;

    private String summary;

    private String author;

    private String thumbnailType;

    private String ccbContent;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInfId() {
        return infId;
    }

    public void setInfId(String infId) {
        this.infId = infId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(String publishAt) {
        this.publishAt = publishAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumbnailType() {
        return thumbnailType;
    }

    public void setThumbnailType(String thumbnailType) {
        this.thumbnailType = thumbnailType;
    }

    public String getCcbContent() {
        return ccbContent;
    }

    public void setCcbContent(String ccbContent) {
        this.ccbContent = ccbContent;
    }

    public IntelligentInfDo(long id, String infId, String content, String publishAt, String title, String summary, String author, String thumbnailType, String ccbContent) {
        this.id = id;
        this.infId = infId;
        this.content = content;
        this.publishAt = publishAt;
        this.title = title;
        this.summary = summary;
        this.author = author;
        this.thumbnailType = thumbnailType;
        this.ccbContent = ccbContent;
    }

    public IntelligentInfDo(long id, String infId, String content, String publishAt, String title) {
        this.id = id;
        this.infId = infId;
        this.content = content;
        this.publishAt = publishAt;
        this.title = title;
    }

    public IntelligentInfDo() {
    }
}
