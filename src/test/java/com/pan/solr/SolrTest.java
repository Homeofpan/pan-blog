package com.pan.solr;

import com.pan.pojo.Blog;
import com.pan.service.BlogService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import java.io.IOException;

/**
 * Created by pan tao on 2019/10/29
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class SolrTest {

    @Autowired
    private SolrClient solrClient;
    @Autowired
    private BlogService blogService;

/*    @Test
    public void addBlogToSolr() throws IOException, SolrServerException {
        Blog bolg = blogService.getBolg(14);
        solrClient.addBean(bolg);
        solrClient.commit();
    }*/

    @Test
    public void updateBlogFromSolr() throws IOException, SolrServerException {
        Blog bolg = blogService.getBolg(35);
        UpdateResponse response = solrClient.addBean(bolg);
        solrClient.commit();

    }

}
