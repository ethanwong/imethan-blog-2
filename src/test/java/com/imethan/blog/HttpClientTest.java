package com.imethan.blog;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Name HttpClientTest
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-12 18:18
 */
public class HttpClientTest {

    private final String EMAIL_TEMPLATE_URL = "http://127.0.0.1/email";

    private String getEmailContent(String path) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(EMAIL_TEMPLATE_URL + "?path=" + path)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void main(String[] args) {
        HttpClientTest httpClientTest = new HttpClientTest();
        String content = httpClientTest.getEmailContent("/home/mongodb-export/20210112/imethan-blog-2-20210112.tar.gz ");
        System.out.println(content);
    }
}
