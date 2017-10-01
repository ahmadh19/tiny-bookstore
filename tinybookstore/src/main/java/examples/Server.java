package examples;

import org.apache.xmlrpc.webserver.WebServer;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.PropertyHandlerMapping;

/**
 * XML-RPC Server that offers one operation: a sumAndDifference operation
 * 
 * @author Sara Sprenkle
 */
public class Server {
	private static final int SERVER_PORT = 8888;

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

	/**
	 * Calculates and returns the sum and difference of the given parameters
	 * 
	 * @param x
	 *            the first operand
	 * @param y
	 *            the second operand
	 * @return an array of the sum and difference, respectively, of parameters x and
	 *         y
	 */
	public Integer[] sumAndDifference(int x, int y) {
		System.out.println("Server: Received request for sumAndDifference");
		Integer[] array = new Integer[2];
		array[0] = x + y;
		array[1] = y - x;
		return array;
	}

	/**
	 * Example of a method that throws an exception
	 * 
	 * @param num
	 * @return
	 */
	public boolean isValid(int num) {
		if (num > 100 || num < 0) {
			throw new IllegalArgumentException("not in range 0-100");
		}
		return true;
	}

}