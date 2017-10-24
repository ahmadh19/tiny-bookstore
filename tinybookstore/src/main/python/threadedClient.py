import threading
import time
from client import Client

class threadedClient (threading.Thread):
    
    def __init__(self, threadID, name, operation):
        threading.Thread.__init__(self)
        self.threadID = threadID
        self.name = name
        self.client = Client()
        self.operation = operation
      
    def run(self):
        print ("Starting " + self.name)
        if self.operation == "buy":
            buy(self, 53477)
        elif self.operation == "search": 
            search(self, "distributed systems")

def buy(clientThread, bookId):
    times = []

    for i in range(500):
        start = time.time()
        clientThread.client.buy(bookId) 
        end = time.time() 
        times.append(end - start)
        
    avg = sum(times)/len(times)
    print(clientThread.name + ": Average time to complete 500 buy requests: " + str(avg) + " seconds.")
    
def search(clientThread, term):
    times = []

    for i in range(500):
        start = time.time()
        clientThread.client.search(term) 
        end = time.time() 
        times.append(end - start)
        
    avg = sum(times)/len(times)
    print(clientThread.name + ": Average time to complete 500 search requests: " + str(avg) + " seconds.")

threads = []

command = input("How many clients do you want to test the system with?")
number_of_clients = int(command)

# Create new threads
for i in range(number_of_clients):
    threads.append(threadedClient(i+1, "Client-" + str(i+1), "buy"))

# Start new Threads
for t in threads:
    t.start()

# Wait for all threads to complete
for t in threads:
    t.join()
    
print ("Exiting Main Thread")
