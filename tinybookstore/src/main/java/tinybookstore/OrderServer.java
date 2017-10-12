package tinybookstore;

import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.webserver.WebServer;

/**
 * XML-RPC Server that serves as the front-end server for the TinyBookStore
 * 
 * @author Hammad
 */
public class OrderServer {
	
	private static final int DEFAULT_SERVER_PORT = 8003;

	public static void main(String[] args) {
		try {
			System.out.println("Running Order Server ...");
			PropertyHandlerMapping mapping = new PropertyHandlerMapping();
			WebServer server = new WebServer(DEFAULT_SERVER_PORT);
			XmlRpcServer xmlRpcServer = server.getXmlRpcServer();
			mapping.addHandler("orderServer", OrderServer.class);
			xmlRpcServer.setHandlerMapping(mapping);
			server.start();
		} catch (Exception exception) {
			System.err.println("Order Server: " + exception);
			exception.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void buy(int itemNumber) {
		System.out.println("Order Server: Received request for buy");
		
		// check to see if item is in stock
		// the above function call is going to decrement the stock as well
		// update the stock periodically
	}

	/**
	 * Checks if a book is in stock and if so, decreases the stock by 1 (when order is being
	 * placed)
	 * 
	 * @param bookId
	 * @return
	 */
	public boolean inStock(int bookId) {
		/*if (bookId > 100 || bookId < 0) {
			throw new IllegalArgumentException("not in range 0-100");
		}*/
		return true;
	}

}
