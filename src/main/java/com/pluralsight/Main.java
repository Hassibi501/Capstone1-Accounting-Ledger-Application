package com.pluralsight;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fileName = "test-transactions.csv";
        ArrayList<Transaction> dataFromFile = LedgerRepository.extractFileData(fileName);
        LedgerManager manager = new LedgerManager(dataFromFile);

        Scanner scanner = new Scanner(System.in);
        while(true) {
            ConsoleUtils.displayHomeScreenMenu();
            String userInput = ConsoleUtils.getValidUserInput(scanner, "Enter menu option (D,P,L,X)", "^[DPLXdplx]$", "Error: Please enter valid menu options (D,P,L,X)").toUpperCase(Locale.ROOT);
            switch (userInput) {
                case "D":
                    System.out.println("Add deposit");
                    ConsoleUtils.getUserInputForTransactions(scanner, fileName, "deposit",  manager, false);
                    System.out.println();
                    break;
                case "P":
                    System.out.println("Make a payment");
                    ConsoleUtils.getUserInputForTransactions(scanner, fileName, "payment",  manager, true);
                    System.out.println();
                    break;
                case "L":
                    System.out.println("ledger");
                    ConsoleUtils.runLedgerMenu(scanner, manager);
                    System.out.println();
                    break;
                case "X":
                    System.out.println("exiting program");
                    return;
            }//switch
        }//while
    }
}