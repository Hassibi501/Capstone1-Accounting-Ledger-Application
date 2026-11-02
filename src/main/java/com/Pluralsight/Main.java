package com.Pluralsight;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fileName = "transactions.csv";
//        Scanner scanner = new Scanner(System.in);
//        while(true) {
//            ConsoleUtils.displayHomeScreenMenu();
//            String userInput = ConsoleUtils.getValidUserInput(scanner, "Enter menu option(D,P,L,X)", "^[DPLX]$", "Error: Please enter valid menu options(D,P,L,X)");
//            switch (userInput) {
//                case "D":
//                    System.out.println("Add deposit");
//                    break;
//                case "P":
//                    System.out.println("Make a payment");
//                    break;
//                case "L":
//                    System.out.println("ledger");
//                    break;
//                case "X":
//                    System.out.println("exiting program");
//                    return;
//                default:
//                    System.out.println("Error: Please enter valid menu options(D,P,L,X)");
//                    break;
//            }//switch
//        }//while

        LedgerRepository.extractFileData(fileName);


    }
}