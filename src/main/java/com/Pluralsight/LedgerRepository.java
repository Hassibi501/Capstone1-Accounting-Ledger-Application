package com.Pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class LedgerRepository {
    public static ArrayList<Transactions> extractFileData(String fileName) {
        ArrayList<Transactions> transactions = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            bufferedReader.readLine();
            while((line = bufferedReader.readLine()) != null) {

                if(line.trim().isEmpty()) {
                    bufferedReader.readLine();
                    continue;
                }

                String[] parts = line.split("\\|");
                
                if(parts.length != 5) {
                    System.out.printf("Error: [%s] - Out of bound. There can only be 4 pipes (|). Please correct data formating (LocalDate date|LocalTime time|String description|String vendor|Double amount).\n", line);
                    continue;
                }

                if(parts[3].isEmpty()) {
                    System.out.printf("Error: [%s] - Vendor field can not be empty. Please correct vendor name field.\n", line);
                    continue;
                }

                try {
                    String formatedVendorName = ConsoleUtils.toTitleCase(parts[3]);
                    Transactions extractedData = new Transactions(LocalDate.parse(parts[0]), LocalTime.parse(parts[1]), parts[2], formatedVendorName, Double.parseDouble(parts[4]));
                    transactions.add(extractedData);
                }
                catch (DateTimeParseException e) {
                    System.out.printf("Error: [%s] - Invalid date formate, time format, or date does not exist on the calendar. Please correct date (yyyy-MM-dd) formate, time (HH:mm:ss) formate, or correct non-existing date.\n", line);
                }
                catch (NumberFormatException e) {
                    System.out.printf("Error: [%s] - Invalid amount format. Please correct double format.\n", line);
                }
            }//while
        }
        catch (IOException e) {
            e.getStackTrace();
        }

        return transactions;
    }

    public static void appendDataToFile(String fileName, String[] dataToAppend)  {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true))) {

            String input = String.format("%s|%s|%s|%s|%s\n", dataToAppend[0], dataToAppend[1], dataToAppend[2],dataToAppend[3], dataToAppend[4]);

            bufferedWriter.write(input);
            System.out.println("Successfully appended new item to transactions.");

        }
        catch(IOException e) {
            e.getStackTrace();
        }
    }
}
