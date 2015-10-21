import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.*;
import java.net.InetAddress;

import javax.net.ssl.SSLSocket;
import javax.swing.*;

public class GUI extends JFrame {
	
	//Labels, textfield etc. for GUI
	private JLabel voterIdLBL, passwordLBL;
	private JTextField voterIdTF, passwordTF;
	private TextArea textArea;
	private JButton submitBTN;
	
	final JFrame frame = new JFrame();
	final JPanel panel = new JPanel();
	
	private InetAddress host;
	static final int DEFAULT_CLA_PORT = 8189;	//Client to CLA 8189
	static final int DEFAULT_CTF_PORT = 8190;	//Client to CTF 8190
	
	// Constructor, creates a basic GUI to handle user input
	public GUI() {
		
		//Setting values for labels and textfields etc.
		voterIdLBL = new JLabel("Enter your voterid below:");
		passwordLBL = new JLabel("Enter your password below:");
		voterIdTF = new JTextField (1);
		passwordTF = new JTextField (1);
		textArea = new TextArea("", 5, 30);
		submitBTN = new JButton("Click to submit voterid and password");
		
		//Setting layout for the panel
		panel.setLayout(new GridLayout(6,2));
		
		//Adding labels, textfields etc. to the panel.
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
		//Exits program when closing the window.
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//Connection between Client and CLA&CTF servers.
		final ClientProtocol ptc = new ClientProtocol();
		
		//Retrieving address of the host and resolve it to an InetAddress.
		//Catching exception if not possible.
		try{
			InetAddress.getLocalHost();		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//Ports to be used for connection with CLA and CTF
		int portCLA = DEFAULT_CLA_PORT;
		int portCTF = DEFAULT_CTF_PORT;
		
		//Creates a new client with the resolved InetAdress and the specific CLA port. 
		Client CLAClient = new Client( host, portCLA );
		//Retrieves SSLSocket from the Client.
		final SSLSocket CLASocket = CLAClient.run();
		textArea.append("CLT Socket connection open \n");
		
		//ActionListener for the button that submits VoterID and Password. 
		submitBTN.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Checks if voter has entered VoterID and Password.
				if(!voterIdTF.getText().equals("") && !passwordTF.getText().equals("")){
					//Storing credentials in a String to send to the CLA server.
					String credentials = voterIdTF.getText() + " " + passwordTF.getText();
					//Makes sure that there is a connection to the CLA before sending message.
					if(CLASocket != null){
						ptc.sendMessage(CLASocket, credentials);
						textArea.append("Sending voterid: " + voterIdTF.getText() + "password: ******* to CLA server \n");
					}else{
						textArea.append("Socket to CLA is Null \n");
					}
					
					String[] msgStatus = ptc.getMessage(CLASocket);
					String[] msgValidationID = ptc.getMessage(CLASocket);
					
					if(msgStatus[0].equals("LoginSucceded")){
						textArea.append("Login Succeded \n");
						textArea.append("Your validationID is " + msgValidationID[0] + "\n");
					}
					else{
						textArea.append(msgStatus[0] + "\n");
					}
					
//					textArea.append(msgAccept[0] + "\n");
//					textArea.append("Your validationID is " + msgValidationID[0] + "\n");
					
				}
				
				
			}
			
		});
		
	} 

	public static void main(String[] args) {
		JFrame votingBoothGUI = new GUI();
	}
}