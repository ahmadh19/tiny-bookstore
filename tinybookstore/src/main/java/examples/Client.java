package examples;

import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import tinybookstore.Book;

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
		int port = 8001;
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

		//ArrayList<Integer> params = new ArrayList<Integer>();
		//params.add(53477);
		
		ArrayList<String> params = new ArrayList<String>();
		params.add("college life");

		try {
			System.out.println("Client: about to execute RPC");
			Object[] result = (Object[]) client.execute(
					"frontEndServer.search", params.toArray());
			//String r = ((String) result[0]);
			for(int i = 0; i < result.length; i++) {
				String r = ((String) result[i]);
				System.out.println(r);
			}
			
			//System.out.println(Book.packArrayAsBook(result));
			List<Book> books = Book.packArrayAsBooks(result);
			for(Book b : books) 
				System.out.println(b);
			
		} catch (XmlRpcException e) {
			e.printStackTrace();
		}

	}
}