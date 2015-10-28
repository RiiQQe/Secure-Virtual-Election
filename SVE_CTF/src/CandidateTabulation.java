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
		
		switch(candidate){
		case Musse:
			candidate1.add(idNr);
			allVoters.add(idNr);
			break;
		case Kalle:
			candidate2.add(idNr);
			allVoters.add(idNr);
			break;
		case Langben:
			candidate3.add(idNr);
			allVoters.add(idNr);
			break;
		}
	}
	
	/*
	 * verifyVote verifies if the identification number has already 
	 * been used or not
	 */
	public boolean verifyVote(UUID idNr){
		for(UUID tmpId : allVoters){
            if(tmpId.equals(idNr)){
                   return true;
            }
		}
		return false;
	}
	
	public void displayResult(){
		System.out.println("###############################################");
		System.out.println("Musse had " + candidate1.size() + " votes");
		System.out.println("Kalle had " + candidate2.size() + " votes");
		System.out.println("Langben had " + candidate3.size() + " votes");
		System.out.println("Total voters: " + allVoters.size() + " votes");
	}
}