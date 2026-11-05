package com.Pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class LedgerRepository {
    public static ArrayList<Transactions> extractFileData(String fileName) {
        ArrayList<Transactions> transactions = new ArrayList<>();
        ArrayList<ErrorLogger> errorsInFile = new ArrayList<>();
        int currentRow = 1;


        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            bufferedReader.readLine();
            while((line = bufferedReader.readLine()) != null) {
                currentRow++;

                if(line.trim().isEmpty()) {
                    errorsInFile.add(new ErrorLogger("WARNING", LocalDateTime.now(), currentRow, "EMPTY_LINE", "NO_EMPTY_LINE", line));
                    continue;
                }

                String[] parts = line.split("\\|");
                
                if(parts.length != 5) {
                    errorsInFile.add(new ErrorLogger("ERROR", LocalDateTime.now(), currentRow, "INVALID_COLUMN_COUNT", "Expected 5 pipe-separated fields", line));
                    continue;
                }

                if(parts[3].isEmpty()) {
                    errorsInFile.add(new ErrorLogger("ERROR", LocalDateTime.now(), currentRow, "MISSING_VENDOR", "Vendor field cannot be empty", line));
                    continue;
                }

                try {
                    String formatedVendorName = ConsoleUtils.toTitleCase(parts[3]);
                    Transactions extractedData = new Transactions(LocalDate.parse(parts[0]), LocalTime.parse(parts[1]), parts[2], formatedVendorName, Double.parseDouble(parts[4]));
                    transactions.add(extractedData);
                }
                catch (DateTimeParseException e) {
                    errorsInFile.add(new ErrorLogger("ERROR", LocalDateTime.now(), currentRow, "DATE_TIME_PARSE_ERROR", "Expected valid calendar date (yyyy-MM-dd) and time (HH:mm:ss)", line));

                }
                catch (NumberFormatException e) {
                    errorsInFile.add(new ErrorLogger("ERROR", LocalDateTime.now(), currentRow, "AMOUNT_PARSE_ERROR", "Expected valid numeric decimal for amount", line));
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
