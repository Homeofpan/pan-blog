package com.pan.vo;

import java.util.List;

public class TypeVo {

    //此处的list为查出来的数据集
    private List content;

    private Integer number;
    private Boolean first;
    private Boolean last;
    private Integer totalPages;

    @Override
    public String toString() {
        return "TypeVo{" +
                "content=" + content +
                ", number=" + number +
                ", first=" + first +
                ", last=" + last +
                ", totalPages=" + totalPages +
                '}';
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

    public TypeVo() {

    }
}
