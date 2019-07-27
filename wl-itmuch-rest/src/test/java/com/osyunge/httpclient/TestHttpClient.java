package com.osyunge.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

public class TestHttpClient {
    @Test
    public void testHttpGet() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet("http://www.baidu.com");
        CloseableHttpResponse response = httpClient.execute(get);
        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity,"UTF-8");
        System.out.println(html);
        response.close();
        httpClient.close();
    }

}
