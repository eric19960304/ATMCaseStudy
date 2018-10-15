// Withdrawal.java
// Represents a withdrawal ATM transaction

public class Withdrawal extends Transaction {

    private int amount; // amount to withdraw
    private Keypad keypad; // reference to keypad
    private CashDispenser cashDispenser; // reference to cash dispenser
    public boolean backToMenu; // for record whether the user want to go back main menu

    // constant corresponding to menu option to cancel
    // Withdrawal constructor
    public Withdrawal(int userAccountNumber, Screen atmScreen,
        BankDatabase atmBankDatabase, Keypad atmKeypad,
        CashDispenser atmCashDispenser) {
        // initialize superclass variables
        super(userAccountNumber, atmScreen, atmBankDatabase);

        // initialize references to keypad and cash dispenser
        keypad = atmKeypad;
        cashDispenser = atmCashDispenser;
        backToMenu = false;
    } // end Withdrawal constructor

    private void displayTitle(){
        Screen screen = getScreen();
        screen.clearMessage();
        screen.displayHorizonalLineWithTitle("Withdrawal Menu");
    }

    // perform transaction
    public void execute() {
        boolean cashDispensed = false; // cash was not dispensed yet
        double availableBalance; // amount available for withdrawal

        // get references to bank database and screen
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();

        // loop until cash is dispensed or the user cancels
        do {
            // obtain a chosen withdrawal amount from the user 
            amount = displayMenuOfAmounts();

            // check whether user chose a withdrawal amount or canceled
            if (amount != Keypad.R3) {
                // get available balance of account involved
                availableBalance
                    = bankDatabase.getAvailableBalance(getAccountNumber());

                // check whether the user has enough money in the account 
                if (amount <= availableBalance) {
                    // check whether the cash dispenser has enough money
                    if (cashDispenser.isSufficientCashAvailable(amount)) {
                        // update the account involved to reflect withdrawal
                        bankDatabase.debit(getAccountNumber(), amount);

                        cashDispenser.dispenseCash(amount); // dispense cash
                        cashDispensed = true; // cash was dispensed
                        // instruct user to take card

                        int option = 0;
                        displayTitle();
                        screen.displayMessageLine("");
                        screen.displayMessageLine("");
                        screen.displayMessageLine("Do you need to go back to menu?");
                        screen.displayMessageLine("");
                        screen.displayMessageLine("");
                        screen.displayHorizonalLine();
                        screen.displayMessageLine("");
                        screen.displayMessageLine("");
                        screen.displayMessageLine("               YES                                                                         NO");
                        screen.displayMessageLine("");
                        screen.displayMessageLine("");
                        screen.displayMessageLine("");
                        screen.displayHorizonalLine();

                        while (!(option == Keypad.L2 || option == Keypad.R2)) {
                            Keypad.enteringOption = true;
                            option = (int) keypad.getInput(); // let user enter the selection
                            Keypad.enteringOption = false;
                        }
                        
                        backToMenu = (option == Keypad.L2);

                        if (!backToMenu) {
                            displayTitle();
                            screen.displayMessageLine("");
                            screen.displayMessageLine("Withdraw successfully! \n\nPlease take your ATM CARD now ...");
                            ATM.waiting(4);

                            // instruct user to take cash
                            screen.displayMessageLine(
                                "\nPlease take your CASH now.");
                        } else{
                            displayTitle();
                            screen.displayMessageLine("");
                            screen.displayMessageLine("Withdraw successfully! \n\nPlease take your CASH now ...");
                            ATM.waiting(4);
                            screen.displayMessageLine("\nNow go back to Main Menu ...");
                            ATM.waiting(3);
                            
                            
                        }
                    } // end if
                    else // cash dispenser does not have enough cash
                    {
                        screen.displayMessageLine(
                            "\nInsufficient cash available in the ATM."
                            + "\n\nPlease choose a smaller amount.");
                        ATM.waiting(4);
                    }
                } // end if
                else // not enough money available in user's account
                {
                    screen.displayMessageLine(
                        "\nInsufficient funds in your account."
                        + "\n\nPlease choose a smaller amount.");
                    ATM.waiting(4);
                } // end else
            } // end if
            else // user chose cancel menu option 
            {
                screen.displayMessageLine("\nCanceling transaction...");
                return; // return to main menu because user canceled
            } // end else
        } while (!cashDispensed);

    } // end method execute

    // display a menu of withdrawal amounts and the option to cancel;
    // return the chosen amount or Keypad.R3 if the user chooses to cancel
    private int displayMenuOfAmounts() {
        int userChoice = Keypad.CANCEL; // local variable to store return value
        int input, input2 = 0; // input for selection, input2 for amount of money to withdraw

        Screen screen = getScreen(); // get screen reference

        // array of amounts to correspond to menu numbers
        int amounts[] = {0,100, 500, 1000, 1500};

        // loop while no valid choice has been made
        while (userChoice == Keypad.CANCEL) {
            // display the menu
            displayTitle();
            screen.displayMessageLine("");
            screen.displayMessageLine("");
            screen.displayMessageLine("         Custom the Amount to Withdraw                           Withdraw $100 only");
            screen.displayMessageLine("");
            screen.displayMessageLine("");
            screen.displayHorizonalLine();
            screen.displayMessageLine("");
            screen.displayMessageLine("");
            screen.displayMessageLine("");
            screen.displayMessageLine("         Withdraw $500 only                                     Withdraw $1000 only");
            screen.displayMessageLine("");
            screen.displayMessageLine("");
            screen.displayHorizonalLine();
            screen.displayMessageLine("");
            screen.displayMessageLine("");
            screen.displayMessageLine("         Withdraw $1500 only                                      Back to main menu");
            Keypad.enteringOption = true;
            input = (int) keypad.getInput(); // let user enter the selection
            Keypad.enteringOption = false;

            // determine how to proceed based on the input value
            switch (input) {
                case Keypad.L1:
                    do {
                        do{
                            displayTitle();
                            screen.displayMessage("\nPlease enter the amount you want to withdraw in "
                                + "DOLLARS: ");

                                input2 = (int) keypad.getInput(); // let user enter the amount to withdraw
                        }while(input2==0);

                        if (input2 == Keypad.CANCEL) {
                            userChoice = Keypad.CANCEL;
                            break;
                        }
                        if (input2 % 100 != 0 || input2 < 0) {
                            screen.displayMessageLine("\n\nOnly $100, $500 and $1000 are available. ");
                            if(input2 < 0)
                                screen.displayMessageLine("\nPlease enter an integer. ");
                            else
                                screen.displayMessageLine("\nPlease enter an input which is divisible by 100. ");
                            ATM.waiting(5);
                            continue;
                        }
                        if (input2 > 6000) {
                            screen.displayMessageLine("\n\nYou can only withdraw at most $6000 each time.");
                            screen.displayMessageLine("\nPlease enter an input which is less than or equal to $6000.");
                            ATM.waiting(5);
                            continue;
                        }
                        userChoice = input2;
                    } while (userChoice == Keypad.CANCEL);
                    break;
                case Keypad.R1:
                    userChoice = amounts[1]; 
                    break;
                case Keypad.L2:
                    userChoice = amounts[2]; 
                    break;
                case Keypad.R2:
                    userChoice = amounts[3]; 
                    break;
                case Keypad.L3:
                    userChoice = amounts[4]; 
                    break;
                case Keypad.R3: // the user chose to cancel
                    userChoice = Keypad.R3; // save user's choice
                    backToMenu = true;
                    break;
                default:
                // do nothing if the user press other button
            } // end switch

            if (userChoice != Keypad.CANCEL && userChoice != Keypad.R3) {

                int confirm = 0;
                displayTitle();
                screen.displayMessageLine("");
                screen.displayMessageLine("");
                screen.displayMessageLine("The amount you want to withdraw is " + userChoice);
                screen.displayMessageLine("");
                screen.displayMessageLine("");
                screen.displayHorizonalLine();
                screen.displayMessageLine("");
                screen.displayMessageLine("");
                screen.displayMessageLine("            Comfirm                                                                                     Enter again ");
                screen.displayMessageLine("");
                screen.displayMessageLine("");
                screen.displayMessageLine("");
                screen.displayHorizonalLine();

                while (!(confirm == Keypad.L2 || confirm == Keypad.R2)) {
                    Keypad.enteringOption = true;
                    confirm = (int) keypad.getInput(); // let user enter the selection
                    Keypad.enteringOption = false;
                }
                if (confirm == Keypad.R2) {
                    userChoice = Keypad.CANCEL;
                }
            }
        } // end while

        return userChoice; // return withdrawal amount or CANCELED
    }
} // end class Withdrawal

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
