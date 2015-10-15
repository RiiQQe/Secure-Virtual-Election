import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.*;
import java.net.InetAddress;

import javax.net.ssl.SSLSocket;
import javax.swing.*;

public class GUI extends JFrame {
	
	private JLabel voterIdLBL, passwordLBL;
	private JTextField voterIdTF, passwordTF;
	private TextArea textArea;
	private JButton submitBTN;
	final JFrame frame = new JFrame();
	final JPanel panel = new JPanel();
	
	private InetAddress host;
	static final int DEFAULT_CLA_PORT = 8189;	//Client to CLA 8189
	static final int DEFAULT_CTF_PORT = 8190;	//Client to STF 8190
	// Constructor:
	public GUI() {
		voterIdLBL = new JLabel("Enter your voterid below:");
		passwordLBL = new JLabel("Enter your password below:");
		voterIdTF = new JTextField (1);
		passwordTF = new JTextField (1);
		textArea = new TextArea("", 5, 30);
		submitBTN = new JButton("Click to submit voterid and password");
		
		panel.setLayout(new GridLayout(6,2));
		
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
		
		//Connection between Client and CLA&CTF servers
		final ClientProtocol ptc = new ClientProtocol();
		try{
			InetAddress.getLocalHost();		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		int portCLA = DEFAULT_CLA_PORT;
		int portCTF = DEFAULT_CTF_PORT;
		
		Client CLAClient = new Client( host, portCLA );
		final SSLSocket CLASocket = CLAClient.run();
		textArea.append("CLT Socket connection open \n");
		
		submitBTN.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!voterIdTF.getText().equals("") && !passwordTF.getText().equals("")){
					String credentials = voterIdTF.getText() + " " + passwordTF.getText();
					
					if(CLASocket != null){
						ptc.sendMessage(CLASocket, credentials);
					}else{
						textArea.append("Socket to CLA is Null \n");
					}
				}
			}
			
		});
		
	} 

	public static void main(String[] args) {
		JFrame votingBoothGUI = new GUI();
	}
}