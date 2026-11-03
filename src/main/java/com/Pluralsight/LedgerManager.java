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
            if(items.getAmount() <= 0){
                Transactions transactions = new Transactions(items.getLocalDate(), items.getLocalTime(), items.getDescription(), items.getVendor(), items.getAmount());
                payments.add(transactions);
            }
        }

        printDisplayFormating(payments);
    }



}
