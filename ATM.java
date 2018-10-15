// ATM.java
// Represents an automated teller machine

import javax.swing.JFrame;

public class ATM {

    private boolean userAuthenticated; // whether user is authenticated
    private int currentAccountNumber; // current user's account number
    private Screen screen; // ATM's screen
    private Keypad keypad; // ATM's keypad
    private CashDispenser cashDispenser; // ATM's cash dispenser
    // removed function: deposit- private DepositSlot depositSlot; // ATM's deposit slot
    private BankDatabase bankDatabase; // account information database

    // constants corresponding to main menu options
    private static final int BALANCE_INQUIRY = 1;
    private static final int WITHDRAWAL = 2;
    // removed function: deposit- private static final int DEPOSIT = 3;
    private static final int TRANSFER = 3;
    private static final int EXIT = 4;

    // no-argument ATM constructor initializes instance variables
    public ATM() {
        userAuthenticated = false; // user is not authenticated to start
        currentAccountNumber = 0; // no current account number to start
        screen = new Screen(); // create screen
        keypad = new Keypad(screen); // create keypad 
        cashDispenser = new CashDispenser(); // create cash dispenser
        // removed function: deposit- depositSlot = new DepositSlot(); // create deposit slot
        bankDatabase = new BankDatabase(); // create acct info database

        ATMFrame atmFrame = new ATMFrame(keypad, screen);
        atmFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        atmFrame.setSize(735, 600); // set frame size
        atmFrame.setVisible(true); // display frame
        atmFrame.setResizable(false);
    } // end no-argument ATM constructor

    // start ATM 
    public void run() {
        // welcome and authenticate user; perform transactions
        while (true) {
            // loop while user is not yet authenticated
            while (!userAuthenticated) {
               screen.clearMessage();
                screen.displayMessageLine("\nWelcome!");
                waiting(1);
                authenticateUser(); // authenticate user
            } // end while

            performTransactions(); // user is now authenticated 
            userAuthenticated = false; // reset before next ATM session
            currentAccountNumber = 0; // reset before next ATM session 
            screen.displayMessageLine("\nThank you! Goodbye!");
            waiting(3);
        } // end while   
    } // end method run

    // attempts to authenticate user against database
    private void authenticateUser() {
        // initialize
        int accountNumber = -1, pin = -1;

        screen.displayMessage("\nPlease enter your account number: ");

        while(accountNumber < 0){  // < 0 means non-number input
            accountNumber = keypad.getInput(); // input account number
            if (accountNumber == Keypad.CANCEL)
                return; // user cancel
        }

        screen.displayMessage("\nEnter your PIN: "); // prompt for PIN
        
        while(pin < 0){  // < 0 means non-number input
            pin = keypad.getPassword(); // input PIN
            if (pin == Keypad.CANCEL)
                return; // user cancel
        }

        // set userAuthenticated to boolean value returned by database
        userAuthenticated
            = bankDatabase.authenticateUser(accountNumber, pin);

        // check whether authentication succeeded
        if (userAuthenticated) {
            currentAccountNumber = accountNumber; // save user's account #
        } // end if
        else {
            screen.displayMessageLine(
                "\n\nInvalid account number or PIN.\n\n Please try again.");
            waiting(2);
        }
    } // end method authenticateUser

    // display the main menu and perform transactions
    private void performTransactions() {
        // local variable to store transaction currently being processed
        Transaction currentTransaction = null;

        boolean userExited = false; // user has not chosen to exit

        // loop while user has not chosen option to exit system
        while (!userExited) {
            // show main menu and get user selection
            int mainMenuSelection = displayMainMenu();

            // decide how to proceed based on user's menu selection
            switch (mainMenuSelection) {
                // user chose to perform one of three transaction types
                case BALANCE_INQUIRY:
                case WITHDRAWAL:
                // removed function: deposit- case DEPOSIT:
                case TRANSFER:
                    // initialize as new object of chosen type
                    currentTransaction
                        = createTransaction(mainMenuSelection);

                    currentTransaction.execute(); // execute transaction
                    if (mainMenuSelection == WITHDRAWAL) {
                        if (currentTransaction instanceof Withdrawal) { // check whether the transaction is Withdrawal
                            Withdrawal theTransaction = (Withdrawal) currentTransaction;
                            if (!theTransaction.backToMenu) // check whether the user request to back to main menu
                            {
                                userExited = true;
                            }
                        }
                    } else if (mainMenuSelection == TRANSFER) {
                        if (currentTransaction instanceof Transfer) { // check whether the transaction is Withdrawal
                            Transfer theTransaction = (Transfer) currentTransaction;
                            if (!theTransaction.backToMenu) // check whether the user request to back to main menu
                            {
                                userExited = true;
                            }
                        }
                    } else {
                        if (currentTransaction instanceof BalanceInquiry) { // check whether the transaction is Withdrawal
                            BalanceInquiry theTransaction = (BalanceInquiry) currentTransaction;
                            if (!theTransaction.backToMenu) // check whether the user request to back to main menu
                            {
                                userExited = true;
                            }
                        }
                    }
                    break;
                case EXIT: // user chose to terminate session
                    screen.clearMessage();
                    screen.displayMessageLine("\n\nPlease take your ATM  CARD now.");
                    ATM.waiting(2);
                    userExited = true; // this ATM session should end
                    break;
                default: 
                    break;
            } // end switch
        } // end while
    } // end method performTransactions

    // display the main menu and return an input selection
    private int displayMainMenu() {
        screen.clearMessage();
        screen.displayHorizonalLineWithTitle("Main Menu");
        screen.displayMessageLine("");
        screen.displayMessageLine("");
        screen.displayMessageLine("         View my balance                                                  Withdraw cash");
        screen.displayMessageLine("");
        screen.displayMessageLine("");
        screen.displayHorizonalLine();
        screen.displayMessageLine("");
        screen.displayMessageLine("");
        screen.displayMessageLine("");
        screen.displayMessageLine("         Transfer fund                                                             Exit");
        screen.displayMessageLine("");
        screen.displayMessageLine("");
        screen.displayHorizonalLine();
        Keypad.enteringOption = true;
        int ans = keypad.getInput();
        Keypad.enteringOption = false;
        int option = 0;
        switch(ans){
            case Keypad.L1:
                option = BALANCE_INQUIRY;
                break;
            case Keypad.R1:
                option = WITHDRAWAL;
                break;
            case Keypad.L2:
                option = TRANSFER;
                break;
            case Keypad.R2:
                option = EXIT;
                break;
            default:
               option = 0;
        }
        return option;  // return user's selection
    } // end method displayMainMenu

    // return object of specified Transaction subclass
    private Transaction createTransaction(int type) {
        Transaction temp = null; // temporary Transaction variable

        // determine which type of Transaction to create     
        switch (type) {
            case BALANCE_INQUIRY: // create new BalanceInquiry transaction
                temp = new BalanceInquiry(
                    currentAccountNumber, screen, keypad, bankDatabase);
                break;
            case WITHDRAWAL: // create new Withdrawal transaction
                temp = new Withdrawal(currentAccountNumber, screen,
                    bankDatabase, keypad, cashDispenser);
                break;
            /* removed function: deposit- case DEPOSIT: // create new Deposit transaction
             temp = new Deposit( currentAccountNumber, screen, 
             bankDatabase, keypad, depositSlot );
             break; -*/
            case TRANSFER: // create new Transfer transaction
                temp = new Transfer(currentAccountNumber, screen,
                    bankDatabase, keypad);
                break;
        } // end switch

        return temp; // return the newly created object
    } // end method createTransaction

    public static void waiting(int second) { // simulate the waiting time of processing
        long start = System.currentTimeMillis() / 1000;
        // wait for two seconds
        while ((System.currentTimeMillis() / 1000 - start) < second) {
        }
    } // end of method proceesing

} // end class ATM

/**
 * ************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and * Pearson Education,
 * Inc. All Rights Reserved. * * DISCLAIMER: The authors and publisher of this
 * book have used their * best efforts in preparing the book. These efforts
 * include the * development, research, and testing of the theories and programs
 * * to determine their effectiveness. The authors and publisher make * no
 * warranty of any kind, expressed or implied, with regard to these * programs
 * or to the documentation contained in these books. The authors * and publisher
 * shall not be liable in any event for incidental or * consequential damages in
 * connection with, or arising out of, the * furnishing, performance, or use of
 * these programs. *
 * ***********************************************************************
 */
