package utilities.dpexceptions;

public class DPNotAvailable extends RuntimeException {
	public DPNotAvailable() {
		super();
	}

	public DPNotAvailable(String message) {
		super(message);
	}
}
