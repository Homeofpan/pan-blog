package com.pan.pojo;

import java.io.Serializable;

public class Tag  implements Serializable {
    private Integer id;

    private String name;

    private Integer blogs;

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getBlogs() {
        return blogs;
    }

    public void setBlogs(Integer blogs) {
        this.blogs = blogs;
    }
}