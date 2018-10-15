
// Transfer.java
// for transferring fund from one bank account to another bank account
import java.text.DecimalFormat;
import java.math.RoundingMode;

public class Transfer extends Transaction {

    private int toAccountNumber; // the destination account number of transferring the fund
    private double amount; // amount to transfer
    private Keypad keypad; // reference to keypad
    public boolean backToMenu;

    // constant corresponding to menu option to cancel
    private final static int CANCELED = Keypad.CANCEL;

    // Transfer constructor
    public Transfer(int userAccountNumber, Screen atmScreen,
        BankDatabase atmBankDatabase, Keypad atmKeypad) {
        // initialize superclass variables
        super(userAccountNumber, atmScreen, atmBankDatabase);

        // initialize references to keypad
        keypad = atmKeypad;
        backToMenu = false;
    } // end Transfer constructor

    private void displayTitle(){
        Screen screen = getScreen();
        screen.clearMessage();
        screen.displayHorizonalLineWithTitle("Transfer Menu");
    }

    // perform transaction
    public void execute() {
        BankDatabase bankDatabase = getBankDatabase(); // get reference
        Screen screen = getScreen(); // get reference
        boolean transferred = false; // transferring was not completed yet
        double availableBalance; // amount available for transfer
        boolean isConfirm = false;
        // loop until transferring is completed or the user cancels
        do {

            toAccountNumber = promptForToAccountNumber(); // get the destination account number of transferring the fund

            // check whether user entered a transfer amount or canceled
            if (toAccountNumber != CANCELED) {

                amount = promptForTransferAmount(); // get transfer amount from user

                if (amount != CANCELED) {
                    // get available balance of account involved
                    availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());

                    // check whether the user has enough money in the account or inval
                    if (amount <= availableBalance) {
                        // check whether the destination account number is same to the current user account
                        if (toAccountNumber != getAccountNumber()) // not same, no problem and continue
                        {

                            // check whether the destination account number exist
                            if (bankDatabase.checkUserExist(toAccountNumber)) // exist
                            {
                                // ask for user confrim
                                displayTitle();
                                screen.displayMessageLine("");
                                screen.displayMessageLine("");
                                screen.displayMessageLine("The destination of transferring " + toAccountNumber);
                                screen.displayMessageLine("");
                                screen.displayMessageLine("");
                                screen.displayHorizonalLine();
                                screen.displayMessageLine("");
                                screen.displayMessageLine("");
                                screen.displayMessageLine("The amount to transfer is " + amount);
                                screen.displayMessageLine("");
                                screen.displayMessageLine("");
                                screen.displayMessageLine("");
                                screen.displayHorizonalLine();
                                screen.displayMessageLine("");
                                screen.displayMessageLine("");
                                screen.displayMessageLine("                   Comfirm                                                                        Enter again ");
                                screen.displayMessageLine(""); 
                                screen.displayMessageLine("");
                                screen.displayHorizonalLine();
                                int confirm = 0;
                                while (!(confirm == Keypad.L3 || confirm == Keypad.R3)) {
                                    Keypad.enteringOption = true;
                                    confirm = keypad.getInput(); // let user enter the selection
                                    Keypad.enteringOption = false;
                                }
                                if (confirm == Keypad.L3) {
                                    isConfirm = true;
                                } else {
                                    isConfirm = false;
                                }
                                if (isConfirm) {
                                    bankDatabase.debit(getAccountNumber(), amount);
                                    bankDatabase.credit(toAccountNumber, amount);
                                    transferred = true; // transferred
                                }
                            } // end if
                            else // the destination account number do not exist
                            {
                                screen.displayMessageLine(
                                    "\nCannot find the destination account number."
                                    + "\n\nPlease enter a valid destination account number.");
                                ATM.waiting(5);
                            } // end else
                        } // end if
                        else // the destination account number is same to the current user account, ask user to enter again
                        {
                            screen.displayMessageLine(
                                "\nThe destination account number is same to your current logged-in account."
                                + "\n\nPlease enter a valid destination account number.");
                            ATM.waiting(5);
                        } // end else
                    } // end if
                    else // not enough money available in user's account
                    {
                        screen.displayMessageLine(
                            "\nInsufficient funds in your account."
                            + "\n\nPlease choose a smaller amount.");
                        ATM.waiting(5);
                    } // end else
                } // end if
                else // user canceled instead of entering amount
                {
                    screen.displayMessageLine("\nCanceling transaction...");
                    ATM.waiting(3);
                    backToMenu = true;
                    return;
                } // end else

            } // end if 
            else // user canceled instead of entering the destination account number
            {
                screen.displayMessageLine("\nCanceling transaction...");
                ATM.waiting(3);
                backToMenu = true;
                return;
            } // end else
        } while (!transferred);
        
        int option = 0;
        displayTitle();
        screen.displayMessageLine("");
        screen.displayMessageLine("");
        screen.displayMessageLine("Do you need to go back to Main Menu?");
        screen.displayMessageLine("");
        screen.displayMessageLine("");
        screen.displayHorizonalLine();
        screen.displayMessageLine("");
        screen.displayMessageLine("");
        screen.displayMessageLine("                   YES                                                                                   NO      ");
        screen.displayMessageLine("");
        screen.displayMessageLine("");
        screen.displayMessageLine("");
        screen.displayHorizonalLine();

        while (!(option == Keypad.L2 || option == Keypad.R2)) {
            Keypad.enteringOption = true;
            option = keypad.getInput(); // let user enter the selection
            Keypad.enteringOption = false;
        }
        if (option == Keypad.L2) {
            backToMenu = true;
        } else {
            backToMenu = false;
        }
        
        displayTitle();
        screen.displayMessageLine("");
        screen.displayMessageLine("Transferring completed.\n");
        screen.displayDollarAmount(amount);
        screen.displayMessage(" have been transferred from your account \nto the account: ");
        screen.displayMessage(Integer.toString(toAccountNumber));
        screen.displayMessageLine(".");
        screen.displayMessageLine("");

        if (!backToMenu) {
            screen.displayMessageLine("Please take your ATM CARD now.");
        } else {
            screen.displayMessageLine("Now go back to Main Menu ...");
        }
        ATM.waiting(6);
        
    } // end method execute

    // prompt user to enter a transfer amount in cents 
    private double promptForTransferAmount() {
        Screen screen = getScreen(); // get reference to screen
        double input =0;
        while(input == 0.0){
            // display the prompt
            displayTitle();
            screen.displayMessageLine("");
            screen.displayMessageLine("Please enter a transfer amount in "
            + "DOLLARS correct to 2 decimal places: ");
            input= keypad.getDoubleInput(); // receive input of transfer amount
        }

        // check whether the user canceled or entered a valid amount
        if ((int) input == CANCELED) { // canceled is negative, so will not collide with input value
            return CANCELED;
        } else {
            // round the number to 2 decimal places
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.FLOOR);
            input = Double.valueOf(df.format(input));
            return input; // return dollar amount 
        } // end else
    } // end method promptForTransferAmount

    private int promptForToAccountNumber() {
        Screen screen = getScreen(); // get reference to screen

        // display the prompt
        displayTitle();
        screen.displayMessageLine("");
        screen.displayMessageLine("Please enter the destination of transferring: ");
        int input = keypad.getInput(); // receive input of destination account number

        // check whether the user canceled or entered a valid number
        if (input == CANCELED) {
            return CANCELED;
        } else {
            return input; // return the destination account number
        } // end else
    } // end method promptForToAccountNumber
}
