package com.pan.pojo;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Blog implements Serializable{
    //博客Id
    @Field("blog_id")
    private Integer id;

    //用户id
    @Field("blog_uid")
    private Integer uid;

    //博客类型id
    @Field("blog_typeid")
    private Integer typeid;

    //博客标签id
    @Field("blog_tagids")
    private String tagids;

    //博客的title
    @Field("blog_title")
    private String title;

    //博客的描述
    @Field("blog_description")
    private String description;

    //博客的首图
    @Field("blog_first_picture")
    private String firstPicture;

    //博客的访问数量
    @Field("blog_views")
    private Integer views;

    //是否开启点赞:0:false,1:true
    @Field("blog_appreciation")
    private Integer appreciation;

    //是否开启转载声明,0:false,1:true
    @Field("blog_share_statement")
    private Integer shareStatement;

    //是否开启评论0:false,1:true
    @Field("blog_commentabled")
    private Integer commentabled;

    //是否发布0:false,1:true
    @Field("blog_published")
    private Integer published;

    //原创,转载,翻译
    @Field("blog_flag")
    private String flag;

    //是否推荐0:false,1:true
    @Field("blog_recommend")
    private Integer recommend;

    //博客创建的时间
    @Field("blog_create_time")
    private Date createTime;

    //博客修改的时间
    @Field("blog_update_time")
    private Date updateTime;

    //博客内容
    @Field("blog_content")
    private String content;

    //自己手动增加
    private Type type;
    //自己手动增加,使这个值和tagids时刻保持一致,即修该两变量的set方法
    private String tagIds;
    //自己手动增加的,用来在index页面展示blog,需要手动赋值该属性
    private User user;
    //自己手动增加的,用来在blog页面展示blog,需要手动赋值该属性
    private List<Tag> tags;
    //solr域中规定的id,必须具备
    @Field("id")
    private String solrId;

    public String getSolrId() {
        return solrId;
    }

    public void setSolrId(String solrId) {
        this.solrId = solrId;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", uid=" + uid +
                ", typeid=" + typeid +
                ", tagids='" + tagids + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", flag='" + flag + '\'' +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", tagIds='" + tagIds + '\'' +
                '}';
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
        this.tagids = this.tagIds;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
        if(type!= null &&type.getId() != null){
            this.typeid = type.getId();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        this.solrId = id+"";
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
        this.tagids = tagids == null ? null : tagids.trim();
        //增加一行
        this.tagIds = this.tagids;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture == null ? null : firstPicture.trim();
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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
        this.content = content == null ? null : content.trim();
    }
}