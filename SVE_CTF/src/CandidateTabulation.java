import java.util.LinkedList;
import java.util.UUID;

/*
*   Author: Rickard & Michael
*   Liu-Student id: ricli877 & micsj823  
*/

/*
 * Database that holds all candidates
 */

public class CandidateTabulation {

	private LinkedList<UUID> candidate1 = new LinkedList<UUID>();
	private LinkedList<UUID> candidate2 = new LinkedList<UUID>();
	private LinkedList<UUID> candidate3 = new LinkedList<UUID>();
	private LinkedList<UUID> allVoters = new LinkedList<UUID>();
	
	private static class SingeltonHolder {
        public static final CandidateTabulation INSTANCE = new CandidateTabulation();
	}
		
	public static CandidateTabulation instance() {
		return SingeltonHolder.INSTANCE;
	}
		
	private CandidateTabulation() {
		
	}
	
	
	private enum Candidates {
		Musse, Kalle, Langben; 
	}
	
	/*
	 * addVote adds the users identification number to the specified vote
	 */
	public void addVote(UUID idNr, String vote){
		Candidates candidate = Candidates.valueOf(vote);
		
		System.out.println("You voted for: " + vote);
		
		switch(candidate){
		case Kalle:
			candidate1.add(idNr);
			allVoters.add(idNr);
			break;
		case Musse:
			candidate2.add(idNr);
			allVoters.add(idNr);
			break;
		case Langben:
			candidate3.add(idNr);
			allVoters.add(idNr);
			break;
		}
	}

	public void displayResult(){
		System.out.println("###############################################");
		System.out.println("Kalle Anka had " + candidate1.size() + " votes");
		System.out.println("Musse Pigg had " + candidate2.size() + " votes");
		System.out.println("Långben had " + candidate3.size() + " votes");
		System.out.println("Total voters: " + allVoters.size() + " votes");
	}
	
	public String getResult(){
		
		String result = "" + candidate1.size() + " " + candidate2.size() + " " + candidate3.size() + " " + allVoters.size() + "";
		
		return result;
		
	}
	
	public String getIds(int k){
		String msg = "";
		
		System.out.println("Size of candidate_1 " + candidate1.size());
		System.out.println("Size of candidate_2 " + candidate2.size());
		System.out.println("Size of candidate_3 " + candidate3.size());
		
		if(k == 1){
			if(candidate1.size() == 0) return "No vote";
			for(int i = 0; i < candidate1.size(); i++)
				msg = msg + " " + candidate1.get(i);	
		}
		else if(k == 2){
			if(candidate2.size() == 0) return "No vote";
			for(int i = 0; i < candidate2.size(); i++)
				msg = msg + " " + candidate2.get(i);	
		}
		else{
			if(candidate3.size() == 0) return "No vote";
			for(int i = 0; i < candidate3.size(); i++)
				msg = msg + " " + candidate3.get(i);	
		}
		
		return msg;
		
	}

	/*
	 * verifyVote verifies if the identification number has already 
	 * been used or not
	 */
	public boolean verifyVote(UUID idNr){
		for(UUID tmpId : allVoters){
            if(tmpId.equals(idNr))
                   return true;
            
		}
		return false;
	}
}