// BankDatabase.java
// Represents the bank account information database 

public class BankDatabase
{
   private Account accounts[]; // array of Accounts
   private final int FIRST_PART_LENGTH = 5; // the first part of account number 
					//which identifies the user
   private final String SAVING_ACCOUNT_ID = "01"; // the second part of account
						  // which indicate the type of account
   private final String CURRENT_ACCOUNT_ID = "02"; // the second part of account
						  // which indicate the type of account
   
   
   // no-argument BankDatabase constructor initializes accounts
   public BankDatabase()
   {
      accounts = new Account[ 3 ]; // just 3 accounts for testing
      accounts[ 0 ] = new SavingAccount( 1234501, 54321, 16000.0, 18000.0  );
      accounts[ 1 ] = new CurrentAccount( 1234502, 54321, 5000.0, 5000.0 );
      accounts[ 2 ] = new SavingAccount( 9876501, 56789, 300.0, 300.0 );
   } // end no-argument BankDatabase constructor
   
   // retrieve Account object containing specified account number
   private Account getAccount( int accountNumber )
   {
      // loop through accounts searching for matching account number
      for ( Account currentAccount : accounts )
      {
         // return current account if match found
         if ( currentAccount.getAccountNumber() == accountNumber )
            return currentAccount;
      } // end for

      return null; // if no matching account was found, return null
   } // end method getAccount
   

   // determine whether user-specified account number and PIN match
   // those of an account in the database
   public boolean authenticateUser( int userAccountNumber, int userPIN )
   {
      // attempt to retrieve the account with the account number
      Account userAccount = getAccount( userAccountNumber );

      // if account exists, return result of Account method validatePIN
      if ( userAccount != null )
         return userAccount.validatePIN( userPIN );
      else
         return false; // account number not found, so return false
   } // end method authenticateUser

   // return available balance of Account with specified account number
   public double getAvailableBalance( int userAccountNumber )
   {
      return getAccount( userAccountNumber ).getAvailableBalance();
   } // end method getAvailableBalance

   // return total balance of Account with specified account number
   public double getTotalBalance( int userAccountNumber )
   {
      return getAccount( userAccountNumber ).getTotalBalance();
   } // end method getTotalBalance
   
   // credit an amount to Account with specified account number
   public void credit( int userAccountNumber, double amount )
   {
      getAccount( userAccountNumber ).credit( amount );
   } // end method credit

   // debit an amount from of Account with specified account number
   public void debit( int userAccountNumber, double amount )
   {
      getAccount( userAccountNumber ).debit( amount );
   } // end method debit
   
   // check whether user-specified account number exist
   // return true if the account number exist
   public boolean checkUserExist( int userAccountNumber )
   {
	   // attempt to retrieve the account with the account number
      Account userAccount = getAccount( userAccountNumber );

	  // if account number exists, return true, otherwise, return false
      if ( userAccount != null )
       return true;
    else
		  return false; // account number do not exist
   } // end method checkUserExist
   
   
   // return account number if the user have saving account, return -1 if not
   public int getSavingAccountNumber( int userAccountNumber ){
	   // convert the userAccountNumber from current account to saving account
	   String savingAccountNumber = Integer.toString(userAccountNumber).substring(0,FIRST_PART_LENGTH) + SAVING_ACCOUNT_ID;
	   // attempt to retrieve the account with the account number
      Account userAccount =getAccount( Integer.parseInt(savingAccountNumber) );

	  // if account number exists, return true, otherwise, return false
      if ( userAccount != null )
       return Integer.parseInt(savingAccountNumber); // account number exist
    else
		  return -1; // account number do not exist
   } // end method getSavingAccountNumber
   
   // return account number if the user have current account, return -1 if not
   public int getCurrentAccountNumber( int userAccountNumber ){
	   // convert the userAccountNumber from current account to saving account
	   String savingAccountNumber = Integer.toString(userAccountNumber).substring(0,FIRST_PART_LENGTH) + CURRENT_ACCOUNT_ID;
	   // attempt to retrieve the account with the account number
      Account userAccount =getAccount( Integer.parseInt(savingAccountNumber) );
	  // if account number exists, return true, otherwise, return false
      if ( userAccount != null )
       return Integer.parseInt(savingAccountNumber); // account number exist
    else
		  return -1; // account number do not exist
   } // end method getCurrentAccountNumber
   
   // return Interest Rate of saving account
	//return -1 when the user account number given is not a saving account number
   public double getInterestRate( int userAccountNumber ){
	   if(getAccount( userAccountNumber ) instanceof SavingAccount){
			SavingAccount account = (SavingAccount) getAccount( userAccountNumber );
			return account.getInterestRate();
	   } // end if
	   else{
		   System.out.println("System Error: down casting fail at getInterestRate()"); // display error message
		   return -1; // not saving account
	   } // end else
   } // end of getInterestRate
   
   // return Overdrawn Limit of saving account
	//return -1 when the user account number given is not a current account number
   public double getOverdrawnLimit( int userAccountNumber ){
	   if(getAccount( userAccountNumber ) instanceof CurrentAccount){
			CurrentAccount account = (CurrentAccount) getAccount( userAccountNumber );
			return account.getOverdrawnLimit();
	   } // end if
	   else{
		   System.out.println("System Error: down casting fail at getOverdrawnLimit()"); // display error message
		   return -1; // not current account
	   } // end else
   } // end of getOverdrawnLimit
   
} // end class BankDatabase



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