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
	
	private static final XmlRpcClient catalogServer = new XmlRpcClient();
	private static final XmlRpcClient orderServer = new XmlRpcClient();

	public static void main(String[] args) {
		try {
			System.out.println("Running Front-End Server ...");
			PropertyHandlerMapping mapping = new PropertyHandlerMapping();
			WebServer server = new WebServer(DEFAULT_SERVER_PORT);
			XmlRpcServer xmlRpcServer = server.getXmlRpcServer();
			mapping.addHandler("frontEndServer", FrontEndServer.class);
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

			// establish a connection to the order server
			XmlRpcClientConfigImpl orderServerConfig = new XmlRpcClientConfigImpl();
			String serverURL2 = "http://" + serverMachine + ":" + 8003;
			try {
				orderServerConfig.setServerURL(new URL(serverURL2));
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
