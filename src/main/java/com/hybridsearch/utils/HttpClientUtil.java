package com.hybridsearch.utils;


import com.google.gson.Gson;
import com.hybridsearch.model.Face;
import com.hybridsearch.model.SearchModel;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.List;


/**
 * @Author Anthony Z.
 * @Date 23/12/2022
 * @Description:
 */
public class HttpClientUtil {

    private static CloseableHttpClient httpClient = null;
    private final static Object syncLock = new Object();
    private static Gson gson = new Gson();
    private final static String URI = "http://81.70.159.39:33388/feature";
    private final static String address = "http://81.70.159.39/";


    public static CloseableHttpClient getHttpClient(String url){
        String hostName = url.split("/")[2];
        int port = 80;
        if(hostName.contains(":")){
            String[] arr = hostName.split(":");
            hostName = arr[0];
            port = Integer.parseInt(arr[1]);
        }

        if(httpClient == null){
            synchronized (syncLock){
                if(httpClient == null){
                    httpClient = createHttpClient(200, 40, 100,
                            hostName, port);
                }
            }
        }

        return httpClient;
    }

    public static CloseableHttpClient createHttpClient(int maxTotal, int maxPerRoute, int maxRoute,
                                                       String hostName, int port){

        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf).register("https", sslsf).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);

        cm.setMaxTotal(maxTotal); // 设置最大连接数
        cm.setDefaultMaxPerRoute(maxPerRoute); // 设置每个路由器最大连接基础
        cm.setMaxPerRoute(new HttpRoute(new HttpHost(hostName, port)), maxRoute); //设置目标主机的最大连接数

        // 请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = (exception, executionCount, context) -> {

            if (executionCount >= 5) {// 如果已经重试了5次，就放弃
                return false;
            }
            if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                return true;
            }
            if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                return false;
            }
            if (exception instanceof InterruptedIOException) {// 超时
                return false;
            }
            if (exception instanceof UnknownHostException) {// 目标服务器不可达
                return false;
            }
            if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                return false;
            }
            if (exception instanceof SSLException) {// SSL握手异常
                return false;
            }

            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            if (!(request instanceof HttpEntityEnclosingRequest)) {// 如果请求是幂等的，就再次尝试
                return true;
            }

            return false;
        };

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setRetryHandler(httpRequestRetryHandler).build();

        return httpClient;

    }

    /**
     * http post json request
     * @param jsonParam
     * @return
     */
    public static String post(JSONObject jsonParam){
        return post(jsonParam.toString());
    }

    /**
     * http post request
     * @param value
     * @return
     */
    public static String post(String value){
        StringEntity entity = new StringEntity(value, "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(10000)
                .setConnectTimeout(10000).setSocketTimeout(10000).build();
        HttpPost httpPost = new HttpPost(URI);
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Connection", "close");
        httpPost.setEntity(entity);

        CloseableHttpClient httpClient = getHttpClient(URI);

        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity entity2 = response.getEntity();
                return EntityUtils.toString(entity2, "UTF-8");
            } finally {
                response.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Detect the face using verifier
     * @param base64
     * @return
     */
    public static String detectFace(String base64){
        JSONObject jsonParam = new JSONObject();
        String result = null;
        try{
            jsonParam.put("api_key", "");
            jsonParam.put("image_base64", base64);
            result = post(jsonParam);

        }catch (JSONException ex){
            ex.printStackTrace();
        }

        return result;
    }

    public static String getFeature(List<Face> faces){
        SearchModel searchModel = new SearchModel();
        searchModel.api_key = "";
        searchModel.faces = faces;
        String json = gson.toJson(searchModel);

        try{
            String result = post(json);

            // testing code
            System.out.println(result);

            return result;
        }catch (Exception ex){
            System.err.println(ex);
        }

        return null;
    }
}
