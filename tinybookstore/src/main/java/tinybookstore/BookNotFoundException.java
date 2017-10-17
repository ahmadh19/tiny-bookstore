package tinybookstore;

public class BookNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public BookNotFoundException() {
		super("Error: Invalid Book ID...");
	}
	
}
