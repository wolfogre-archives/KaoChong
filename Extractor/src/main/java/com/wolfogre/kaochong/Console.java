package com.wolfogre.kaochong;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Scanner;

/**
 * Created by wolfogre on 9/13/16.
 */
@Service
public class Console {
    private Scanner scanner;

    @PostConstruct
    public void init() {
        scanner = new Scanner(System.in);
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public void writeLine(String str) {
        System.out.println(str);
    }
}
