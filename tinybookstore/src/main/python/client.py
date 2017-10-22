"""
Author: Cooper Baird
File: client.py
XMLRPC client interface for the Tiny Bookstore
"""

from book import Book
import xmlrpc.client, sys

class Client:
    def __init__(self):
        hostname = "localhost"
        port = "8001"
        if len(sys.argv) > 1:
            hostname = sys.argv[1]
            port = str(sys.argv[2])
        self.proxy = xmlrpc.client.ServerProxy("http://" + hostname + ":" + port + "/")
    
    """Returns an array of books based on the results."""
    def get_books(self, array):
        books = []
        num_of_books = int(len(array) / 5)
        for i in range(num_of_books):
            index = i*5
            book = Book(array[index:index+5])
            books.append(book)
        return books
    
    def search(self):
        topic = input("Topic: ")
        result = self.proxy.frontEndServer.search(topic)
        books = self.get_books(result)
        if (len(books) == 0):
            print("No books found. Please try again.")
        else:
            for i in books:
                print(i)
                
    def lookup(self):
        item_num = int(input("Item number: "))
        try:
            result = self.proxy.frontEndServer.lookup(item_num)
            books = self.get_books(result)
            print(books[0])
        except Exception as exception:
            print("Book not found. Please try again.")
            #print("Got an exception:", exception)
            
    def buy(self):
        item_num = int(input("Item number of book to buy: "))
        try:
            result = self.proxy.frontEndServer.buy(item_num)
            display = self.proxy.frontEndServer.lookup(item_num)
            books = self.get_books(display)
            print("Bought book: ", books[0].title, ".")
        except Exception as exception:
            print("Got an exception:", exception)

def main():
    client = Client()
    print("WELCOME TO THE TINY BOOKSTORE!")
    while True:
        print()
        command = input("What do you want to do? (Search, Lookup, Buy) ").lower()
        if command == "search":
            client.search()
        elif command == "lookup":
            client.lookup()
        elif command == "buy":
            client.buy()
        elif command == "":
            break
        else:
            print("Invalid command. Please try again.")

if __name__ == '__main__':
    main()
        