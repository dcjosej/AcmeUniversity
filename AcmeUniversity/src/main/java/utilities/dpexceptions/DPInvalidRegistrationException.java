package utilities.dpexceptions;

public class DPInvalidRegistrationException extends RuntimeException{

	public DPInvalidRegistrationException(){
		super();
	}
	
	public DPInvalidRegistrationException(String message){
		super(message);
	}
}
