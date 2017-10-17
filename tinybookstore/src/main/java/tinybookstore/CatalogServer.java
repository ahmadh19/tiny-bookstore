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
	
	private static Map<Integer, Book> catalog = new TreeMap<>(); 
	private static final int DEFAULT_SERVER_PORT = 8002;
	private static final String DEFAULT_SERVER_HOSTNAME = "localhost";
	
	public static void main(String[] args) {
		int port = DEFAULT_SERVER_PORT;
		String hostname = DEFAULT_SERVER_HOSTNAME;
		if( args.length == 2) {
			hostname = args[0];
			port = Integer.parseInt(args[1]);
		} 
		try {
			fillCatalog();
			System.out.println("Running Catalog Server ...");
			PropertyHandlerMapping mapping = new PropertyHandlerMapping();
			WebServer server = new WebServer(port);
			XmlRpcServer xmlRpcServer = server.getXmlRpcServer();
			mapping.addHandler("catalogServer", CatalogServer.class);
			xmlRpcServer.setHandlerMapping(mapping);
			server.start();
			// automatic stock renewal based off of:
			// https://stackoverflow.com/questions/14281058/run-a-method-at-a-time-interval
			//TODO: THE STOCK RENEWAL IS NOT WORKING. FIX IT!
			while(true) {
			    long intervalInMs = 1000; // run every minute
			    long nextRun = System.currentTimeMillis() + intervalInMs;
			    synchronized(catalog) { //TODO: is it fine to do synchronized on catalog?
			    	updateStockAutomatically();
			    }
			    if (nextRun > System.currentTimeMillis()) {
			        Thread.sleep(nextRun - System.currentTimeMillis());
			    }
			}
		} catch (Exception exception) {
			System.err.println("Server: " + exception);
			exception.printStackTrace();
		}
	}
	
	/**
	 * Increments the stock count of every book by 10.
	 */
	private static void updateStockAutomatically() {
		for(Book b : catalog.values()) {
			catalog.put(b.getBookId(), b);
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
	 * @throws BookNotFoundException 
	 */
	public String[] query(int id) throws BookNotFoundException {
		Book book = catalog.get(id);
		if(book != null) {
			String[] result = Book.unpackBookAsArray(book);
			return result;
		} else {
			throw new BookNotFoundException();
		}
	}
	
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
	 * @throws BookNotFoundException 
	 */
	public Integer updateStock(int id, int books) throws BookNotFoundException {
		Book bk = catalog.get(id);
		if(bk != null) {
			bk.setStockCount(bk.getStockCount() - books);
			catalog.put(id, bk);
			return new Integer(id);
		} else {
			throw new BookNotFoundException();
		}
	}
	
	/**
	 * @param id the book's id
	 * @param price the new price of the book
	 * @throws BookNotFoundException 
	 */
	public Integer updateCost(int id, double price) throws BookNotFoundException {
		Book bk = catalog.get(id);
		if(bk != null) {
			bk.setCost(price);
			catalog.put(id, bk);
			return new Integer(id);
		} else {
			throw new BookNotFoundException();
		}
	}
	
	/**
	 * @param id the book's id
	 * @param price the new price of the book
	 * @throws BookNotFoundException 
	 */
	public Integer inStock(int id) throws BookNotFoundException {
		Book bk = catalog.get(id);
		if(bk != null) {
			synchronized(catalog) {
				int stockCount = bk.getStockCount();
				if(stockCount > 0) {
					bk.setStockCount(stockCount - 1);
					catalog.put(id, bk);
					return 1; // cannot use boolean?
				}
				return 0;
			}
		} else {
			throw new BookNotFoundException();
		}
	}
	
}
