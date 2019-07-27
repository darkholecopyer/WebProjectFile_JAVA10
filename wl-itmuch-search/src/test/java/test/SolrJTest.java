package test;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

public class SolrJTest {
    @Test
    public void addDocument(){
        //创建一连接
        SolrServer solrServer = new HttpSolrServer("http://192.168.122.129:8080/solr");
        //创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id","test001");
        document.addField("item_title","测试商品2");
        document.addField("item_price",5432);
        //把文档对象写入索引库
        try {
            solrServer.add(document);
            //提交
            solrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void deleteDocument(){
        //创建一连接
        SolrServer solrServer = new HttpSolrServer("http://192.168.122.129:8080/solr");
        try {
            solrServer.deleteById("test001");
            solrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
