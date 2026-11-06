package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class LedgerManager {
    private final ArrayList<Transaction> transactions;

    public LedgerManager (ArrayList<Transaction> _transactions){
        this.transactions = _transactions;
        sortTransaction();
    }

    // Enforces the requirement to display newest entries first by chaining
    // date and time comparators in descending (reversed) chronological order.
    private void sortTransaction() {
        this.transactions.sort(
                Comparator.comparing(Transaction::getLocalDate)
                        .thenComparing(Transaction::getLocalTime)
                        .reversed()
        );
    }
    
    public String[] addTransaction(String description, String vendor, String amount) {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formatedCurrentTime = currentTime.format(formatter);

        // Sanitize pipe characters from user input to prevent corrupting the pipe-delimited CSV structure
        description = description.replace("|", "-");

        Transaction newData = new Transaction(currentDate, LocalTime.parse(formatedCurrentTime), description, vendor, Double.parseDouble(amount));
        this.transactions.add(newData);

        return new String[]{currentDate.toString(), formatedCurrentTime,description, vendor, amount};
    }

    private void printDisplayFormating (ArrayList<Transaction> listToPrint) {
        System.out.println();
        if(listToPrint.isEmpty()) {
            System.out.println("No transactions found for the selected search.");
            System.out.println();
            return;
        }
        System.out.println("-".repeat(85));
        System.out.printf("%-11s %-9s %-35s %-17s %5s\n", "Date", "Time", "Description", "Vendor", "Amount");

        for(Transaction item: listToPrint) {
            System.out.printf("%-11tF %-9tT %-35s %-17s $%.2f\n", item.getLocalDate(), item.getLocalTime(), item.getDescription(), item.getVendor(), item.getAmount());
        }

        System.out.println("-".repeat(85));
    }

    public void displayAllTransactions() {
        /*
         * Version 1: Create a duplicate Transaction object for every item.
         *
         * ArrayList<Transaction> allTransactions = new ArrayList<>();
         *
         * for (Transaction item : this.transactions) {
         *     Transaction transaction = new Transaction(item.getLocalDate(), item.getLocalTime(), tem.getDescription(), item.getVendor(), item.getAmount());
         *     allTransactions.add(transaction);
         * }
         *
         * printDisplayFormating(allTransactions);
         */

        /*
         * Version 2: Reuse the existing Transaction objects instead of creating duplicates.
         *
         * ArrayList<Transaction> allTransactions = new ArrayList<>();
         *
         * for (Transaction item : this.transactions) {
         *     allTransactions.add(item);
         * }
         *
         * printDisplayFormating(allTransactions);
         */

        /*
         * Version 3: Add all existing Transaction references to a new list at once.
         *
         * ArrayList<Transaction> allTransactions = new ArrayList<>();
         * allTransactions.addAll(this.transactions);
         *
         * printDisplayFormating(allTransactions);
         */

        // Passes the underlying list reference directly to avoid O(n) memory overhead
        // from cloning Transaction objects or allocating redundant intermediate lists.
        printDisplayFormating(this.transactions);
    }

    public void displayDepositsOnly() {
        ArrayList<Transaction> deposits = new ArrayList<>();

        for(Transaction item: this.transactions) {
            if(item.getAmount() >= 0) {
                deposits.add(item);
            }
        }

        printDisplayFormating(deposits);
    }

    public void displayPaymentsOnly() {
        ArrayList<Transaction> payments = new ArrayList<>();

        for(Transaction item: this.transactions) {
            if(item.getAmount() <= 0) {
                payments.add(item);
            }
        }

        printDisplayFormating(payments);
    }

    private void searchByDate(ArrayList<Transaction> dateTransactions, LocalDate startDate, LocalDate endDate) {
        for(Transaction item: this.transactions) {
            boolean isBeforeOrOnEndDate = item.getLocalDate().equals(endDate) || item.getLocalDate().isBefore(endDate);
            boolean isAfterOrOnStartDate = item.getLocalDate().equals(startDate) || item.getLocalDate().isAfter(startDate);

            if(isBeforeOrOnEndDate && isAfterOrOnStartDate) {
                dateTransactions.add(item);
            }
        }

        printDisplayFormating(dateTransactions);
    }

    /*
     * 1) Month To Date (MTD)
     * Starting from the first day of the current month up to today.
     * Ex: If today is November 14, 2026:
     * Displays transactions from Nov 1st, 2026 to Nov 14th, 2026
     */
    public void displayMonthsToDate() {
        ArrayList<Transaction> dateTransactions = new ArrayList<>();

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);

        searchByDate(dateTransactions, startDate, endDate);
    }

    /*
     * 2) Previous Month
     * The entire calendar month prior to the current month.
     * (From the 1st day to the last dau of the past month.)
     * Ex: If today is November 14, 2026, the previous month is October:
     * Displays transactions from Oct 1st, 2026 to Oct 30th, 2026
     */
    public void displayPreviousMonth() {
        ArrayList<Transaction> dateTransactions = new ArrayList<>();

        LocalDate startDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        searchByDate(dateTransactions, startDate, endDate);
    }

    /*
     * 3) Year To Date (YTD)
     * From the first day of the current year (Jan 1st) up to today's date.
     * Ex: If today is November 14, 2026:
     * Displays transactions from Jan 1st, 2026 to Nov 14th, 2026
     */
    public void displayYearToDate() {
        ArrayList<Transaction> dateTransactions = new ArrayList<>();

        LocalDate startDate = LocalDate.now().withDayOfYear(1);
        LocalDate endDate = LocalDate.now();

        searchByDate(dateTransactions, startDate, endDate);
    }

    /*
     * 4) Previous Year
     * The entire calendar year prior to the current year.
     * (From Jan 1st to Dec 31st of the previous year from the current year.)
     * Ex: If today is November 14, 2026:
     * Displays transactions from Jan 1st, 2025 to Dec 31st, 2025
     */
    public void displayPreviousYear() {
        ArrayList<Transaction> dateTransactions = new ArrayList<>();

        LocalDate startDate = LocalDate.now().minusYears(1).withDayOfYear(1);
        LocalDate endDate = LocalDate.now().minusYears(1).withMonth(12).withDayOfMonth(31);
        searchByDate(dateTransactions, startDate, endDate);
    }

    public void displayByVendor(String vendor) {
        ArrayList<Transaction> vendorTransactions = new ArrayList<>();

        for(Transaction item : this.transactions) {
            if(Objects.equals(item.getVendor(), vendor)) {
                vendorTransactions.add(item);
            }
        }

        printDisplayFormating(vendorTransactions);
    }

    /**
     * Evaluates transactions against optional filter criteria using a progressive elimination
     * pattern: every record starts as a candidate and is excluded only when a non-empty filter fails.
     */
    public void customSearch(LocalDate startDate, LocalDate endDate, String description, String vendor, String amount){
        ArrayList<Transaction> filteredList = new ArrayList<>();

        for(Transaction item: this.transactions) {
            boolean isMatch = true;

            if(startDate != null) {
                if(item.getLocalDate().isBefore(startDate)) isMatch = false;
            }

            if(endDate != null) {
                if(item.getLocalDate().isAfter(endDate)) isMatch = false;
            }

            if(!description.isEmpty()) {
                if(!item.getDescription().equalsIgnoreCase(description)) isMatch = false;
            }

            if(!vendor.isEmpty()) {
                if(!item.getVendor().equalsIgnoreCase(vendor)) isMatch = false;
            }

            if(!amount.isEmpty()) {
                if(item.getAmount() != Double.parseDouble(amount)) isMatch = false;
            }

            if(isMatch) filteredList.add(item);

        }//for loop

        printDisplayFormating(filteredList);
    }
}
