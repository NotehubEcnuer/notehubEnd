package com.ecnu.notehub.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author onion
 * @date 2019/11/7 -1:23 下午
 */
@Configuration
public class ElasticRestClient {
    private String ipAddress = "139.9.217.28";
    private int port = 9200;
    @Bean(name = "highLevelClient")
    public RestHighLevelClient highLevelClient(){
        HttpHost httpHost = new HttpHost(ipAddress, port, "http");
        return new RestHighLevelClient(RestClient.builder(httpHost));
    }
}
