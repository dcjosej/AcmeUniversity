package utilities.dpexceptions;

public class DPForbiddenException extends RuntimeException{

	public DPForbiddenException(){
		super();
	}
	
	public DPForbiddenException(String message){
		super(message);
	}
}
