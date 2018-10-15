// Screen.java
// Represents the screen of the ATM

import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Font;
import java.lang.StringBuilder;

public class Screen {

    public JTextArea textArea; //text area to display output 
    private String content = "";
    private static final int lineLength = 89;

    public Screen() {
        textArea = new JTextArea(20, 69);  // declaration of textArea for displaying output
        textArea.setEditable(false);    // set textArea not editable
        Font font = new Font("Monospaced", Font.BOLD, 12);
        textArea.setFont(font);
    }

    public void clearMessage() {
        content = "";
        textArea.setText(content);
    }

    public void displayInput(String input) {
        textArea.setText(content + input);
    }
    
    public void displayPassword(String input){
        String stars = "";
        for(int i=0; i<input.length() ;i++)
            stars += "*";
        displayInput(stars);
    }

    public void clearInput() {
        textArea.setText(content);
    }

    public void enterInput(String input) {
        content += input + '\n';
        textArea.setText(content);
    }

    // displays a message without a carriage return
    public void displayMessage(String message) {
        content += message;
        textArea.setText(content);
    } // end method displayMessage

    // display a message with a carriage return
    public void displayMessageLine(String message) {
        content += message + '\n';
        textArea.setText(content);
    } // end method displayMessageLine

    // display a dollar amount
    public void displayDollarAmount(double amount) {

        displayMessage(String.format("$%,.2f", amount));
    } // end method displayDollarAmount 

    public void displayHorizonalLine(){
        StringBuilder msg = new StringBuilder();
        while(msg.length() < Screen.lineLength){
            msg.append("-");
        }
        displayMessageLine(msg.toString());
    }

    public void displayHorizonalLineWithTitle(String title){
        StringBuilder msg = new StringBuilder();
        int halfIndex = (Screen.lineLength - title.length()) / 2;
        
        for(int i=0; i<halfIndex ; i++){
            msg.append("-");
        }
        msg.append(title);
        while(msg.length() < Screen.lineLength ){
            msg.append("-");
        }

        displayMessageLine(msg.toString());
    }
    
    
} // end class Screen



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
