//package com.babyTalk.base.http.httpclient.config;
//
//import lombok.Data;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@Data
//@ConfigurationProperties(prefix = "http")
//public class HttpClientConfig {
//    /**
//     * 最大连接数
//     */
//    private int maxTotal;
//    /**
//     * 最大路由连接数
//     */
//    private int maxPreRoute;
//
//    /**
//     * 连接超时时间
//     */
//    private int CONNECT_TIMEOUT;
//
//    /**
//     * 请求超时时间
//     */
//    private int CONNECTION_REQUEST_TIMEOUT;
//
//    /**
//     * socket读写超时时间
//     */
//    private int SOCKET_TIMEOUT;
//
//    @Bean
//    public PoolingHttpClientConnectionManager getPoolConnManager() {
//        PoolingHttpClientConnectionManager poolConnManager = new PoolingHttpClientConnectionManager();
//        poolConnManager.setMaxTotal(maxTotal);
//        poolConnManager.setDefaultMaxPerRoute(maxPreRoute);
//        return poolConnManager;
//    }
//
//    @Bean
//    public RequestConfig getRequestConfig() {
//        return RequestConfig.custom()
//                .setConnectTimeout(CONNECT_TIMEOUT)
//                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
//                .setSocketTimeout(SOCKET_TIMEOUT)
//                .build();
//    }
//}
