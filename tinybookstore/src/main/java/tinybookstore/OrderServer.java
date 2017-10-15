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
	private static final XmlRpcClient catalogServer = new XmlRpcClient();

	public static void main(String[] args) {
		try {
			System.out.println("Running Order Server ...");
			PropertyHandlerMapping mapping = new PropertyHandlerMapping();
			WebServer server = new WebServer(DEFAULT_SERVER_PORT);
			XmlRpcServer xmlRpcServer = server.getXmlRpcServer();
			mapping.addHandler("orderServer", OrderServer.class);
			xmlRpcServer.setHandlerMapping(mapping);
			server.start();

			// establish a connection to the catalog server
			XmlRpcClientConfigImpl catalogServerConfig = new XmlRpcClientConfigImpl();
			String serverMachine = "localhost";
			String serverURL = "http://" + serverMachine + ":" + 8888;
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
	 * 
	 */
	public Integer buy(int itemNumber) {
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
		} catch (OutOfStockException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
