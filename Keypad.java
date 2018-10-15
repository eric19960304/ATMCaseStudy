// Keypad.java
// Represents the keypad of the ATM

import java.util.Scanner; // program uses Scanner to obtain user input
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.JOptionPane;


public class Keypad {

    Screen screen;
    public static final int CANCEL = -10;
    public static final int L1 = -1;
    public static final int L2 = -2;
    public static final int L3 = -3;
    public static final int R1 = -4;
    public static final int R2 = -5;
    public static final int R3 = -6;
    
    private volatile int returnValue = 0;
    private volatile double returnDoubleValue = 0.0;
    private volatile boolean entered=false;
    private volatile boolean enteringPassword = false;
    public static volatile boolean enteringOption = false;

    //for GUI
    public JButton choiceButton[];
    public JButton keys[];

    private String input = ""; // reads data from the GUI button
    private String totalInput = ""; // store inputs

    // no-argument constructor initializes the Scanner
    public Keypad(Screen atmScreen) {
        screen = atmScreen;

        // create button for ScreenJPanel
        choiceButton = new JButton[6];
        choiceButton[0] = new JButton("L1");
        choiceButton[1] = new JButton("L2");
        choiceButton[2] = new JButton("L3");
        choiceButton[3] = new JButton("R1");
        choiceButton[4] = new JButton("R2");
        choiceButton[5] = new JButton("R3");

        // create button for keyPadJPanel
        keys = new JButton[16]; // array keys contains 16 JButtons 
        // initialize all digit key buttons
        keys[0] = new JButton("00");
        for (int i = 1; i <= 9; i++) {
            keys[i] = new JButton(String.valueOf(i));
        }
        keys[10] = new JButton("Cancel");
        keys[11] = new JButton("Clear");
        keys[12] = new JButton("Enter");
        keys[13] = new JButton("");
        keys[14] = new JButton(".");
        keys[15] = new JButton("0");

    } // end no-argument Keypad constructor

    // handle the button press situation
    public void pressButton(String buttonInput) {
        if(buttonInput == null)
            return;
        input = buttonInput;
        // case press number or "."
        if (!input.isEmpty()
            && !(input.equals("Enter")
                || input.equals("Clear")
                || input.equals("L1")
                || input.equals("L2")
                || input.equals("L3")
                || input.equals("R1")
                || input.equals("R2")
                || input.equals("R3")
                || input.equals("Cancel"))
            ) 
        {
            // prevent too long enter 
            if(totalInput.length() < 9)
                totalInput += input;
            // when entering password
            if(!enteringPassword){
                if(!enteringOption)
                    if(totalInput.length() < 10)
                        screen.displayInput(totalInput);
            }
            else{
                if(totalInput.length() < 10)
                    screen.displayPassword(totalInput);
            }
        } else{
            // case press Enter
            if(!totalInput.isEmpty() && input.equals("Enter")){
                if(isInteger(totalInput)){
                    returnValue = Integer.valueOf(totalInput);
                }
                else{
                    returnValue = -1;
                }

                returnDoubleValue = Double.valueOf(totalInput);

                if(!(enteringPassword || enteringOption)){
                    screen.enterInput(totalInput);
                }
                else{
                    screen.enterInput("");
                }
                    
                totalInput = "";
                entered = true;
            }
            // case press Clear
            else if(input.equals("Clear")){
                screen.clearInput();
                totalInput = "";
            }
            // case press Cancel
            else if (input.equals("Cancel")) {
                screen.clearInput();
                returnValue = CANCEL;
                returnDoubleValue = (double) CANCEL;
                totalInput = "";
                entered = true;
            }
            // case press L1~3, R1~3 button
            else if( !input.isEmpty()){
                if(input.equals("L1")){
                    returnValue = L1;
                    returnDoubleValue = L1;
                }
                if(input.equals("R1")){
                    returnValue = R1;
                    returnDoubleValue = R1;
                }
                if(input.equals("L2")){
                    returnValue = L2;
                    returnDoubleValue = L2;
                }
                if(input.equals("R2")){
                    returnValue = R2;
                    returnDoubleValue = R2;
                }
                if(input.equals("L3")){
                    returnValue = L3;
                    returnDoubleValue = L3;
                }
                if(input.equals("R3")){
                    returnValue = R3;
                    returnDoubleValue = R3;
                }
                screen.clearInput();
                totalInput = "";
                entered = true;
            }
        }
        
    }

    // return an integer value entered by user 
    public int getInput() {
        while(entered == false){
            try {
                Thread.sleep(300);
            } catch(InterruptedException e) {
            }
        }
        entered = false;
        totalInput = "";
        return returnValue;
    } // end method getInput
    
    // used when enter password
    public int getPassword() {
        enteringPassword = true;
        while(entered == false){
            try {
                Thread.sleep(300);
            } 
            catch(InterruptedException e) { }
        }
        entered = false;
        enteringPassword = false;
        totalInput = "";
        return returnValue;
    } // end method getInput

    public double getDoubleInput() {
        while(entered == false){
            try {
                Thread.sleep(300);
            } catch(InterruptedException e) { }
        }
        entered = false;
        return (double)returnDoubleValue;
    } // end method getDoubleInput

    public boolean isInteger(String s) {
    try { 
        Integer.parseInt(s); 
    } catch(NumberFormatException e) { 
        return false; 
    } catch(NullPointerException e) {
        return false;
    }
    return true;
    }
    
} // end class Keypad  

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
