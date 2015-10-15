import java.awt.Container;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame {
	
	private JLabel voterIdLBL, passwordLBL;
	private JTextField voterIdTF, passwordTF;
	private TextArea textArea;
	private JButton submit;
	// Constructor:
	public GUI() {
		voterIdLBL = new JLabel("Enter your voterid below:");
		passwordLBL = new JLabel("Enter your password below:");
		
		voterIdTF = new JTextField (1);
		passwordTF = new JTextField (1);
		
		textArea = new TextArea("", 5, 30);
		
		submit = new JButton("Click to submit voterid and password");
		
	    Container pane = getContentPane();
	    pane.setLayout(new GridLayout(6, 2));
	    
	    pane.add(voterIdLBL);
	    pane.add(voterIdTF);
	    pane.add(passwordLBL);
	    pane.add(passwordTF);
	    pane.add(submit);
	    pane.add(textArea);
	    
		
		setTitle("Virtual Voting Booth");
		setSize(500,400); // default size is 0,0
		setLocation(10,200); // default is 0,0 (top left corner)
	
		// Window Listeners
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			} //windowClosing
		} );
		
	
	} 

	public static void main(String[] args) {
		JFrame votingBoothGUI = new GUI();
		votingBoothGUI.setVisible(true);
	} //main
} //class EmptyFrame1