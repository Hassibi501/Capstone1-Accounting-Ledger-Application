# Capstone 1: Accounting Ledger Application

A straightforward command-line application built in Java to help track and manage financial transactions. The app allows you to record deposits, log payments, and generate detailed reports based on date ranges and vendors. All data is saved and loaded from a local CSV file.

## Features

*   **Record Transactions:** Easily add new deposits or debit payments.
*   **Ledger Reports:** View all transactions or filter by specific criteria (Month-to-Date, Year-to-Date, Previous Year, etc.).
*   **Custom Searches:** Search your transaction history by specific dates, vendors, or amounts.
*   **Data Validation:** Safely reads from the CSV file and logs any corrupted data rows into a dedicated JSON error log without crashing the app.

## Screenshots

**Main Menu**
![](/var/mnt/SharedData/Projects/Java Projects/Pluralsight/3-Capstones/Capstone-1-Accounting-Ledger-Application/Read-Me-Images/homescreen.png)

**Viewing the Ledger**
![](/var/mnt/SharedData/Projects/Java Projects/Pluralsight/3-Capstones/Capstone-1-Accounting-Ledger-Application/Read-Me-Images/ledgert.png)

![](/var/mnt/SharedData/Projects/Java Projects/Pluralsight/3-Capstones/Capstone-1-Accounting-Ledger-Application/Read-Me-Images/ledger2..png)

**Reports**
![](/var/mnt/SharedData/Projects/Java Projects/Pluralsight/3-Capstones/Capstone-1-Accounting-Ledger-Application/Read-Me-Images/reports.png)

**Custom Search**
![](/var/mnt/SharedData/Projects/Java Projects/Pluralsight/3-Capstones/Capstone-1-Accounting-Ledger-Application/Read-Me-Images/customsearch.png)

**Error Logging**
![](/var/mnt/SharedData/Projects/Java Projects/Pluralsight/3-Capstones/Capstone-1-Accounting-Ledger-Application/Read-Me-Images/errorloggingpng.png)

## How to Run

### Prerequisites
* **Java Development Kit (JDK):** Make sure you have Java installed.
* **Maven:** This project uses Maven to handle dependencies (like Gson for error logging).

### Option 1: Running in IntelliJ IDEA (Recommended)
This project is already set up to run easily inside IntelliJ IDEA with two different configurations.

1. Open the project folder in IntelliJ IDEA.
2. Allow IntelliJ to sync the Maven `pom.xml` file to automatically download the Gson dependency.
3. In the top right corner of the IDE, locate the **Run/Debug Configurations** dropdown next to the Play button. You can choose between two setups:

* **Default Configuration (Main):**
  * Just select `Main` and hit the Play button.
  * This runs the program normally and defaults to reading/writing from `transactions.csv`.

* **Test Configuration (Custom Argument):**
  * Select your `test configuration` (or edit the configuration and add `test-transaction.csv` into the **Program arguments** box).
  * Hit the Play button.
  * This runs the program using the custom `test-transaction.csv` file instead of the default ledger, which is great for testing without messing up your main data.



### Option 2: Running from the Terminal

If you prefer to use the command line, you can run the project using Maven.

1. Open your terminal and navigate to the root directory of the project.
2. Compile the project and download dependencies by running:
```bash
mvn clean compile
```


3. **To run with the default file (`transactions.csv`):**
```bash
mvn exec:java -Dexec.mainClass="com.pluralsight.Main"
```

4. **To run with a custom file (like your test configuration):**
   You can pass the filename as an argument like this:
```bash
mvn exec:java -Dexec.mainClass="com.pluralsight.Main" -Dexec.args="test-transaction.csv"
```