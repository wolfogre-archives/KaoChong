package com.wolfogre.kaochong;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Created by wolfogre on 9/13/16.
 */
@Service
public class Extrator {

    @Autowired
    FileLoader fileLoader;

    @Autowired
    Console console;

    public void extratToICS() {
        Document doc = Jsoup.parse(fileLoader.getOriginHtml());
        Element groups = doc.getElementById("j-groups");
        Elements dataList = groups.getElementsByClass("group_title");
        Elements dataCourseList = groups.getElementsByClass("group_cons_list");
        console.writeLine("Please input the out path of ics:");
        FileOutputStream fileOutputStream = null;

        console.writeLine("--------start of ics--------");

        console.writeLine("BEGIN:VCALENDAR");
        console.writeLine("VERSION:1.0");
        console.writeLine("PRODID:vCal ID default");
        for(int i = 0; i < dataList.size(); ++i) {
            String data =  new StringBuilder(dataList.get(i).text()).deleteCharAt(4).deleteCharAt(6).toString(); // 2016-09-19 -> 20160919
            for(Element e : dataCourseList.get(i).getElementsByClass("group_cons")) {
                Elements spans = e.select("span");
                String timeRange = new StringBuilder(spans.get(0).text()).deleteCharAt(6).toString(); // 09:00- 11:00 -> 09:00-11:00
                String startTime = new StringBuilder(timeRange.split("-")[0]).deleteCharAt(2).append("00").toString(); // 09:00 -> 090000
                String endTime = new StringBuilder(timeRange.split("-")[1]).deleteCharAt(2).append("00").toString(); // 09:00 -> 090000
                console.writeLine("BEGIN:VEVENT");
                console.writeLine("DESCRIPTION:狼煞博客 http://blog.wolfogre.com");
                console.writeLine("DTEND:" + data + "T" + endTime);
                console.writeLine("DTSTART:" + data + "T" + startTime);
                console.writeLine("ETZ:Asia/Shanghai");
                console.writeLine("SUMMARY:【考虫六级】" + spans.get(1).text() + "-" + spans.get(2).text());
                console.writeLine("REMINDER:15");
                console.writeLine("REMINDER:3");
                console.writeLine("OWNER:nobody@localhost");
                console.writeLine("END:VEVENT");

            }
        }
        console.writeLine("END:VCALENDAR");
        console.writeLine("--------end of ics--------");


    }
}
