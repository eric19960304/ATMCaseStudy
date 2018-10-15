// SavingAccount.java
// Represents a bank account with the special type "saving account"

public class SavingAccount extends Account 
{
	private double interestRate; // interest rate per annum
	
	SavingAccount( int theAccountNumber, int thePIN, 
      double theAvailableBalance, double theTotalBalance )
	{
		super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
		interestRate = 0.001; // default value is 0.1%
	}
	
	// returns the interest rate
	public double getInterestRate()
	{
		return interestRate;
	} // end method getInterestRate
	
	// allow other functions to set a new a value to the interest rate 
	// when the value given is not valid this function will return false, otherwise, return true
	public boolean setInterestRate(double theInterestRate)
	{
		boolean isValid = true; // do checking here (always returns true, 
								// because this is only a software simulation the real situation)
		
		// valid case
		if(isValid)
		{
			interestRate = theInterestRate;
			return isValid;
		}
		
		// invalid case
		return isValid;
	} // end method setInterestRate
	
}
