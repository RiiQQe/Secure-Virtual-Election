import java.util.UUID;

import javax.net.ssl.SSLSocket;

/*
*   Author: Rickard & Michael
*   Liu-Student id: ricli877 & micsj823  
*/

/*
 * Class that handles connection between CTF and client
 */
public class ClientHandler implements Runnable{
	private SSLSocket socketToClient;
	private CTFProtocol protocol = new CTFProtocol();
	
	public ClientHandler(SSLSocket socketClient){
		this.socketToClient = socketClient;
	}
	
	private enum Action {
		vote, validation, display; 
	}
	
	//Thread that will continue receiving/sending messages from/to client as long
	//as CTF is running. 
	public void run(){

		while(true){
			String[] msg = protocol.getMessage(socketToClient);
						
			Action action = Action.valueOf(msg[0]);
			
			//Different cases depending on what the first index of "msg" contains.
			switch(action){
			//This case handles all votes from the client.
			case vote:
				UUID validNr = UUID.fromString(msg[1]);
				UUID idNr = UUID.fromString(msg[2]);
				String vote = msg[3];
				
				if(ValIDNrContainer.instance().verifyNr(validNr)){
					ValIDNrContainer.instance().setVoted(validNr);
					CandidateTabulation.instance().addVote(idNr, vote);
					protocol.sendMessage(socketToClient, "Your vote has been accounted");
				}else{
					protocol.sendMessage(socketToClient, "You have allready voted!");
				}
				break;
			case validation:
				//This case handles the case when the user wants to validate the vote.
				UUID validNr2 = UUID.fromString(msg[1]);
				UUID idNr2 = UUID.fromString(msg[2]);
				if(ValIDNrContainer.instance().verifyNr(validNr2)){

					System.out.println("VOTE RECIEVED");
					protocol.sendMessage(socketToClient, "You have not voted");
				}else if(CandidateTabulation.instance().verifyVote(idNr2)){

					System.out.println("VOTE NOT RECIEVED");
					protocol.sendMessage(socketToClient, "Your vote is in the tabulation");
				}else{

					protocol.sendMessage(socketToClient, "Your identification is not in the tabulation, but your validation number is used");
				}
				break;
			case display:
				//This case handles the case when the user wants to display the result
				//of the voting.
				CandidateTabulation.instance().displayResult();
				break;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		

	}

}