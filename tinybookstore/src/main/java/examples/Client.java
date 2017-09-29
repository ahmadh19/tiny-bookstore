package examples;

import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 * XML-RPC client that talks to the XML-RPC server
 * 
 * @author Sara Sprenkle
 * 
 */
public class Client {
	private static final String DEFAULT_SERVER_HOSTNAME = "localhost";

	public static void main(String[] args) {
		System.out.println("Running client ...");
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		int port = 8888;
		String server = DEFAULT_SERVER_HOSTNAME;
		if( args.length == 1) {
			server = args[0];
		} 
		String serverURL = "http://" + server + ":" + port;
		try {
			config.setServerURL(new URL(serverURL));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);

		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(10);
		params.add(40);

		try {
			System.out.println("Client: about to execute RPC");
			Object[] result = (Object[]) client.execute(
					"sample.sumAndDifference", params.toArray());
			int sum = ((Integer) result[0]).intValue();
			System.out.println("The sum is: " + sum);
			int diff = ((Integer) result[1]).intValue();
			System.out.println("The difference is: " + diff);
		} catch (XmlRpcException e) {
			e.printStackTrace();
		}

	}
}