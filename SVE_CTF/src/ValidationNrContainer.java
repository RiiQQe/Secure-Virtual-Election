import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ValidationNrContainer {
	
	private Map <UUID, Boolean> map = new HashMap<UUID, Boolean>();
	
	private static class SingeltonHolder {
        public static final ValidationNrContainer INSTANCE = new ValidationNrContainer();
	}
		
	public static ValidationNrContainer instance() {
		return SingeltonHolder.INSTANCE;
	}
		
	private ValidationNrContainer() {
		
	}
	
	public void addNr(UUID nr){
		map.put(nr, true);
//		System.out.println(nr + " Added");
	}
	
	public void setVoted(UUID nr){
		map.put(nr, false);
//		System.out.println(nr + " Voted");
	}
	
	public boolean verifyNr(UUID nr){
		if(map.get(nr) == null){
			
			return false;
		}
		return map.get(nr);
	}
	
	public boolean verifyNewNr(UUID nr){
		if(map.get(nr) == null){
//			System.out.println("New Validation number: " +nr);
			return true;
		}
		return false;
	}

}