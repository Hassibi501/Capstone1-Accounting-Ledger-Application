package com.pluralsight;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

public class ConsoleUtils {
    public static void printSectionHeader(String title) {
        System.out.println("\n--- " + title + " ---");
    }

    public static void displayHomeScreenMenu(){
        printMenu("""
                -------------------------------
                D) Add Deposit
                P) Make Payment (Debit)
                L) Ledger
                X) Exit
                -------------------------------
                """);
    }
    private static void displayLedgerMenu(){
        printMenu("""
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
        printMenu("""
                -------------------------------
                1) Month To Date
                2) Previous Month
                3) Year To Date
                4) Previous Year
                5) Search by Vendor
                6) Custom Search
                0) Back
                -------------------------------
                """);
    }

    private static void printMenu(String menu) {
        System.out.println("\n" + menu.strip());
    }

    public static void runLedgerMenu(Scanner scanner, LedgerManager manager){
        String userInput;
        while(true) {
            displayLedgerMenu();
            userInput = getValidUserInput(scanner, "Enter menu option(A,D,P,R,H)", "^[ADPRHadprh]$", "Error: Please enter valid menu options(A,D,P,R,H)").toUpperCase(Locale.ROOT);

            switch (userInput) {
                case "A":
                    printSectionHeader("All Transactions");
                    manager.displayAllTransactions();
                    break;
                case "D":
                    printSectionHeader("Deposits");
                    manager.displayDepositsOnly();
                    break;
                case "P":
                    printSectionHeader("Payments");
                    manager.displayPaymentsOnly();
                    break;
                case "R":
                    printSectionHeader("Reports");
                    runReportsMenu(scanner, manager);
                    break;
                case "H":
                    System.out.println("\nReturning to main menu...\n");
                    return;
            }//switch
        }//while
    }

    private static void runReportsMenu(Scanner scanner, LedgerManager manager) {
        int userInput;
        String vendorRaw;
        String vendor;
        while(true) {
            displayReportsMenu();
            userInput = Integer.parseInt(getValidUserInput(scanner, "Enter menu option (1,2,3,4,5,6,0)", "^[1234560]$", "Error: Please enter valid menu options (1,2,3,4,5,6,0)"));

            switch (userInput) {
                case 1:
                    printSectionHeader("Month To Date");
                    manager.displayMonthsToDate();
                    break;
                case 2:
                    printSectionHeader("Previous Month");
                    manager.displayPreviousMonth();
                    break;
                case 3:
                    printSectionHeader("Year To Date");
                    manager.displayYearToDate();
                    break;
                case 4:
                    printSectionHeader("Previous Year");
                    manager.displayPreviousYear();
                    break;
                case 5:
                    printSectionHeader("Search by Vendor");
                    vendorRaw = getValidUserInput(scanner, "Enter vendor's name:", "^[a-zA-Z0-9 '&.-]+$$", "Error: Please enter valid input");
                    vendor = toTitleCase(vendorRaw);
                    manager.displayByVendor(vendor);
                    break;
                case 6:
                    printSectionHeader("Custom Search");
                    System.out.println("Search by any or more fields. Press Enter to skip a field.");
                    LocalDate startDate = getValidDateInput(scanner, "Enter the Start Date of the transaction you're looking for (yyyy-MM-dd): ", "^\\d{4}-\\d{2}-\\d{2}$", "Error: Please enter proper date format (yyyy-MM-dd):");
                    LocalDate endDate = getValidDateInput(scanner, "Enter the End Date of the transaction you're looking for (yyyy-MM-dd): ", "^\\d{4}-\\d{2}-\\d{2}$", "Error: Please enter proper date format (yyyy-MM-dd):");
                    String description = ConsoleUtils.getValidUserInput(scanner, "Enter transaction's description:", "^.+$", "Error: Please enter valid input", false);
                    vendorRaw = getValidUserInput(scanner, "Enter vendor's name:", "^[a-zA-Z0-9 '&.-]+$$", "Error: Please enter valid input", false);
                    vendor = toTitleCase(vendorRaw);
                    String amountRaw = getValidUserInput(scanner, "Enter the amount of money for transaction:", "^-?\\d+(\\.?\\d{2})?$", "Error: Please enter valid input (12.22 or -12.22).", false);
                    manager.customSearch(startDate, endDate, description, vendor, amountRaw);
                    break;
                case 0:
                    System.out.println("\nReturning to ledger menu...\n");
                    return;
            }//switch
        }//while
    }

    public static String toTitleCase(String userInput) {
        if(userInput.isEmpty()) return "";

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
        return getValidUserInput(scanner, promptMessage, regex, errorMessage, true);
    }

    public static String getValidUserInput(Scanner scanner, String promptMessage, String regex, String errorMessage, boolean inputIsRequired){
        while(true){
            System.out.print(promptMessage + " ");

            String userInput = scanner.nextLine();

            if(userInput.isEmpty()){
                if(inputIsRequired) {
                    System.out.println("\nError: Input was empty. Please enter valid input.");
                    continue;
                }
                else {
                    return userInput;
                }
            }

            if (!userInput.matches(regex)){
                System.out.println("\n" + errorMessage);
                continue;
            }

            return userInput;
        }
    }

    public static void getUserInputForTransactions (Scanner scanner, String fileName, String transactionType, LedgerManager manager,  boolean isNegative){

        String descPromptMsg = "Enter %s description:".formatted(transactionType);
        String venPromptMsg = "Enter %s vendor:".formatted(transactionType);

        String description = getValidUserInput(scanner, descPromptMsg, "^.+$", "Error: Please enter valid input");
        String vendorRaw = getValidUserInput(scanner, venPromptMsg, "^[a-zA-Z0-9 '&.-]+$", "Error: Please enter valid input");
        String vendor = toTitleCase(vendorRaw);
        String amount;

        if(!isNegative) {
            amount = getValidUserInput(scanner, "Enter amount of money for the deposit:", "^\\d+(\\.?\\d{2})?$", "Error: Please enter valid input (12.22).");
        }
        else {
            amount = "-" + getValidUserInput(scanner, "Enter amount of money for the payment:", "^\\d+(\\.?\\d{2})?$", "Error: Please enter valid input (12.22).");
        }

        String[] newDepositEntry = manager.addTransaction(description, vendor, amount);
        LedgerRepository.appendDataToFile(fileName, newDepositEntry);
    }

    public static LocalDate getValidDateInput(Scanner scanner, String promptMessage, String regex, String errorMessage){
        while (true) {
            String dateRaw = getValidUserInput(scanner, promptMessage, regex, errorMessage, false);

            if (dateRaw.isEmpty()) return null;

            try {
                return LocalDate.parse(dateRaw);
            }
            catch (DateTimeParseException e) {
                System.out.println("Error: That date does not exist on the calendar. Please try again.");
            }
        }

    }
}
