package util;


import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.xmlrpc.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);


    /**
     * 执行一个带参数的HTTP GET请求，返回请求响应的JSON字符串
     *
     * @param url 请求的URL地址
     * @return 返回请求响应的JSON字符串
     */
    public static String doGet(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS))
                .build();
        Request request = new Request.Builder()

                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {

            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
