package tinybookstore;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.webserver.WebServer;

/**
 * XML-RPC Server that serves as the front-end server for the TinyBookStore
 * 
 * @author Hammad
 */
public class OrderServer {
	
	private static List<Integer> ordersList = new ArrayList<Integer>();
	private static final int DEFAULT_SERVER_PORT = 8003;
	private static final int DEFAULT_CATALOG_SERVER_PORT = 8002;
	private static final String DEFAULT_SERVER_HOSTNAME = "localhost";
	private static final XmlRpcClient catalogServer = new XmlRpcClient();

	public static void main(String[] args) {
		int port = DEFAULT_SERVER_PORT;
		String hostname = DEFAULT_SERVER_HOSTNAME;
		int catalog_port = DEFAULT_CATALOG_SERVER_PORT;
		String catalog_hostname = DEFAULT_SERVER_HOSTNAME;
		if( args.length == 4) {
			hostname = args[0];
			port = Integer.parseInt(args[1]);
			catalog_hostname = args[2];
			catalog_port = Integer.parseInt(args[3]);
		} 
		try {
			System.out.println("Running Order Server ...");
			PropertyHandlerMapping mapping = new PropertyHandlerMapping();
			WebServer server = new WebServer(port);
			XmlRpcServer xmlRpcServer = server.getXmlRpcServer();
			mapping.addHandler("orderServer", OrderServer.class);
			xmlRpcServer.setHandlerMapping(mapping);
			server.start();

			// establish a connection to the catalog server
			XmlRpcClientConfigImpl catalogServerConfig = new XmlRpcClientConfigImpl();
			String serverURL = "http://" + catalog_hostname + ":" + Integer.toString(catalog_port);
			try {
				catalogServerConfig.setServerURL(new URL(serverURL));
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			catalogServer.setConfig(catalogServerConfig);
			
		} catch (Exception exception) {
			System.err.println("Order Server: " + exception);
			exception.printStackTrace();
		}
	}

	/**
	 * @throws OutOfStockException 
	 * 
	 */
	public Integer buy(int itemNumber) throws OutOfStockException {
		System.out.println("Order Server: Received request for buy");
		
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(itemNumber);

		try {
			System.out.println("Order Server: about to execute RPC");
			Object result = (Object) catalogServer.execute(
					"catalogServer.inStock", params.toArray());
			if((Integer) result == 1) {
				System.out.println("Order Server: Buy request successful");
				ordersList.add(itemNumber);
				return new Integer(itemNumber);
			} else {
				throw new OutOfStockException();
			}
			
		} catch (XmlRpcException e) {
			e.printStackTrace();
		} 
		
		return null;
	}

}
