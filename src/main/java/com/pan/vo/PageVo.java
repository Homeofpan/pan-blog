package com.pan.vo;

import java.util.List;

public class PageVo {

    //此处的list为查出来的数据集
    private List content;
    //当前页数
    private Integer number;
    //是否首页
    private Boolean first;
    //是否尾页
    private Boolean last;
    //总页数
    private Integer totalPages;
    //总查询数量
    private long totalElements;

    @Override
    public String toString() {
        return "PageVo{" +
                "content=" + content +
                ", number=" + number +
                ", first=" + first +
                ", last=" + last +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                '}';
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List getContent() {
        return content;
    }

    public void setContent(List content) {
        this.content = content;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public Boolean getFirst() {

        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public PageVo() {

    }
}
