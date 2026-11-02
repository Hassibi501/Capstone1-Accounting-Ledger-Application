package com.Pluralsight;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fileName = "transactions.csv";
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
                    break;
                case "P":
                    System.out.println("Make a payment");
                    break;
                case "L":
                    System.out.println("ledger");
                    ConsoleUtils.runLedgerMenu(scanner);
                    break;
                case "X":
                    System.out.println("exiting program");
                    return;
            }//switch
        }//while



    }
}