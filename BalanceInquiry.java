// BalanceInquiry.java
// Represents a balance inquiry ATM transaction

public class BalanceInquiry extends Transaction {

    private Keypad keypad; // reference to keypad
    public boolean backToMenu; // for record whether the user want to go back main menu


    // BalanceInquiry constructor
    public BalanceInquiry(int userAccountNumber, Screen atmScreen, Keypad atmKeypad,
        BankDatabase atmBankDatabase) {
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad = atmKeypad;
        backToMenu = false;
    } // end BalanceInquiry constructor

    
    // performs the transaction
    public void execute() {
        // get references to bank database and screen
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();
        screen.clearMessage();
        screen.displayHorizonalLineWithTitle("Balance Inquiry Menu");
        screen.displayMessageLine("");
        // check whether saving account exist for current user
        // if exist, display the information
        if (bankDatabase.getSavingAccountNumber(getAccountNumber()) != -1) {    // run if the user has saving account
            // get the saving account number
            int savingAccountNumber = bankDatabase.getSavingAccountNumber(getAccountNumber());
            double availableBalance, totalBalance; // declare and initialize variable
            
            // get the available balance for the account involved
            availableBalance = bankDatabase.getAvailableBalance(savingAccountNumber);

            // get the total balance for the account involved
            totalBalance = bankDatabase.getTotalBalance(savingAccountNumber);

            // get interest rate for the account involved
            double interestRate = bankDatabase.getInterestRate(savingAccountNumber);
            screen.displayMessage("- Saving Account ");
            if( getAccountNumber() == savingAccountNumber)
                screen.displayMessage("(Current logged-in account)");
            screen.displayMessage("\n - Available balance: ");
            screen.displayDollarAmount(availableBalance);
            screen.displayMessage("\n - Total balance:     ");
            screen.displayDollarAmount(totalBalance);
            screen.displayMessage("\n - Interest Rate:     " + Double.toString(interestRate * 100) + "%");
            screen.displayMessageLine("");
        } //end if

        // check whether current account exist for current user
        // if exist, display the information
        if (bankDatabase.getCurrentAccountNumber(getAccountNumber()) != -1) {   // run if the user has current account
            // get the current account number
            int currentAccountNumber = bankDatabase.getCurrentAccountNumber(getAccountNumber());
            double availableBalance, totalBalance; // declare and initialize variable
            
            // get the available balance for the account involved
            availableBalance = bankDatabase.getAvailableBalance(currentAccountNumber);

            // get the total balance for the account involved
            totalBalance = bankDatabase.getTotalBalance(currentAccountNumber);

            // get the Overdrawn Limit for the account involved
            double OverdrawnLimit = bankDatabase.getOverdrawnLimit(currentAccountNumber);
            screen.displayMessage("\n- Current Account ");
            if( getAccountNumber() == currentAccountNumber)
                 screen.displayMessage("(Current logged-in account)");
            screen.displayMessage("\n - Available balance: ");
            screen.displayDollarAmount(availableBalance);
            screen.displayMessage("\n - Total balance:     ");
            screen.displayDollarAmount(totalBalance);
            screen.displayMessage("\n - Overdrawn Limit:   ");
            screen.displayDollarAmount(OverdrawnLimit);
            screen.displayMessageLine("");
            screen.displayMessageLine("");
        } // end if
        
        if( !((bankDatabase.getSavingAccountNumber(getAccountNumber()) != -1) && (bankDatabase.getCurrentAccountNumber(getAccountNumber()) != -1) )){
            screen.displayMessageLine("");
            screen.displayMessageLine("");
            screen.displayMessageLine("");
            screen.displayMessageLine("");
            screen.displayMessageLine("");
        }

        int option = 0;
        screen.displayMessageLine("");
        screen.displayHorizonalLine();
        screen.displayMessageLine("");
        screen.displayMessageLine("");
        screen.displayMessageLine("     Go back Main Menu                                                      Exit ATM");

        while (!(option == Keypad.L3 || option == Keypad.R3)) {
            Keypad.enteringOption = true;
            option = keypad.getInput(); // let user enter the selection
            Keypad.enteringOption = false;
        }
        if (option == Keypad.L3) {
            backToMenu = true;
        } else {
            backToMenu = false;
            screen.clearMessage();
            screen.displayMessageLine("\n\nPlease take your ATM  CARD now.");
            ATM.waiting(3);
        }
    } // end method execute
} // end class BalanceInquiry

/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
