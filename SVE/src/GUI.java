import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame {
	
	private JLabel voterIdLBL, passwordLBL;
	private JTextField voterIdTF, passwordTF;
	private TextArea textArea;
	private JButton submitBTN;
	final JFrame frame = new JFrame();
	final JPanel panel = new JPanel();
	// Constructor:
	public GUI() {
		voterIdLBL = new JLabel("Enter your voterid below:");
		passwordLBL = new JLabel("Enter your password below:");
		voterIdTF = new JTextField (1);
		passwordTF = new JTextField (1);
		textArea = new TextArea("", 5, 30);
		submitBTN = new JButton("Click to submit voterid and password");
		
		panel.setLayout(new GridLayout(6,2));
		
		submitBTN.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("Funkar");
			}
			
		});
		
	    panel.add(voterIdLBL);
	    panel.add(voterIdTF);
	    panel.add(passwordLBL);
	    panel.add(passwordTF);
	    panel.add(submitBTN);
	    panel.add(textArea);
	    frame.add(panel);
	    frame.pack();
	    frame.setVisible(true);
	    
		frame.setTitle("Virtual Voting Booth");
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
	} 

	public static void main(String[] args) {
		JFrame votingBoothGUI = new GUI();
	}
}