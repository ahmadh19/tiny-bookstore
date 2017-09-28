package examples;

import org.apache.xmlrpc.webserver.WebServer;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.PropertyHandlerMapping;

/**
 * Server 
 * 
 * 
 * @author sprenkle
 * 
 */
public class Server {
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
			PropertyHandlerMapping phm = new PropertyHandlerMapping();
			WebServer server = new WebServer(8888);
			XmlRpcServer xmlRpcServer = server.getXmlRpcServer();
			phm.addHandler("sample", Server.class);
			xmlRpcServer.setHandlerMapping(phm);
			server.start();
		} catch (Exception exception) {
			System.err.println("Server: " + exception);
			exception.printStackTrace();
		}
	}
}