//package com.babyTalk.base.http.httpclient.util;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//import org.slf4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.awt.*;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//import java.util.*;
//import java.util.List;
//
//@Service
//public class HttpUtils {
//    @Autowired
//    private Logger logger;
//    @Autowired
//    private PoolingHttpClientConnectionManager poolConnManager;
//    @Autowired
//    private RequestConfig requestConfig;
//
//    public CloseableHttpClient getConnection() {
//        RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
//        return HttpClients.custom()
//                // 设置连接池管理
//                .setConnectionManager(poolConnManager)
//                .setDefaultRequestConfig(config)
//                // 设置重试次数
//                .setRetryHandler(new DefaultHttpRequestRetryHandler(2, false)).build();
//    }
//
//    /**
//     * 发起Get请求（无参）
//     *
//     * @param url：请求地址
//     * @return map
//     */
//    public String httpGet(String url) {
//        CloseableHttpClient httpClient = getConnection();
//        HttpGet httpGet = new HttpGet(url);
//        CloseableHttpResponse response = null;
//        try {
//            response = httpClient.execute(httpGet);
//            String result = EntityUtils.toString(response.getEntity(), "utf-8");
//            int code = response.getStatusLine().getStatusCode();
//            if (code == HttpStatus.SC_OK) {
//                return result;
//            } else {
//                logger.error("请求{}返回错误码：{},{}", url, code, result);
//                return null;
//            }
//        } catch (IOException e) {
//            logger.error("http请求异常，{}", url, e);
//        } finally {
//            try {
//                if (response != null)
//                    response.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//
//    /**
//     * 有参GET请求
//     *
//     * @param url：请求地址
//     * @param param：参数
//     * @return map
//     */
//    public String httpGet(String url, Map<String, Object> param) {
//        CloseableHttpClient httpClient = getConnection();
//        Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
//        int i = 0;
//        StringBuilder urlBuilder = new StringBuilder(url);
//        while (it.hasNext()) {
//            Map.Entry<String, Object> entry = it.next();
//            if (i == 0) {
//                urlBuilder.append("?").append(entry.getKey()).append("=").append(entry.getValue());
//            } else {
//                urlBuilder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
//            }
//            i++;
//        }
//        url = urlBuilder.toString();
//        System.out.println(url);
//        HttpGet httpGet = new HttpGet(url);
//        CloseableHttpResponse response = null;
//        try {
//            response = httpClient.execute(httpGet);
//            String result = EntityUtils.toString(response.getEntity(), "utf-8");
//            int code = response.getStatusLine().getStatusCode();
//            if (code == HttpStatus.SC_OK) {
//                return result;
//            } else {
//                logger.error("请求{}返回错误码：{},{}", url, code, result);
//                return null;
//            }
//        } catch (IOException e) {
//            logger.error("http请求异常，{}", url, e);
//        } finally {
//            try {
//                if (response != null)
//                    response.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public Map<String,Object> httpGetReturnMap(String url, Map<String, Object> param) {
//        CloseableHttpClient httpClient = getConnection();
//        Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
//        int i = 0;
//        StringBuilder urlBuilder = new StringBuilder(url);
//        while (it.hasNext()) {
//            Map.Entry<String, Object> entry = it.next();
//            if (i == 0) {
//                urlBuilder.append("?").append(entry.getKey()).append("=").append(entry.getValue());
//            } else {
//                urlBuilder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
//            }
//            i++;
//        }
//        url = urlBuilder.toString();
//        System.out.println(url);
//        HttpGet httpGet = new HttpGet(url);
//        CloseableHttpResponse response = null;
//        try {
//            response = httpClient.execute(httpGet);
//            String result = EntityUtils.toString(response.getEntity(), "utf-8");
//            int code = response.getStatusLine().getStatusCode();
//            if (code == HttpStatus.SC_OK) {
//                return (Map<String, Object>) JSONObject.parse(result);
//            } else {
//                logger.error("请求{}返回错误码：{},{}", url, code, result);
//                return null;
//            }
//        } catch (IOException e) {
//            logger.error("http请求异常，{}", url, e);
//        } finally {
//            try {
//                if (response != null)
//                    response.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public String post(String uri, Object params) {
//        CloseableHttpClient httpClient = getConnection();
//        HttpPost httpPost = new HttpPost(uri);
//        CloseableHttpResponse response = null;
//        try {
//            StringEntity paramEntity = new StringEntity(JSON.toJSONString(params), StandardCharsets.UTF_8);
//            paramEntity.setContentEncoding("UTF-8");
//            paramEntity.setContentType("application/json;charset=utf-8");
//            httpPost.setEntity(paramEntity);
//            response = httpClient.execute(httpPost);
//            int code = response.getStatusLine().getStatusCode();
//            String result = EntityUtils.toString(response.getEntity());
//            if (code == HttpStatus.SC_OK) {
//                return result;
//            } else {
//                logger.error("请求{}返回错误码:{},请求参数:{},{}", uri, code, params, result);
//                return null;
//            }
//        } catch (IOException e) {
//            logger.error("收集服务配置http请求异常", e);
//        } finally {
//            try {
//                if (response != null) {
//                    response.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    /**
//     * post请求返回map
//     * @param uri
//     * @param params
//     * @return
//     */
//    public JSONObject postReturnMap(String uri, Object params) {
//        CloseableHttpClient httpClient = getConnection();
//        HttpPost httpPost = new HttpPost(uri);
//        CloseableHttpResponse response = null;
//        try {
//            StringEntity paramEntity = new StringEntity(JSON.toJSONString(params), StandardCharsets.UTF_8);
//            paramEntity.setContentEncoding("UTF-8");
//            paramEntity.setContentType("application/json;charset=utf-8");
//            httpPost.setEntity(paramEntity);
//            response = httpClient.execute(httpPost);
//            int code = response.getStatusLine().getStatusCode();
//            String result = EntityUtils.toString(response.getEntity());
//            if (code == HttpStatus.SC_OK) {
//                HttpEntity entity = response.getEntity();
//                return JSON.parseObject(EntityUtils.toString(entity, "UTF-8"));
//            } else {
//                logger.error("请求{}返回错误码:{},请求参数:{},{}", uri, code, params, result);
//                return null;
//            }
//        } catch (IOException e) {
//            logger.error("收集服务配置http请求异常", e);
//        } finally {
//            try {
//                if (response != null) {
//                    response.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    /**
//     * post请求返回map，含请求头
//     * @param uri
//     * @param params
//     * @return
//     */
//    public Map<String, Object> postReturnMap(String uri, Map<String,Object> params, Map<String,String> headers) {
//        CloseableHttpClient httpClient = getConnection();
//        HttpPost httpPost = new HttpPost(uri);
//        CloseableHttpResponse response = null;
//        try {
//           // 添加参数
//            List<NameValuePair> nameValuePairs = new ArrayList<>();
//            if (params.size() != 0) {
//                // 将mapdata中的key存在set集合中，通过迭代器取出所有的key，再获取每一个键对应的值
//                Set keySet = params.keySet();
//                Iterator it = keySet.iterator();
//                while (it.hasNext()) {
//                    String k =  it.next().toString();// key
//                    Object v = params.get(k);// value
//                    nameValuePairs.add(new BasicNameValuePair(k, (String) v));
//                }
//            }
//            httpPost.setEntity( new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
//          for(String key:headers.keySet()){
//                httpPost.setHeader(key,headers.get(key));
//            }
//            response = httpClient.execute(httpPost);
//            int code = response.getStatusLine().getStatusCode();
//            String result = EntityUtils.toString(response.getEntity());
//            if (code == HttpStatus.SC_OK) {
//                return (Map<String, Object>) JSONObject.parse(result);
//            } else {
//                logger.error("请求{}返回错误码:{},请求参数:{},{}", uri, code, params, result);
//                return null;
//            }
//        } catch (IOException e) {
//            logger.error("收集服务配置http请求异常", e);
//        } finally {
//            try {
//                if (response != null) {
//                    response.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public InputStream postGetInputStream(String uri, Map<String, Object> params) {
//        CloseableHttpClient httpClient = getConnection();
//        HttpPost httpPost = new HttpPost(uri);
//        CloseableHttpResponse response = null;
//        try {
//            StringEntity paramEntity = new StringEntity(JSON.toJSONString(params), StandardCharsets.UTF_8);
//            paramEntity.setContentEncoding("UTF-8");
//            paramEntity.setContentType("application/json;charset=utf-8");
//            httpPost.setEntity(paramEntity);
//            response = httpClient.execute(httpPost);
//            int code = response.getStatusLine().getStatusCode();
//            return response.getEntity().getContent();
////
//
////            if (code == HttpStatus.SC_OK) {
////                return result;
////            } else {
////                logger.error("请求{}返回错误码:{},请求参数:{},{}", uri, code, params,result);
////                return null;
////            }
//        } catch (IOException e) {
//            logger.error("收集服务配置http请求异常", e);
//        } finally {
////            try {
////                if(response != null) {
////                    response.close();
////                }
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//        }
//        return null;
//    }
//
////    public String sendPostByForm(String url, Map<String,String> map,int reSend) {
////        //声明返回结果
////        String result = "";
////        //开始请求API接口时间
////        long startTime=System.currentTimeMillis();
////        //请求API接口的响应时间
////        long endTime= 0L;
////        HttpEntity httpEntity = null;
////        UrlEncodedFormEntity entity = null;
////        HttpResponse httpResponse = null;
////        CloseableHttpClient httpClient = null;
////        try {
////            // 创建连接
////             httpClient=getConnection();
////            // 设置请求头和报文
////            HttpPost httpPost = new HttpPost(url);
////            //设置参数
////            List<NameValuePair> list = new ArrayList<NameValuePair>();
////            Iterator iterator = map.entrySet().iterator();
////            while(iterator.hasNext()){
////                Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
////                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
////            }
////            entity = new UrlEncodedFormEntity(list,HttpConstant.UTF8_ENCODE);
////            httpPost.setEntity(entity);
////            logger.info("请求{}接口的参数为{}",url,map);
////            //执行发送，获取相应结果
////            httpResponse = httpClient.execute(httpPost);
////            httpEntity= httpResponse.getEntity();
////            result = EntityUtils.toString(httpEntity);
////        } catch (Exception e) {
////            logger.error("请求{}接口出现异常",url,e);
////            if (reSend > 0) {
////                logger.info("请求{}出现异常:{}，进行重发。进行第{}次重发",url,e.getMessage(),(HttpConstant.REQ_TIMES-reSend +1));
////                result = sendPostByForm(url, map, reSend - 1);
////                if (result != null && !"".equals(result)) {
////                    return result;
////                }
////            }
////        }finally {
////            try {
////                EntityUtils.consume(httpEntity);
////            } catch (IOException e) {
////                logger.error("http请求释放资源异常",e);
////            }
////        }
////        //请求接口的响应时间
////        endTime=System.currentTimeMillis();
////        logger.info("请求{}接口的响应报文内容为{},本次请求API接口的响应时间为:{}毫秒",url,result,(endTime-startTime));
////        return result;
////
////    }
//
//    /**
//     *
//     * @param url
//     * @param paarms
//     * @return
//     */
//    public JSONObject postForForm(String url, Map<String, String> params) {
//        CloseableHttpClient httpClient = getConnection();
//        HttpPost httpPost = new HttpPost(url);
//        ArrayList<BasicNameValuePair> list = new ArrayList<>();
//        params.forEach((key, value) -> list.add(new BasicNameValuePair(key, value)));
//        try {
//            if (Objects.nonNull(params) && params.size() >0) {
//                httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
//            }
//            InputStream content = httpPost.getEntity().getContent();
//            InputStreamReader inputStreamReader = new InputStreamReader(content, "UTF-8");
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String readLine = bufferedReader.readLine();
//            System.out.println("readLine===================================" + readLine);
//            HttpResponse response = httpClient.execute(httpPost);
//            HttpEntity entity = response.getEntity();
//            JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(entity, "UTF-8"));
//            System.out.println(jsonObject);
//            System.out.println("啊这");
//            System.out.println(httpPost.getURI());
//            return jsonObject;
//
//        }catch (IOException e) {
//            System.out.println(httpPost.getURI());
//            System.out.println("有报错？");
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * multipart/form-data  单个文件
//     * @param url
//     * @param params
//     * @param fileName
//     * @param file
//     * @return
//     */
//    public JSONObject postForMultipart(String url, Map<String, String> params, String fileName,MultipartFile file) {
//        CloseableHttpClient httpClient = getConnection();
//        HttpPost httpPost = new HttpPost(url);
//        try {
//            httpPost.setHeader("Accept-Language","zh-CN,zh;q=0.9");
//            httpPost.setHeader("Accept-Encoding","gzip, deflate");  //像header这些自己去设置吧
//
//            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//            builder.addBinaryBody(fileName, file.getBytes(), ContentType.MULTIPART_FORM_DATA,"aa");//添加文件
//            for(String key:params.keySet()){
//                builder.addTextBody(key, params.get(key));
//            }
//            httpPost.setEntity(builder.build());
//            HttpResponse response = httpClient.execute(httpPost);
//            HttpEntity entity = response.getEntity();
//            JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(entity, "UTF-8"));
//            System.out.println(jsonObject);
//            System.out.println(httpPost.getURI());
//            return jsonObject;
//
//        }catch (IOException e) {
//            System.out.println(httpPost.getURI());
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * multipart/form-data  无文件
//     * @param url
//     * @param params
//     * @return
//     */
//    public JSONObject postForMultipart(String url, Map<String, String> params) {
//        CloseableHttpClient httpClient = getConnection();
//        HttpPost httpPost = new HttpPost(url);
//        try {
//            httpPost.setHeader("Accept-Language","zh-CN,zh;q=0.9");
//            httpPost.setHeader("Accept-Encoding","gzip, deflate");  //像header这些自己去设置吧
//
//            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//            for(String key:params.keySet()){
//                builder.addTextBody(key, params.get(key));
//            }
//            httpPost.setEntity(builder.build());
//            HttpResponse response = httpClient.execute(httpPost);
//            HttpEntity entity = response.getEntity();
//            JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(entity, "UTF-8"));
//            System.out.println(jsonObject);
//            System.out.println(httpPost.getURI());
//            return jsonObject;
//
//        }catch (IOException e) {
//            System.out.println(httpPost.getURI());
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//}
////    Bearer A21AAIR-77Bkkj-NpoXAV38KshxuAa0fq4swl1mTsQC9ldSmtdNqCX7OeWPQ70-oMNqRwMM-CvVAo44rNeh_LG6mBnWpR8mVg