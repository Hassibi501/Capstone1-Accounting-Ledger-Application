package com.Pluralsight;

import java.util.Locale;
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

    public static void runLedgerMenu(Scanner scanner, LedgerManager manager){
        String userInput;
        while(true) {
            displayLedgerMenu();
            userInput = getValidUserInput(scanner, "Enter menu option(A,D,P,R,H)", "^[ADPRH]$", "Error: Please enter valid menu options(A,D,P,R,H)");

            switch (userInput) {
                case "A":
                    System.out.println("display all entries");
                    manager.displayAllTransactions();
                    System.out.println();
                    break;
                case "D":
                    System.out.println("display deposits");
                    manager.displayDepositsOnly();
                    System.out.println();
                    break;
                case "P":
                    System.out.println("display payments");
                    manager.displayPaymentsOnly();
                    System.out.println();
                    break;
                case "R":
                    System.out.println("display report");
                    runReportsMenu(scanner, manager);
                    break;
                case "H":
                    System.out.println("going back to main menu...");
                    return;
            }//switch
        }//while
    }

    private static void runReportsMenu(Scanner scanner, LedgerManager manager) {
        int userInput;
        while(true) {
            displayReportsMenu();
            userInput = Integer.parseInt(getValidUserInput(scanner, "Enter menu option(1,2,3,4,5,0)", "^[123450]$", "Error: Please enter valid menu options(1,2,3,4,5,0)"));

            switch (userInput) {
                case 1:
                    System.out.println("Month To Date");
                    manager.displayMonthsToDate();
                    System.out.println();
                    break;
                case 2:

                    System.out.println("Previous Month");
                    manager.displayPreviousMonth();
                    System.out.println();
                    break;
                case 3:
                    System.out.println("Year To Date");
                    manager.displayYearToDate();
                    System.out.println();
                    break;
                case 4:
                    System.out.println("Previous  year");
                    manager.displayPreviousYear();
                    System.out.println();
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

    public static String toTitleCase(String userInput) {
        String[] words = userInput.trim().split("\\s+");
        StringBuilder titleCase = new StringBuilder();

        // takes each word and capitalizes the first letter
        for(String word: words) {
            if(!word.isEmpty()) {
                titleCase.append(Character.toUpperCase(word.charAt(0)))  // gets the first letter of a word and capitalize it
                        .append(word.substring(1).toLowerCase(Locale.ROOT)) // gets every letter after the first one (.substring(1)) and lower case them.
                        .append(" "); // adds a space for the next word to process in the for loop
            }
        }
        return titleCase.toString().trim();
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
