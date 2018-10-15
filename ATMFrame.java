// ATMFrame.java

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class ATMFrame extends JFrame {

	private JPanel keyPadJPanel;
	private JPanel screenJPanel;
	private JPanel lBlankButtonJPanel;
	private JPanel rBlankButtonJPanel;
	private Keypad keypad;
	private Screen screen;

	// constructor sets up GUI
	public ATMFrame(Keypad atmKeypad, Screen atmScreen) {
		super("ATM");

		keypad = atmKeypad;
		screen = atmScreen;

		// set all JPanel layout
		keyPadJPanel = new JPanel();
		keyPadJPanel.setLayout(new GridLayout(4, 4));
		screenJPanel = new JPanel();
		screenJPanel.setLayout(new BoxLayout(screenJPanel, BoxLayout.X_AXIS));
		lBlankButtonJPanel = new JPanel();
		lBlankButtonJPanel.setLayout(new GridLayout(3, 1));
		rBlankButtonJPanel = new JPanel();
		rBlankButtonJPanel.setLayout(new GridLayout(3, 1));

		// add component to JPanel

		lBlankButtonJPanel.add(keypad.choiceButton[0]);
		lBlankButtonJPanel.add(keypad.choiceButton[1]);
		lBlankButtonJPanel.add(keypad.choiceButton[2]);

		rBlankButtonJPanel.add(keypad.choiceButton[3]);
		rBlankButtonJPanel.add(keypad.choiceButton[4]);
		rBlankButtonJPanel.add(keypad.choiceButton[5]);


		screenJPanel.add(lBlankButtonJPanel);

		screenJPanel.add(screen.textArea);

		screenJPanel.add(rBlankButtonJPanel);

      // add buttons to keyPadJPanel panel
		// 7, 8, 9, Cancel
		for (int i = 7; i <= 10; i++) {
			keyPadJPanel.add(keypad.keys[i]);
		}

		// 4, 5, 6
		for (int i = 4; i <= 6; i++) {
			keyPadJPanel.add(keypad.keys[i]);
		}

		// Clear
		keyPadJPanel.add(keypad.keys[11]);

		// 1, 2, 3
		for (int i = 1; i <= 3; i++) {
			keyPadJPanel.add(keypad.keys[i]);
		}

		// Enter
		keyPadJPanel.add(keypad.keys[12]);

		// 0
		keyPadJPanel.add(keypad.keys[0]);

		// "", '.' , ""
		for (int i = 15; i >= 13; i--) {
			keyPadJPanel.add(keypad.keys[i]);
		}

		add(screenJPanel, BorderLayout.NORTH);
		add(keyPadJPanel, BorderLayout.CENTER);

		// initialize button handle
		ButtonHandler handler = new ButtonHandler();
		for (int i = 0; i < 16; i++) {
			keypad.keys[i].addActionListener(handler);
		}

		for (int i = 0; i < 6; i++) {
			keypad.choiceButton[i].addActionListener(handler);
		}
		
	} // end ATMFrame constructor
	
	private class ButtonHandler implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			String buttonInput = ((JButton) event.getSource()).getText();
			keypad.pressButton(buttonInput);
		}

	}

} // end class ATMFrame
