package com.wolfogre.kaochong;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wolfogre on 9/13/16.
 */
@Service
public class Extrator {
    @Autowired
    FileLoader fileLoader;

    public void extratToSqlite() {
        Document doc = Jsoup.parse(fileLoader.getOriginHtml());
        Element groups = doc.getElementById("j-groups");
        Elements dataList = groups.getElementsByClass("group_title");
        Elements dataCourseList = groups.getElementsByClass("group_cons_list");
        for(int i = 0; i < dataList.size(); ++i) {
            String data = dataList.get(i).text();
            for(Element e : dataCourseList.get(i).getElementsByClass("group_cons")) {
                Elements spans = e.select("span");
                System.out.println(data + " " + spans.get(0).text() + " " + spans.get(1).text() + " " + spans.get(2).text());
            }
        }

    }
}
