"""
Author: Cooper Baird
File: client.py
XMLRPC client interface for the Tiny Bookstore
"""

from book import Book
import xmlrpc.client, sys

"""Returns an array of books based on the results."""
def get_books(array):
    books = []
    num_of_books = int(len(array) / 5)
    for i in range(0, num_of_books):
        index = i*5
        book = Book(array[index:index+5])
        books.append(book)
    return books

hostname = "localhost"
port = "8001"
if len(sys.argv) > 1:
    hostname = sys.argv[1]
    port = str(sys.argv[2])
    
proxy = xmlrpc.client.ServerProxy("http://" + hostname + ":" + port + "/")

print("WELCOME TO THE TINY BOOKSTORE!")
while True:
    print()
    command = input("What do you want to do? (Search, Lookup, Buy) ").lower()
    if command == "search":
        topic = input("Topic: ")
        result = proxy.frontEndServer.search(topic)
        books = get_books(result)
        for i in books: print(i)
    elif command == "lookup":
        item_num = int(input("Item number: "))
        result = proxy.frontEndServer.lookup(item_num)
        books = get_books(result)
        print(books[0])
    elif command == "buy":
        item_num = int(input("Item number of book to buy: "))
        result = proxy.frontEndServer.buy(item_num)
        display = proxy.frontEndServer.lookup(item_num)
        books = get_books(display)
        print(books[0])
    elif command == "":
        break
    
        