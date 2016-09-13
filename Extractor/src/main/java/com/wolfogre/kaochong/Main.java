package com.wolfogre.kaochong;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by wolfogre on 9/13/16.
 */
public class Main {
    public static void main(String[] args){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Extrator extrator = context.getBean(Extrator.class);
        extrator.extratToSqlite();
        context.close();
    }
}
