package com.Pluralsight;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BatchLogWriter {
    public static void loggingErrorsToJson(ArrayList<ErrorLogEntry> errorList, String fileName) {
        LocalDateTime currentDateTimeRaw = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss");
        String currentDateTime = currentDateTimeRaw.format(formatter);

        String jsonFileName = "logs/error-log-" + currentDateTime + ".json";
        File folder = new File("logs");

        if(!folder.exists()){
            folder.mkdir();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(jsonFileName))) {
            bufferedWriter.write(gson.toJson(errorList));
        }
        catch (IOException e) {
            System.out.println("Failed to write log file.");
            e.printStackTrace();
        }
    }
}
