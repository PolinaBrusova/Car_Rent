package com.example.demo.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class MyLogger {
    public static void inform(String line){
        String finalLine = "ИНФОРМАЦИЯ ОТ СЕРВЕРА "+ LocalDate.now() +": "+line+"\n";
        System.out.println(finalLine);
        writeLogging(finalLine);
    }

    public static void writeLogging(String line){
        try(FileWriter writer = new FileWriter("logging.txt", true))
        {
            writer.write(line);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
