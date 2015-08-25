package utilities.dpexceptions;

public class DPExceedsQuantity extends RuntimeException {
	public DPExceedsQuantity() {
		super();
	}

	public DPExceedsQuantity(String message) {
		super(message);
	}
}
