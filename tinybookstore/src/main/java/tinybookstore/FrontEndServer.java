package tinybookstore;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
public class FrontEndServer {
	
	private static final int DEFAULT_SERVER_PORT = 8001;
	private static final int DEFAULT_CATALOG_SERVER_PORT = 8002;
	private static final int DEFAULT_ORDER_SERVER_PORT = 8003;
	private static final String DEFAULT_SERVER_HOSTNAME = "localhost";
	private static final XmlRpcClient catalogServer = new XmlRpcClient();
	private static final XmlRpcClient orderServer = new XmlRpcClient();

	public static void main(String[] args) {
		int port = DEFAULT_SERVER_PORT;
		String hostname = DEFAULT_SERVER_HOSTNAME;
		int catalog_port = DEFAULT_CATALOG_SERVER_PORT;
		String catalog_hostname = DEFAULT_SERVER_HOSTNAME;
		int order_port = DEFAULT_ORDER_SERVER_PORT;
		String order_hostname = DEFAULT_SERVER_HOSTNAME;
		if( args.length == 6) {
			hostname = args[0];
			port = Integer.parseInt(args[1]);
			catalog_hostname = args[2];
			catalog_port = Integer.parseInt(args[3]);
			order_hostname = args[4];
			order_port = Integer.parseInt(args[5]);
		} 
		try {
			System.out.println("Running Front-End Server ...");
			PropertyHandlerMapping mapping = new PropertyHandlerMapping();
			WebServer server = new WebServer(port);
			XmlRpcServer xmlRpcServer = server.getXmlRpcServer();
			mapping.addHandler("frontEndServer", FrontEndServer.class);
			xmlRpcServer.setHandlerMapping(mapping);
			server.start();
			
			// establish a connection to the catalog server
			XmlRpcClientConfigImpl catalogServerConfig = new XmlRpcClientConfigImpl();
			String catalogServerURL = "http://" + catalog_hostname + ":" + Integer.toString(catalog_port);
			try {
				catalogServerConfig.setServerURL(new URL(catalogServerURL));
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			catalogServer.setConfig(catalogServerConfig);

			// establish a connection to the order server
			XmlRpcClientConfigImpl orderServerConfig = new XmlRpcClientConfigImpl();
			String orderServerURL = "http://" + order_hostname + ":" + Integer.toString(order_port);
			try {
				orderServerConfig.setServerURL(new URL(orderServerURL));
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			orderServer.setConfig(orderServerConfig);


		} catch (Exception exception) {
			System.err.println("Front-End Server: " + exception);
			exception.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	public Object[] search(String topic) {
		System.out.println("Front-End Server: Received request for search");
		try {
			ArrayList<String> params = new ArrayList<String>();
			params.add(topic);
			System.out.println("Front-End Server: about to execute RPC");
			Object[] result = (Object[]) catalogServer.execute(
					"catalogServer.query", params.toArray());
			return result;
		} catch (XmlRpcException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 */
	public Object[] lookup(Integer itemNumber) {
		System.out.println("Front-End Server: Received request for lookup");
		try {
			ArrayList<Integer> params = new ArrayList<Integer>();
			params.add(itemNumber);
			System.out.println("Front-End Server: about to execute RPC");
			Object[] result = (Object[]) catalogServer.execute(
					"catalogServer.query", params.toArray());
			return result;
		} catch (XmlRpcException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 */
	public Integer buy(int itemNumber) {
		System.out.println("Front-End Server: Received request for buy");
		
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(itemNumber);

		try {
			System.out.println("Front-End Server: about to execute RPC");
			Object result = (Object) orderServer.execute(
					"orderServer.buy", params.toArray());
			return (Integer) result;
			
		} catch (XmlRpcException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
