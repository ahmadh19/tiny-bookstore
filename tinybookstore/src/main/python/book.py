"""
Author: Hammad Ahmad
File: book.py
"""

class Book(object):
    "A Book object implementation"

    # Constructor
    def __init__(self, array):
        
        self.bookId = array[0]
        self.title = array[1]
        self.stockCount = array[4]
        self.cost = array[3]
        self.topic = array[2]

    def __str__(self):
        return "Book ID: " + str(self.bookId) + ", Title: " + self.title + ", Topic: " + self.topic + ", Items in Stock: " + str(self.stockCount) + ", Price: $" + str(self.cost);

    """
    "Book ID: " + bookId + ", Title: "+ title + ", Topic: " + topic + ", Items in Stock: " + stockCount + ", Price: $" + cost;
    public static String[] unpackBookAsArray(Book book) {
    def __init__(self, bookId, title, stockCount, cost, topic):
		String[] result = new String[5];
		result[0] = Integer.toString(book.getBookId());
		result[1] = book.getTitle();
		result[2] = book.getTopic();
		result[3] = Double.toString(book.getCost());
		result[4] = Integer.toString(book.getStockCount());
		return result;
	}
    """

    def __main__(self):
        b = Book([123, "TEST", "test category", 5.75, 15])
        print(b)
        
    
