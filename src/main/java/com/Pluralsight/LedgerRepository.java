package com.Pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
}
