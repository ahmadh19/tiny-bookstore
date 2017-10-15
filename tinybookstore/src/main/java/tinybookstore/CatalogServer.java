package tinybookstore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.webserver.WebServer;

/**
 * Server for the catalog
 * @author cooperbaird
 */
public class CatalogServer {
	
	// @cooper, why is the catalog final? -Hammad
	private static final Map<Integer, Book> catalog = new TreeMap<>(); 
	private static final int SERVER_PORT = 8888;
	
	public static void main(String[] args) {
		try {
			fillCatalog();
			System.out.println("Running Catalog Server ...");
			PropertyHandlerMapping mapping = new PropertyHandlerMapping();
			WebServer server = new WebServer(SERVER_PORT);
			XmlRpcServer xmlRpcServer = server.getXmlRpcServer();
			mapping.addHandler("catalogServer", CatalogServer.class);
			xmlRpcServer.setHandlerMapping(mapping);
			server.start();
		} catch (Exception exception) {
			System.err.println("Server: " + exception);
			exception.printStackTrace();
		}
	}
	
	/**
	 * Fills the book catalog with all the available books
	 */
	private static void fillCatalog() {
		catalog.put(53477, new Book(53477, "Achieving Less Bugs with More Hugs in CSCI 325", 
				30, 25.50, "distributed systems"));
		catalog.put(53573, new Book(53573, "Distributed Systems for Dummies", 
				26, 42.19, "distributed systems"));
		catalog.put(12365, new Book(12365, "Surviving College", 
				52, 17.25, "college life"));
		catalog.put(12498, new Book(12498, "Cooking for the Impatient Undergraduate", 
				13, 43.75, "college life"));
	}
	
	/**
	 * @param id the book's id
	 * @return the book with the corresponding id
	 */
	public String[] query(int id) {
		Book book = catalog.get(id);
		String[] result = Book.unpackBookAsArray(book);
		return result;
	}
	
	//TODO: WORK ON THIS METHOD. IT NEEDS TO BE FIXED
	/**
	 * Overloaded method
	 * @param topic the book's topic
	 * @return the books with the corresponding topic
	 */
	public String[] query(String topic) {
		List<Book> books = new ArrayList<>();
		for(Book b : catalog.values()) {
			if(b.getTopic().equals(topic))
				books.add(b);
		}
		
		String[] results = Book.unpackBooksAsArray(books);
		
		return results;
	}
	
	/**
	 * @param id the book's id
	 * @param books the number of books to buy
	 */
	public Integer updateStock(int id, int books) {
		Book bk = catalog.get(id);
		bk.setStockCount(bk.getStockCount() - books);
		catalog.put(id, bk);
		return new Integer(id);
	}
	
	/**
	 * @param id the book's id
	 * @param price the new price of the book
	 */
	public Integer updateCost(int id, double price) {
		Book bk = catalog.get(id);
		bk.setCost(price);
		catalog.put(id, bk);
		return new Integer(id);
	}
	
	/**
	 * @param id the book's id
	 * @param price the new price of the book
	 */
	public Integer inStock(int id) {
		Book bk = catalog.get(id);
		int stockCount = bk.getStockCount();
		if(stockCount > 0) {
			bk.setStockCount(stockCount - 1);
			catalog.put(id, bk);
			return 1; // cannot use boolean?
		}
		return 0;
	}
	
}
