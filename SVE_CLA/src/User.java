
import java.util.UUID;

public class User {

	private String name, psswrd;
	private UUID validId;
	
	public User(String name, String psswrd, UUID validId){
		this.name = name;
		this.psswrd = psswrd;
		
		//Set validId if it is null
		if(validId == null) validId = getValidId();
		
		this.validId = validId;
	}
	
	public UUID getValidId(){
		if(validId == null) validId = UUID.randomUUID();
		return validId;
	}
	
	public String getName(){
		System.out.println("name:" + name + "!");
		return name;
	}
	
	public String getPassword(){
		System.out.println("password:" + psswrd + "!");
		return psswrd;
	}
	
}
