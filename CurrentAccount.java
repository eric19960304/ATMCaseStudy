// CurrentAccount.java
// Represents a bank account with the special type "current account"

public class CurrentAccount extends Account
{
	private double OverdrawnLimit; // overdrawn limit
	
	CurrentAccount( int theAccountNumber, int thePIN, 
      double theAvailableBalance, double theTotalBalance)
	{
		super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
		OverdrawnLimit = 10000.00; // default value is HK$10,000
		
	}
	
	public double getOverdrawnLimit()
	{
		return OverdrawnLimit;
	} // end method getOverdrawnLimit
	
	// allow other functions to set a new a value to the interest rate 
	// when the value given is not valid this function will return false, otherwise, return true
	public boolean setOverdrawnLimit(double theOverdrawnLimit)
	{
		boolean isValid = true; // do checking here (always returns true, 
								// because this is only a software simulation the real situation)
		
		// valid case
		if(isValid)
		{
			OverdrawnLimit = theOverdrawnLimit;
			return isValid;
		}
		
		// invalid case
		return isValid;
	} // end method setOverdrawnLimit
}
