package com.Pluralsight;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fileName = "test-transactions.csv";
        ArrayList<Transactions> dataFromFile = LedgerRepository.extractFileData(fileName);
        LedgerManager manager = new LedgerManager(dataFromFile);

        Scanner scanner = new Scanner(System.in);
        while(true) {
            ConsoleUtils.displayHomeScreenMenu();
            String userInput = ConsoleUtils.getValidUserInput(scanner, "Enter menu option(D,P,L,X)", "^[DPLX]$", "Error: Please enter valid menu options(D,P,L,X)");
            switch (userInput) {
                case "D":
                    System.out.println("Add deposit");
                    //TODO: Get user input by calling LedgerManager.
                    String description = ConsoleUtils.getValidUserInput(scanner, "Enter deposit description:", "\\w+", "Error: Please enter valid input");
                    String vendor = ConsoleUtils.getValidUserInput(scanner, "Enter deposit vendor:", "^[A-Z]\\w+$", "Error: Please enter valid input");
                    String amount = ConsoleUtils.getValidUserInput(scanner, "Enter amount of money for the deposit:", "^\\d+(\\.?\\d{2})?$", "Error: Please enter valid input(12.22)");
                    String[] newDepositEntry = manager.addDeposit(description, vendor, amount);
                    LedgerRepository.appendDataToFile(fileName, newDepositEntry);

                    break;
                case "P":
                    System.out.println("Make a payment");
                    break;
                case "L":
                    System.out.println("ledger");
                    ConsoleUtils.runLedgerMenu(scanner, manager);
                    break;
                case "X":
                    System.out.println("exiting program");
                    return;
            }//switch
        }//while


    }
}