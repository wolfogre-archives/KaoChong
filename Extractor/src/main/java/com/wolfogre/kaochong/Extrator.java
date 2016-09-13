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

    @Autowired
    Database database;

    @Autowired
    Console console;

    public void extratToSqlite() {
        Document doc = Jsoup.parse(fileLoader.getOriginHtml());
        Element groups = doc.getElementById("j-groups");
        Elements dataList = groups.getElementsByClass("group_title");
        Elements dataCourseList = groups.getElementsByClass("group_cons_list");
        for(int i = 0; i < dataList.size(); ++i) {
            String data = dataList.get(i).text();
            for(Element e : dataCourseList.get(i).getElementsByClass("group_cons")) {
                Elements spans = e.select("span");
                String timeRange = new StringBuilder(spans.get(0).text()).deleteCharAt(6).toString(); // 09:00- 11:00 -> 09:00-11:00
                database.insertCourse(data + " " + timeRange.split("-")[0], timeRange, spans.get(1).text(), spans.get(2).text());
                console.writeLine(data + " " + timeRange.split("-")[0] + " | " + timeRange + " | " + spans.get(1).text() + " | " + spans.get(2).text());
            }
        }

    }
}
