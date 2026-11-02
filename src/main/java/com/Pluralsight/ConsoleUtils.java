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
    private static void displayLedgerMenu(){
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

    private static void displayReportsMenu(){
        System.out.println("""
                -------------------------------
                1) Month To Date
                2) Previous Month
                3) Year To Date
                4) Previous Year
                5) Search by Vendor
                0) Back
                -------------------------------
                """);
    }

    public static void runLedgerMenu(Scanner scanner){
        String userInput;
        while(true) {
            displayLedgerMenu();
            userInput = getValidUserInput(scanner, "Enter menu option(A,D,P,R,H)", "^[ADPRH]$", "Error: Please enter valid menu options(A,D,P,R,H)");

            switch (userInput) {
                case "A":
                    System.out.println("display all entries");
                    break;
                case "D":
                    System.out.println("display deposits");
                    break;
                case "P":
                    System.out.println("display payments");
                    break;
                case "R":
                    System.out.println("display report");
                    runReportsMenu(scanner);
                    break;
                case "H":
                    System.out.println("going back to main menu...");
                    return;
            }//switch
        }//while
    }

    private static void runReportsMenu(Scanner scanner) {
        int userInput;
        while(true) {
            displayReportsMenu();
            userInput = Integer.parseInt(getValidUserInput(scanner, "Enter menu option(1,2,3,4,5,0)", "^[123450]$", "Error: Please enter valid menu options(1,2,3,4,5,0)"));

            switch (userInput) {
                case 1:
                    System.out.println("Month To Date");
                    break;
                case 2:
                    System.out.println("Previous Month");
                    break;
                case 3:
                    System.out.println("Year To Date");
                    break;
                case 4:
                    System.out.println("Previous  year");
                    displayReportsMenu();
                    break;
                case 5:
                    System.out.println("search by vendor");
                    break;
                case 0:
                    System.out.println("going back to main menu...");
                    return;
            }//switch
        }//while
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
