package com.Pluralsight;

import java.util.ArrayList;

public class LedgerManager {
    private ArrayList<Transactions> transactions;

    public LedgerManager (ArrayList<Transactions> _transactions){
        this.transactions = _transactions;
    }

    public void displayAllTransactions() {
        if(this.transactions.isEmpty()) {
            System.out.println("\nNo Transactions have been made");
            return;
        }
        System.out.println("-".repeat(85));
        System.out.printf("%-11s %-9s %-35s %-17s %5s\n",
                "Date", "Time", "Description", "Vendor", "Amount");
        for(Transactions items: this.transactions) {
            System.out.printf("%-11tF %-9tT %-35s %-17s $%.2f\n",
                    items.getLocalDate(), items.getLocalTime(), items.getDescription(), items.getVendor(), items.getAmount());
        }
        System.out.println("-".repeat(85));
    }


}
