package utilities.dpexceptions;

public class DPNoFileException extends RuntimeException{

	public DPNoFileException(){
		super();
	}
	
	public DPNoFileException(String message){
		super(message);
	}
}
