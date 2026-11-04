package com.Pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class LedgerRepository {
    public static ArrayList<Transactions> extractFileData(String fileName) {
        ArrayList<Transactions> transactions = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            bufferedReader.readLine();
            while((line = bufferedReader.readLine()) != null) {

                String[] parts = line.split("\\|");

                Transactions extractedData = new Transactions(LocalDate.parse(parts[0]), LocalTime.parse(parts[1]), parts[2], parts[3], Double.parseDouble(parts[4]));

                transactions.add(extractedData);
            }
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
        catch(IOException e ) {
            e.getStackTrace();
        }
    }
}
