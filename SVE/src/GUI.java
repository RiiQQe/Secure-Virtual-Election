import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.*;
import java.net.InetAddress;
import java.util.UUID;

import javax.net.ssl.SSLSocket;
import javax.swing.*;

public class GUI extends JFrame {
	
	//Labels, textfield etc. for GUI
	private JLabel voterIdLBL, passwordLBL, voteLBL;
	private JTextField voterIdTF, passwordTF, voteTF;
	private TextArea textArea;
	private JButton credentialsBTN, voteBTN;
	
	final JFrame frame = new JFrame();
	final JPanel panel = new JPanel();
	
	private InetAddress host;
	static final int DEFAULT_CLA_PORT = 8189;	//Client to CLA 8189
	static final int DEFAULT_CTF_PORT = 8190;	//Client to CTF 8190
	
	private UUID idNr = null, validNr = null;
	
	// Constructor, creates a basic GUI to handle user input
	public GUI() {
		
		//Setting values for labels and textfields etc.
		voterIdLBL = new JLabel("Enter your voterid below:");
		voterIdTF = new JTextField (1);
		
		passwordLBL = new JLabel("Enter your password below:");
		passwordTF = new JTextField (1);
		
		voteLBL = new JLabel("Enter your vote below:");
		voteTF = new JTextField(1);
		voteBTN = new JButton("Click to submit your vote");
		
		textArea = new TextArea("", 5, 30);
		credentialsBTN = new JButton("Click to submit voterid and password");
		
		
		//Setting layout for the panel
		panel.setLayout(new GridLayout(6,2));
		frame.setLayout(new GridLayout(3,1));
		
		//Adding labels, textfields etc. to the panel.
	    panel.add(voterIdLBL);
	    panel.add(voteLBL);
	    panel.add(voterIdTF);
	    panel.add(voteTF);
	    panel.add(passwordLBL);
	    panel.add(voteBTN);
	    panel.add(passwordTF);
	    
	    frame.add(panel);
	    frame.add(credentialsBTN);
	    frame.add(textArea);
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

		Client CTFClient = new Client( host, portCTF);
		final SSLSocket CTFSocket = CTFClient.run();
		textArea.append("CTF Socket connection open \n");
		
		//Creates a new client with the resolved InetAdress and the specific CLA port. 
		Client CLAClient = new Client( host, portCLA );
		//Retrieves SSLSocket from the Client.
		final SSLSocket CLASocket = CLAClient.run();
		textArea.append("CLA Socket connection open \n");

		
		//ActionListener for the button that submits VoterID and Password. 
		credentialsBTN.addActionListener(new ActionListener(){

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
//					String[] msgValidationID = ptc.getMessage(CLASocket);
					
					if(msgStatus[0].equals("LoginSucceded")){
						textArea.append("Login Succeded \n");
						
						validNr = ptc.getVerificationNr(CLASocket);
						idNr = getIdentificationNr();
						
						textArea.append("sending : " + validNr + " " + idNr + " to ctf \n");
						
					}
					else{
						textArea.append(msgStatus[0] + "\n");
					}
	
			
				}
				
				
			}
			
		});
		
		voteBTN.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!voteTF.equals("")){
					String vote = "vote " + validNr + " " + idNr + " " + "Candidate_1";
					textArea.append(vote + "\n");
					
					if(CTFSocket != null){
						ptc.sendMessage(CTFSocket, vote);
						textArea.append("You voted for Candidate_1");
					}
					else{
						textArea.append("Socket to CTF is null");
					}
					
				}
				
			}
			
		});
		
	} 
	
	public UUID getIdentificationNr(){
		return UUID.randomUUID();
	}

	public static void main(String[] args) {
		JFrame votingBoothGUI = new GUI();
	}
}