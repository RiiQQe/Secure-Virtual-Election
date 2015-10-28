import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.*;
import java.net.InetAddress;
import java.util.UUID;

import javax.net.ssl.SSLSocket;
import javax.swing.*;

public class GUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Labels, textfield etc. for GUI
	private JLabel voterIdLBL, passwordLBL, voteLBL;
	private JTextField voterIdTF, passwordTF, voteTF, idNrTF;
	private TextArea textArea;
	private JButton credentialsBTN, voteBTN, verifyBTN, printAll;
	
	private JRadioButton can1RBTN = new JRadioButton("Kalle Anka");
	private JRadioButton can2RBTN = new JRadioButton("Musse Pigg");
	private JRadioButton can3RBTN = new JRadioButton("Långben");
	
	private ButtonGroup groupBTN = new ButtonGroup();
	
	final JFrame frame = new JFrame();
	final JPanel panel = new JPanel();
	
	private InetAddress host;
	static final int DEFAULT_CLA_PORT = 8189;	//Client to CLA 8189
	static final int DEFAULT_CTF_PORT = 8190;	//Client to CTF 8190
	
	//UUID numbers that will be sent to CTF for verification.
	private UUID idNr = null, validNr = null;
	
	// Constructor, creates a basic GUI to handle user input.
	public GUI() {
		
		//Setting values for labels and textfields etc.
		voterIdLBL = new JLabel("Enter your voterid below:");
		voterIdTF = new JTextField (1);
		
		passwordLBL = new JLabel("Enter your password below:");
		passwordTF = new JTextField (1);
		
		voteLBL = new JLabel("Connect to CLA and CTF to vote..");
		//voteTF = new JTextField(1);
		voteBTN = new JButton("Click to submit your vote");
		
		textArea = new TextArea("", 120, 600);
		credentialsBTN = new JButton("Click to submit voterid and password");
		
		verifyBTN = new JButton("Click to verify vote");
		
		printAll = new JButton("Print all votes");
		
		idNrTF = new JTextField(1);
		
		printAll.setEnabled(false);
		
		can1RBTN.setEnabled(false);
		can2RBTN.setEnabled(false);
		can3RBTN.setEnabled(false);
		
		groupBTN.add(can1RBTN);
		groupBTN.add(can2RBTN);
		groupBTN.add(can3RBTN);
		
		//Setting layout for the panel
		panel.setLayout(new GridLayout(11,2));
		frame.setLayout(new GridLayout(2,1));
		
		//Adding labels, textfields etc. to the panel.
	    panel.add(voterIdLBL);
	    panel.add(voteLBL);
	    panel.add(voterIdTF);
	    panel.add(can1RBTN);
	    panel.add(passwordLBL);
	    panel.add(can2RBTN);
	    panel.add(passwordTF); 
	    panel.add(can3RBTN);
	    panel.add(credentialsBTN);
	    panel.add(voteBTN);
	    panel.add(idNrTF);
	    panel.add(printAll);
	    panel.add(verifyBTN);
	    
	    frame.add(panel);
	    frame.add(textArea);
	    frame.pack();
	    frame.setVisible(true);
	    
		frame.setTitle("Virtual Voting Booth");
		frame.setSize(1000, 1000);
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

		//Creates a new client with the resolved InetAdress and the specific CTF port. 
		Client CTFClient = new Client( host, portCTF);
		//Retrieves SSLSocket from the CTFClient.
		final SSLSocket CTFSocket = CTFClient.run();
		
		if(CTFSocket == null){
			textArea.append("CTF Socket is null \n");
		}
		else{
			textArea.append("CTF Socket connection open \n");
		}
		
		//Creates a new client with the resolved InetAdress and the specific CLA port. 
		Client CLAClient = new Client( host, portCLA );
		//Retrieves SSLSocket from the CLAClient.
		final SSLSocket CLASocket = CLAClient.run();
		
		if(CTFSocket == null){
			textArea.append("CLA Socket is null \n");
		}
		else{
			textArea.append("CLA Socket connection open \n");
		}
		
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
					
					//Status that says if the login succeded or not.
					String[] msgStatus = ptc.getMessage(CLASocket);
					
					if(msgStatus[0].equals("LoginSucceeded")){
						textArea.append("Login Succeeded \n");
						
						
						//Make voting enabled
						can1RBTN.setEnabled(true);
						can2RBTN.setEnabled(true);
						can3RBTN.setEnabled(true);
						
						//Retrieving validationnumber from CLA.
						validNr = ptc.getValidationId(CLASocket);
						idNr = genRandomIdNr();
						
						textArea.append("ID number: " + idNr);
						
					}
					else{
						textArea.append(msgStatus[0] + "\n");
					}
	
			
				}
				
				
			}
			
		});
		
		/*This is what happends when vote is done*/
		voteBTN.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(can1RBTN.isSelected()){
					String vote = "vote " + validNr + " " + idNr + " Kalle";
					
					if(CTFSocket != null){
						ptc.sendMessage(CTFSocket, vote);
						textArea.append("voted for: " + can1RBTN.getText());
					}
					else{
						textArea.append("Socket to CTF is null");
					}					
					textArea.append("\n");
					
					String[] answ = ptc.getMessage(CTFSocket);
					
					for(int i = 0; i < answ.length ; i++){
					
						textArea.append(answ[i] + " ");
					
					}
					textArea.append("\n");
					
				}
				
				else if(can2RBTN.isSelected()){
					String vote = "vote " + validNr + " " + idNr + " Musse";
					
					if(CTFSocket != null){
						ptc.sendMessage(CTFSocket, vote);
						textArea.append("voted for: " + can2RBTN.getText());
					}
					else{
						textArea.append("Socket to CTF is null");
					}					
					textArea.append("\n");
					
					String[] answ = ptc.getMessage(CTFSocket);
					
					for(int i = 0; i < answ.length ; i++){
					
						textArea.append(answ[i] + " ");
					
					}
					textArea.append("\n");
				}
				else if(can3RBTN.isSelected()){
					String vote = "vote " + validNr + " " + idNr + " Langben";
					
					
					if(CTFSocket != null){
						ptc.sendMessage(CTFSocket, vote);
						textArea.append("voted for: " + can3RBTN.getText());
					}
					else{
						textArea.append("Socket to CTF is null");
					}					
					textArea.append("\n");
					
					String[] answ = ptc.getMessage(CTFSocket);
					
					for(int i = 0; i < answ.length ; i++){
					
						textArea.append(answ[i] + " ");
					
					}
					textArea.append("\n");
				}
				
				printAll.setEnabled(true);
			}
			
		});
		
		verifyBTN.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!idNrTF.getText().equals("")){
					String verify = "validation " + validNr + " " + idNrTF.getText();
					ptc.sendMessage(CTFSocket, verify);
					
					textArea.append(idNrTF.getText());
					
					String[] answ = ptc.getMessage(CTFSocket);
					
					for(int i = 0; i< answ.length ; i++){
					
						textArea.append(answ[i] + " ");
					
					}
					
				}else{
					textArea.append("Fill in ID-nr to verify");
				}
				textArea.append("\n");
			}
			
		});
		
		printAll.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ptc.sendMessage(CTFSocket, "display");
				
			}
			
		});
		
		
		
	} 
	
	public UUID genRandomIdNr(){
		return UUID.randomUUID();
	}

	public static void main(String[] args) {
		JFrame votingBoothGUI = new GUI();
	}
}