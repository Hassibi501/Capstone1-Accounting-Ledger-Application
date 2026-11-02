package com.Pluralsight;

import java.util.Scanner;

public class ConsoleUtils {
    public static void displayHomeScreenMenu(){
        System.out.println("""
                -------------------------------
                D) Add Deposit
                P) Make Payment (Debit)
                L) Ledger
                X) Exit
                -------------------------------
                """);
    }
    public static void displayLedgerMenu(){
        System.out.println("""
                -------------------------------
                A) All Transactions
                D) Deposits
                P) Payments
                R) Reports
                H) Home
                -------------------------------
                """);
    }

    public static void displayReportsMenu(){
        System.out.println("""
                -------------------------------
                1) Month To Date
                2) Previous Month
                3) Year To Date
                4) Previous Year
                5) Search by Vendor
                -------------------------------
                """);
    }

    public void runLedgerMenu(Scanner scanner){
        String userInput;
        while(true) {
            displayLedgerMenu();
            userInput = getValidUserInput(scanner, "Enter menu option(D,P,L,X)", "^[DPLX]$", "Error: Please enter valid menu options(D,P,L,X)");

            switch (userInput) {
                case "A":
            }


        }
    }

    public static String getValidUserInput(Scanner scanner, String promptMessage, String regex, String errorMessage){
        while(true){
            System.out.println(promptMessage);

            String userInput = scanner.nextLine();

            if(userInput.isEmpty()){
                System.out.println("Error: Input was empty. Please enter valid input. ");
                continue;
            }

            if (!userInput.matches(regex)){
                System.out.println(errorMessage);
                continue;
            }

            return userInput;
        }
    }
}
