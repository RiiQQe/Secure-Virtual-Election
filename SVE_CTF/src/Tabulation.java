import java.util.LinkedList;
import java.util.UUID;

public class Tabulation {

	private LinkedList<UUID> candidate1 = new LinkedList<UUID>();
	private LinkedList<UUID> candidate2 = new LinkedList<UUID>();
	private LinkedList<UUID> candidate3 = new LinkedList<UUID>();
	private LinkedList<UUID> candidate4 = new LinkedList<UUID>();
	private LinkedList<UUID> allVoters = new LinkedList<UUID>();
	
	
	private static class SingeltonHolder {
        public static final Tabulation INSTANCE = new Tabulation();
	}
		
	public static Tabulation instance() {
		return SingeltonHolder.INSTANCE;
	}
		
	private Tabulation() {
		
	}
	
	
	private enum Candidates {
		Musse, Kalle, Langben; 
	}
	
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
	
	public void displayResult(){
		System.out.println("###############################################");
		System.out.println("Candidate_1 had " + candidate1.size() + " votes");
		System.out.println("Candidate_2 had " + candidate2.size() + " votes");
		System.out.println("Candidate_3 had " + candidate3.size() + " votes");
		System.out.println("Total voters: " + allVoters.size() + " votes");
	}
	
	public boolean verifyVote(UUID idNr){
		for(UUID tmpId : allVoters){
            if(tmpId.equals(idNr)){
                   return true;
            }
		}
		return false;
	}
}