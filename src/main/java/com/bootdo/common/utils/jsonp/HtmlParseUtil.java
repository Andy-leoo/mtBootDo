package com.bootdo.common.utils.jsonp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/08/04 18:01 <br>
 * @ 解析 页面 拿到数据
 * @see com.bootdo.common.utils.jsonp <br>
 */
public class HtmlParseUtil {

    public static void main(String[] args) throws IOException {
        new HtmlParseUtil().parseJD("java").forEach(System.out::println);
    }


    public List<JDHtml> parseJD(String keyWord) throws IOException {

        List<JDHtml> list = new ArrayList<>();

        String url = "https://search.jd.com/Search?keyword="+ keyWord;
        System.out.println("URL : "+url);
        Document document = Jsoup.parse(new URL(url), 300000);
        Element element = document.getElementById("J_goodsList");
        //获取所有li标签
        Elements li = element.getElementsByTag("li");

        li.forEach(el ->
        {
            String img = el.getElementsByTag("img").eq(0).attr("source-data-lazy-img");
            String price = el.getElementsByClass("p-price").eq(0).text();
            String name = el.getElementsByClass("p-name").eq(0).text();
            list.add(new JDHtml(img,name,price));
        });

        return list;

    }

}
