package com.pan.service;


import com.pan.pojo.Blog;
import com.pan.vo.PageVo;
import org.apache.solr.client.solrj.response.QueryResponse;

public interface SolrService {

    //或取查询结果
    QueryResponse getQueryResult(Integer page, String query);
    //将查询结果包装为vo,和上一个方法分开的原因是controller有可能用到response
    PageVo warpResponseToPageVo(Integer page, QueryResponse response);
    //保存或更新blog到solr,因为add与update是同一个方法
    void saveBlogToSolr(Blog blog);
    //根据id删除solr中对应的博客
    void deleteBlog(Integer id);
}
