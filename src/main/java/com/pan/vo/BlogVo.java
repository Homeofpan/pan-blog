package com.pan.vo;

import com.pan.pojo.Type;

import java.util.Date;

public class BlogVo{
    private Type type;

    private Integer id;

    private Integer uid;

    private Integer typeid;

    private String tagids;

    private String title;

    private String firstPicture;

    private Integer views;

    //是否开启赞赏0:false,1:true
    private Integer appreciation;
    //是否开启转载声明0:false,1:true
    private Integer shareStatement;
    //是否开启评论0:false,1:true
    private Integer commentabled;
    //是否发布0:false,1:true
    private Integer published;
    //是否推荐0:false,1:true
    private Integer recommend;
    //是否原创
    private Integer flag;

    private Date createTime;

    private Date updateTime;

    private String content;

    private String tagIds;

    private String description;

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public BlogVo() {
    }

    @Override
    public String toString() {
        return "BlogVo{" +
                "type=" + type +
                ", id=" + id +
                ", uid=" + uid +
                ", typeid=" + typeid +
                ", tagids='" + tagids + '\'' +
                ", title='" + title + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", flag=" + flag +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", content='" + content + '\'' +
                ", tagIds='" + tagIds + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getTagids() {
        return tagids;
    }

    public void setTagids(String tagids) {
        this.tagids = tagids;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(Integer appreciation) {
        this.appreciation = appreciation;
    }

    public Integer getShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(Integer shareStatement) {
        this.shareStatement = shareStatement;
    }

    public Integer getCommentabled() {
        return commentabled;
    }

    public void setCommentabled(Integer commentabled) {
        this.commentabled = commentabled;
    }

    public Integer getPublished() {
        return published;
    }

    public void setPublished(Integer published) {
        this.published = published;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
