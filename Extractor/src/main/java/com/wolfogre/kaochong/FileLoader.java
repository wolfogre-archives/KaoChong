package com.wolfogre.kaochong;

import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by wolfogre on 9/13/16.
 */
@Service
public class FileLoader {
    public String getOriginHtml() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the path of HTML file:");
        String filePath = scanner.nextLine();
        File file = new File(filePath);
        while(!file.isFile() || !file.exists()) {
            System.out.println("Can not find :" + filePath);
            System.out.println("Please input the path of HTML file:");
            filePath = scanner.nextLine();
            file = new File(filePath);
        }
        scanner.close();
        StringBuilder result = new StringBuilder();
        try {
            scanner = new Scanner(new FileInputStream(file));
            while(scanner.hasNext())
                result.append(scanner.nextLine()).append(System.getProperty("line.separator"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result.toString();
    }
}
