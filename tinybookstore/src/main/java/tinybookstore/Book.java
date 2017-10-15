package tinybookstore;

import java.util.ArrayList;
import java.util.List;

public class Book {

	private int bookId;
	private String title;
	private int stockCount;
	private double cost;
	private String topic;
	
	public Book(int bookId, String title, int stockCount, double cost, String topic) {
		this.bookId = bookId;
		this.title = title;
		this.stockCount = stockCount;
		this.cost = cost;
		this.topic = topic;
	}
	
	@Override
	public String toString() {
		return "Book ID: " + bookId + 
				", Title: "+ title + ", Topic: " + topic + ", Items in Stock: " + stockCount + ", Price: $" + cost;
	}
	
	/**
	 * @return the bookId
	 */
	public int getBookId() {
		return bookId;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @return the stockCount
	 */
	public int getStockCount() {
		return stockCount;
	}
	
	/**
	 * @param stockCount the stockCount to set
	 */
	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}
	
	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}
	
	/**
	 * @param cost the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}
	
	public static String[] unpackBookAsArray(Book book) {
		String[] result = new String[5];
		result[0] = Integer.toString(book.getBookId());
		result[1] = book.getTitle();
		result[2] = book.getTopic();
		result[3] = Double.toString(book.getCost());
		result[4] = Integer.toString(book.getStockCount());
		return result;
	}
	
	public static Book packArrayAsBook(Object[] array) {
		int bookId = Integer.parseInt((String) array[0]);
		String title = (String) array[1];
		String topic = (String) array[2];
		int stockCount = Integer.parseInt((String) array[4]);
		double cost = Double.parseDouble((String) array[3]);
		return new Book(bookId, title, stockCount, cost, topic);
	}
	
	public static String[] unpackBooksAsArray(List<Book> books) {
		String[] results = new String[ 5* books.size()];
		
		for(int i = 1; i < books.size() + 1; i++) {
			Book book = books.get(i-1);
			results[(5*i) - 5] = Integer.toString(book.getBookId());
			results[(5*i) - 4] = book.getTitle();
			results[(5*i) - 3] = book.getTopic();
			results[(5*i) - 2] = Double.toString(book.getCost());
			results[(5*i) - 1] = Integer.toString(book.getStockCount());
		}
		
		return results;
	}
	
	public static List<Book> packArrayAsBooks(Object[] array) {
		ArrayList<Book> books = new ArrayList<Book>();
		
		for(int i = 0; i < array.length; i=i+5) {
			int bookId = Integer.parseInt((String) array[i]);
			String title = (String) array[i+1];
			String topic = (String) array[1+2];
			double cost = Double.parseDouble((String) array[i+3]);
			int stockCount = Integer.parseInt((String) array[i+4]);
			books.add(new Book(bookId, title, stockCount, cost, topic));
		}
		
		return books;
	}
	
}
