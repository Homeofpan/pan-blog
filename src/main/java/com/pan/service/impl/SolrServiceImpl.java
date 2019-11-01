package com.pan.service.impl;

import com.pan.pojo.Blog;
import com.pan.service.SolrService;
import com.pan.service.TypeService;
import com.pan.service.UserService;
import com.pan.vo.PageVo;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

@Service
public class SolrServiceImpl implements SolrService{

    @Autowired
    private SolrClient solrClient;
    @Autowired
    private TypeService typeService;
    @Autowired
    private UserService userService;

    @Override
    public QueryResponse getQueryResult(Integer page,String query) {
        SolrQuery solrQuery = new SolrQuery();
        //查询条件若为空,则设置查询所有
        if(!StringUtils.isEmpty(query)){
            solrQuery.set("q", "blog_keyword:" + query);
        }else{
            solrQuery.set("q", "*:*");
        }
        //设置排序
        solrQuery.setSort("blog_update_time", SolrQuery.ORDER.desc);
        //设置查询页数
        int startNum = 0;
        if (page != 0) {
            startNum = page - 1;
        }
        solrQuery.setStart(startNum * 7);   //从第几条的下一条开始
        solrQuery.setRows(7);           //共获取几条

        //执行查询
        QueryResponse response = null;
        try {
            response = solrClient.query(solrQuery);
        } catch (Exception e) {
            e.printStackTrace();
//            throw new RuntimeException("传输出现错误,请稍后重试");
        }
        if(response == null){
            throw new RuntimeException("传输出现错误,请稍后重试");
        }
        return response;
    }

    @Override
    public PageVo warpResponseToPageVo(Integer page,QueryResponse response) {
        List<Blog> beans = response.getBeans(Blog.class);
        if(beans.size() != 0 && beans != null){
            for (Blog b : beans) {
                b.setUser(userService.findUserById(b.getUid()));
                b.setType(typeService.getTypeById(b.getTypeid()));
            }
        }



        //总匹配数,将response.getResults().getNumFound()的long类型转换成Integer类型
        Integer totalElements = Math.toIntExact(response.getResults().getNumFound());
        //总页数
        Integer totalPages = 0;
        if (totalElements != 0) {
            totalPages = totalElements / 7;
            if (totalElements % 7 != 0) {
                totalPages += 1;
            }
        }

        PageVo pageVo = new PageVo();
        pageVo.setContent(beans);                       // 博客列表
        pageVo.setTotalPages(totalPages);               //总页数
        pageVo.setNumber(page == 0  ? (page+ 1):page);                     //当前页数
        pageVo.setFirst(page == 0 ? true : false);      //是否首页
        pageVo.setLast(page == totalPages ? true : false);        //是否尾页
        pageVo.setTotalElements(totalElements);         //总匹配数
        return pageVo;
    }

    @Override
    public void saveBlogToSolr(Blog blog) {
        try {
            UpdateResponse response = solrClient.addBean(blog);
            solrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("solr更新失败");
        }
    }

    @Override
    public void deleteBlog(Integer id) {
        try {
            solrClient.deleteByQuery("id:"+id);
            solrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
