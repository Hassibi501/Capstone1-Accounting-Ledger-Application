package com.Pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LedgerManager {
    private final ArrayList<Transactions> transactions;

    public LedgerManager (ArrayList<Transactions> _transactions){
        this.transactions = _transactions;
    }
    
    public String[] addTransaction(String description, String vendor, String amount) {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formatedCurrentTime = currentTime.format(formatter);

        Transactions newData = new Transactions(currentDate, LocalTime.parse(formatedCurrentTime), description, vendor, Double.parseDouble(amount));

        this.transactions.add(newData);

        return new String[]{currentDate.toString(), formatedCurrentTime,description, vendor, amount};
    }

    private void printDisplayFormating (ArrayList<Transactions> listToPrint) {
        if(this.transactions.isEmpty()) {
            System.out.println("\nNo Transactions have been made");
            return;
        }
        System.out.println("-".repeat(85));
        System.out.printf("%-11s %-9s %-35s %-17s %5s\n",
                "Date", "Time", "Description", "Vendor", "Amount");
        for(Transactions items: listToPrint) {
            System.out.printf("%-11tF %-9tT %-35s %-17s $%.2f\n",
                    items.getLocalDate(), items.getLocalTime(), items.getDescription(), items.getVendor(), items.getAmount());
        }
        System.out.println("-".repeat(85));
    }

    public void displayAllTransactions() {
       ArrayList<Transactions> allTransactions = new ArrayList<>();

        for(Transactions items: this.transactions) {
            Transactions transactions = new Transactions(items.getLocalDate(), items.getLocalTime(), items.getDescription(), items.getVendor(), items.getAmount());
            allTransactions.add(transactions);
        }

        printDisplayFormating(allTransactions);
    }

    public void displayDepositsOnly() {
        ArrayList<Transactions> deposits = new ArrayList<>();

        for(Transactions items: this.transactions) {
            if(items.getAmount() >= 0) {
                Transactions transactions = new Transactions(items.getLocalDate(), items.getLocalTime(), items.getDescription(), items.getVendor(), items.getAmount());
                deposits.add(transactions);
            }
        }

        printDisplayFormating(deposits);
    }

    public void displayPaymentsOnly() {
        ArrayList<Transactions> payments = new ArrayList<>();

        for(Transactions items: this.transactions) {
            if(items.getAmount() <= 0) {
                Transactions transactions = new Transactions(items.getLocalDate(), items.getLocalTime(), items.getDescription(), items.getVendor(), items.getAmount());
                payments.add(transactions);
            }
        }

        printDisplayFormating(payments);
    }

    private void searchByDate(ArrayList<Transactions> allTransactions, LocalDate startDate, LocalDate endDate) {
        for(Transactions items: this.transactions) {
            boolean isBeforeOrOnEndDate = items.getLocalDate().equals(endDate) || items.getLocalDate().isBefore(endDate);
            boolean isAfterOrOnStartDate = items.getLocalDate().equals(startDate) || items.getLocalDate().isAfter(startDate);

            if(isBeforeOrOnEndDate && isAfterOrOnStartDate) {
                Transactions transactions = new Transactions(items.getLocalDate(), items.getLocalTime(), items.getDescription(), items.getVendor(), items.getAmount());
                allTransactions.add(transactions);
            }
        }

        printDisplayFormating(allTransactions);
    }

    /*
     * 1) Month To Date (MTD)
     * Starting from the first day of the current month up to today.
     * Ex: If today is November 14, 2026:
     * Displays transactions from Nov 1st, 2026 to Nov 14th, 2026
     */
    public void displayMonthsToDate() {
        ArrayList<Transactions> allTransactions = new ArrayList<>();

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);

        searchByDate(allTransactions, startDate, endDate);
    }

    /*
     * 2) Previous Month
     * The entire calendar month prior to the current month.
     * (From the 1st day to the last dau of the past month.)
     * Ex: If today is November 14, 2026, the previous month is October:
     * Displays transactions from Oct 1st, 2026 to Oct 30th, 2026
     */
    public void displayPreviousMonth() {
        ArrayList<Transactions> allTransactions = new ArrayList<>();

        LocalDate startDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().minusMonths(1).withDayOfMonth(31);

        searchByDate(allTransactions, startDate, endDate);
    }

    /*
     * 3) Year To Date (YTD)
     * From the first day of the current year (Jan 1st) up to today's date.
     * Ex: If today is November 14, 2026:
     * Displays transactions from Jan 1st, 2026 to Nov 14th, 2026
     */
    public void displayYearToDate() {
        ArrayList<Transactions> allTransactions = new ArrayList<>();

        LocalDate startDate = LocalDate.now().withDayOfYear(1);
        LocalDate endDate = LocalDate.now();

        searchByDate(allTransactions, startDate, endDate);
    }

    /*
     * 4) Previous Year
     * The entire calendar year prior to the current year.
     * (From Jan 1st to Dec 31st of the previous year from the current year.)
     * Ex: If today is November 14, 2026:
     * Displays transactions from Jan 1st, 2025 to Dec 31st, 2025
     */
    public void displayPreviousYear() {
        ArrayList<Transactions> allTransactions = new ArrayList<>();

        LocalDate startDate = LocalDate.now().minusYears(1).withDayOfYear(1);
        LocalDate endDate = LocalDate.now().minusYears(1).withMonth(12).withDayOfMonth(31);

        searchByDate(allTransactions, startDate, endDate);
    }



}
