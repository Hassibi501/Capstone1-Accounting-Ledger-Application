package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class LedgerRepository {
    public static ArrayList<Transaction> extractFileData(String fileName) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        ArrayList<ErrorLogEntry> errorsInFile = new ArrayList<>();
        String currentDateTime = String.valueOf(LocalDateTime.now());
        int currentRow = 1;


        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            bufferedReader.readLine();
            while((line = bufferedReader.readLine()) != null) {
                currentRow++;

                if(line.trim().isEmpty()) {
                    errorsInFile.add(new ErrorLogEntry(fileName,"WARNING", currentDateTime, currentRow, "EMPTY_LINE", "NO_EMPTY_LINE", line));
                    continue;
                }

                String[] parts = line.split("\\|");
                
                if(parts.length != 5) {
                    errorsInFile.add(new ErrorLogEntry(fileName,"ERROR", currentDateTime, currentRow, "INVALID_COLUMN_COUNT", "Expected 5 pipe-separated fields", line));
                    continue;
                }

                if(parts[3].isEmpty()) {
                    errorsInFile.add(new ErrorLogEntry(fileName, "ERROR", currentDateTime, currentRow, "MISSING_VENDOR", "Vendor field cannot be empty", line));
                    continue;
                }

                try {
                    String formatedVendorName = ConsoleUtils.toTitleCase(parts[3]);
                    Transaction extractedData = new Transaction(LocalDate.parse(parts[0]), LocalTime.parse(parts[1]), parts[2], formatedVendorName, Double.parseDouble(parts[4]));
                    transactions.add(extractedData);
                }
                catch (DateTimeParseException e) {
                    errorsInFile.add(new ErrorLogEntry(fileName, "ERROR", currentDateTime, currentRow, "DATE_TIME_PARSE_ERROR", "Expected valid calendar date (yyyy-MM-dd) and time (HH:mm:ss)", line));

                }
                catch (NumberFormatException e) {
                    errorsInFile.add(new ErrorLogEntry(fileName,"ERROR", currentDateTime, currentRow, "AMOUNT_PARSE_ERROR", "Expected valid numeric decimal for amount", line));
                }

            }//while
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if(!errorsInFile.isEmpty()) {
            BatchLogWriter.loggingErrorsToJson(errorsInFile);
        }
        System.out.printf("""
                File: "%s" loaded.
                %d transactions imported successfully.
                %d records skipped due to data errors (Check logs/ for details).
                """, fileName, transactions.size(), errorsInFile.size());
        return transactions;
    }
    private static boolean fileEndsWithNewLine(String fileName) throws IOException {
        try(RandomAccessFile file = new RandomAccessFile(fileName, "r")) {
            long fileLength = file.length();

            if (fileLength == 0) return false;

            file.seek(fileLength - 1);

            int lastByte = file.read();

            return lastByte == '\n' || lastByte == '\r';
        }
    }
    public static void appendDataToFile(String fileName, String[] dataToAppend)  {
        try {
            File file = new File(fileName);
            boolean fileHasContent = file.length() > 0;
            boolean endsWithNewLine = false;

            if (fileHasContent) {
                endsWithNewLine = fileEndsWithNewLine(fileName);
            }

            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true))) {
                if (fileHasContent && !endsWithNewLine) {
                    bufferedWriter.newLine();
                }

                String input = String.format("%s|%s|%s|%s|%s\n", dataToAppend[0].trim(), dataToAppend[1].trim(), dataToAppend[2].trim(),dataToAppend[3].trim(), dataToAppend[4].trim());

                bufferedWriter.write(input);
                System.out.println("Successfully appended new item to transactions.");

            }
        }

        catch(IOException e) {
            System.out.println("Unable to append to " + fileName);
            e.printStackTrace();
        }
    }
}
