package examples;

import org.apache.xmlrpc.webserver.WebServer;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.PropertyHandlerMapping;

/**
 * Server that offers one operation: a sumAndDifference operation
 * 
 * 
 * @author sprenkle
 * 
 */
public class Server {
	private static final int SERVER_PORT = 8888;

	public Integer[] sumAndDifference(int x, int y) {
		System.out.println("Server: Received request for sumAndDifference");
		Integer[] array = new Integer[2];
		array[0] = new Integer(x + y);
		array[1] = new Integer(y - x);
		return array;
	}

	public static void main(String[] args) {
		try {
			System.out.println("Running Server ...");
			PropertyHandlerMapping mapping = new PropertyHandlerMapping();
			WebServer server = new WebServer(SERVER_PORT);
			XmlRpcServer xmlRpcServer = server.getXmlRpcServer();
			mapping.addHandler("sample", Server.class);
			xmlRpcServer.setHandlerMapping(mapping);
			server.start();
		} catch (Exception exception) {
			System.err.println("Server: " + exception);
			exception.printStackTrace();
		}
	}
}