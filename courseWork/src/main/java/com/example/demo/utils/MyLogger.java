package com.example.demo.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Util for logging events
 */
public class MyLogger {

    /**
     * Imform about events by printing the passed message
     * @param line String value of the message for notification
     */
    public static void inform(String line){
        String finalLine = "ИНФОРМАЦИЯ ОТ СЕРВЕРА "+ LocalDate.now() +": "+line+"\n";
        System.out.println(finalLine);
        writeLogging(finalLine);
    }

    /**
     * Writes the notification to log file
     * @param line String value of the notificaiton message
     */
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
