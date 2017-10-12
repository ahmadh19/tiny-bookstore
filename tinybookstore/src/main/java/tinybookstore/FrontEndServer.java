package tinybookstore;

import java.net.MalformedURLException;
import java.net.URL;

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
			
			
			
		} catch (Exception exception) {
			System.err.println("Front-End Server: " + exception);
			exception.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	public Book[] search(String topic) {
		System.out.println("Front-End Server: Received request for search");
		Book[] array;
		array = null; //TODO: Implement catalog server
		return array;
	}
	
	/**
	 * 
	 */
	public Book[] lookup(int itemNumber) {
		System.out.println("Front-End Server: Received request for lookup");
		Book[] array;
		array = null; //TODO: Implement catalog server
		return array;
	}
	
	/**
	 * 
	 */
	public void buy(int itemNumber) {
		System.out.println("Front-End Server: Received request for buy");
		//TODO: Implement catalog server
	}

}
