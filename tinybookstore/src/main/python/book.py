"""
Author: Hammad Ahmad
File: book.py
A Book object implementation
"""

class Book:
    def __init__(self, array):
        self.bookId = array[0]
        self.title = array[1]
        self.stockCount = array[4]
        self.cost = array[3]
        self.topic = array[2]

    def __str__(self):
        return "Book ID: " + str(self.bookId) + ", Title: " + self.title + ", Topic: " + self.topic + ", Items in Stock: " + str(self.stockCount) + ", Price: $" + str(self.cost);

def main():
    b = Book([123, "TEST", "test category", 5.75, 15])
    print(b)
    
if __name__ == '__main__':
    main()
