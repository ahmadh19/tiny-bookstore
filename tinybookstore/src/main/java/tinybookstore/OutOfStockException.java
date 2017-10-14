package tinybookstore;

public class OutOfStockException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public OutOfStockException() {
		super("Error: Requested item is out of stock...");
	}
	
}
