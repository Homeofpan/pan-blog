package com.pan.pojo;

import java.io.Serializable;
import java.util.List;

public class BlogByYear  implements Serializable {
    //对应年份
    private String key;
    //对应年份下面的所有博客信息
    private List<Blog> value;

    @Override
    public String toString() {
        return "BlogByYear{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Blog> getValue() {
        return value;
    }

    public void setValue(List<Blog> value) {
        this.value = value;
    }
}
