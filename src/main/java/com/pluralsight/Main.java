package com.pluralsight;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fileName;
        if (args.length > 0) {
            fileName = args[0];
        }
        else{
            fileName = "transactions.csv";
        }

        ArrayList<Transaction> dataFromFile = LedgerRepository.extractFileData(fileName);
        LedgerManager manager = new LedgerManager(dataFromFile);

        Scanner scanner = new Scanner(System.in);
        while(true) {
            ConsoleUtils.displayHomeScreenMenu();
            String userInput = ConsoleUtils.getValidUserInput(scanner, "Enter menu option (D,P,L,X)", "^[DPLXdplx]$", "Error: Please enter valid menu options (D,P,L,X)").toUpperCase(Locale.ROOT);
            switch (userInput) {
                case "D":
                    ConsoleUtils.printSectionHeader("Add Deposit");
                    ConsoleUtils.getUserInputForTransactions(scanner, fileName, "deposit",  manager, false);
                    break;
                case "P":
                    ConsoleUtils.printSectionHeader("Make a Payment");
                    ConsoleUtils.getUserInputForTransactions(scanner, fileName, "payment",  manager, true);
                    break;
                case "L":
                    ConsoleUtils.printSectionHeader("Ledger");
                    ConsoleUtils.runLedgerMenu(scanner, manager);
                    break;
                case "X":
                    System.out.println("\nExiting program.\n");
                    return;
            }//switch
        }//while
    }
}