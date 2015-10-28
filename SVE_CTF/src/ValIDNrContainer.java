import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
*   Author: Rickard & Michael
*   Liu-Student id: ricli877 & micsj823  
*/

/*
 * A container that holds validationIDs and if they have voted or not
 */
public class ValIDNrContainer {
	
	private Map <UUID, Boolean> map = new HashMap<UUID, Boolean>();
	
	private static class SingeltonHolder {
        public static final ValIDNrContainer INSTANCE = new ValIDNrContainer();
	}
		
	public static ValIDNrContainer instance() {
		return SingeltonHolder.INSTANCE;
	}
		
	private ValIDNrContainer() {
		
	}
	
	public void addNr(UUID number){
		map.put(number, true);
	}
	
	public void setVoted(UUID number){
		map.put(number, false);
	}
	
	public boolean verifyNr(UUID number){
		if(map.get(number) == null){
			
			return false;
		}
		return map.get(number);
	}
	
	public boolean verifyNewNr(UUID number){
		if(map.get(number) == null){
			return true;
		}
		return false;
	}

}