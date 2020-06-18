package com.bootdo.common.domain;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/06/18 16:12 <br>
 * @ccb json 转换
 * @see com.bootdo.common.domain <br>
 */
public class InfDO {

    private String id;
    private String content;
    private String publish_at;
    private String title;
    private String summary;
    private String author;
    private String thumbnail_type;
    private String ccb_content;
    private String[] thumbnails;
    private String[] columns;
    private String[] content_images;
    private String[] audio_urls;
    private String[] link_informations;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublish_at() {
        return publish_at;
    }

    public void setPublish_at(String publish_at) {
        this.publish_at = publish_at;
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

    public String getThumbnail_type() {
        return thumbnail_type;
    }

    public void setThumbnail_type(String thumbnail_type) {
        this.thumbnail_type = thumbnail_type;
    }

    public String getCcb_content() {
        return ccb_content;
    }

    public void setCcb_content(String ccb_content) {
        this.ccb_content = ccb_content;
    }

    public String[] getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String[] thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public String[] getContent_images() {
        return content_images;
    }

    public void setContent_images(String[] content_images) {
        this.content_images = content_images;
    }

    public String[] getAudio_urls() {
        return audio_urls;
    }

    public void setAudio_urls(String[] audio_urls) {
        this.audio_urls = audio_urls;
    }

    public String[] getLink_informations() {
        return link_informations;
    }

    public void setLink_informations(String[] link_informations) {
        this.link_informations = link_informations;
    }
}
