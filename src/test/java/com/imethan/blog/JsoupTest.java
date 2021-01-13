package com.imethan.blog;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

/**
 * @Name JsoupTest
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-13 10:16
 */
public class JsoupTest {

    public void test(String content){
        String url = "http://imethan.cn";
        org.jsoup.nodes.Document doc = Jsoup.parse(content);
        Elements linkList = doc.head().getElementsByTag("link");
        linkList.forEach(item->{
            String href = item.attr("href");
            item.attr("href",url+href);
        });

        Elements scriptList = doc.head().getElementsByTag("script");
        scriptList.forEach(item->{
            String src = item.attr("src");
            item.attr("src",url+src);
        });

        Elements aList = doc.body().getElementsByClass("nav-link").tagName("a");
        aList.forEach(item->{
            String href = item.attr("href");
            if(!"https://github.com/ethanwong".equals(href)){
                item.attr("href",url+href);
            }

        });

        content = doc.html().toString();
    }
}
