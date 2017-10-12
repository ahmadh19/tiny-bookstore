package tinybookstore;

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
		return title + ": " + bookId + "Topic: " + topic + ", Items in Stock: " + stockCount + ", Price: $" + cost;
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
	
}
