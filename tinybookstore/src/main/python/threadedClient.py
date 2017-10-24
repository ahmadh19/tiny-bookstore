"""
Author: Hammad Ahmad
File: threadedClient.py
XMLRPC threaded client evaluation script for the Tiny Bookstore 
"""

import threading
import time
from client import Client

average_buy_times = []
average_search_times = []

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
            self.buy(53477)
        elif self.operation == "search": 
            self.search("distributed systems")

    def buy(self, bookId):
        times = []
    
        for i in range(500):
            start = time.time()
            self.client.buy(bookId) 
            end = time.time() 
            times.append(end - start)
            
        avg = sum(times)/len(times)
        average_buy_times.append(avg)
        print(self.name + ": Average time to complete 500 buy requests: " + str(avg) + " seconds.")
        
    def search(self, term):
        times = []
    
        for i in range(500):
            start = time.time()
            self.client.search(term) 
            end = time.time() 
            times.append(end - start)
            
        avg = sum(times)/len(times)
        average_search_times.append(avg)
        print(self.name + ": Average time to complete 500 search requests: " + str(avg) + " seconds.")

def get_avg(times):
    sum = 0
    for t in times:
        sum += t
    return sum/len(times)
    
    
threads = []

command = input("How many clients do you want to test the system with? ")
number_of_clients = int(command)
request_type = ""

while True:
    request_type = input("What type of request do you want to test? (Buy, Search) ").lower()
    if request_type == "buy" or request_type == "search":
        break
    else:
        print("Invalid request. Please try again.")

# Create new threads
for i in range(number_of_clients):
    threads.append(threadedClient(i+1, "Client-" + str(i+1), request_type))
    
# Start new Threads
for t in threads:
    t.start()
    
# Wait for all threads to complete
for t in threads:
    t.join()
        
print ("Exiting Main Thread")
print()
if request_type == "buy":
    print("Average buy times:", average_buy_times)
elif request_type == "search":
    print("Average search times:", average_search_times)

"""
Based on several runs of the program, each with incrementing number of clients, the following
run times are the most common:

Buy requests:
1 Client: [0.008265544891357422]
2 Clients: [0.011538910388946532, 0.011554590225219727]
3 Clients: [0.014438920974731446, 0.01444758176803589, 0.014508554458618164]
4 Clients: [0.0198220911026001, 0.019915132522583007, 0.01997739839553833, 0.020008012771606444]
5 Clients: [0.020066205024719237, 0.020072597980499266, 0.020117639541625976, 0.02012364435195923, 0.020132964134216308]

Search requests:
1 Client: [0.0027765598297119142]
2 Clients: [0.0042491774559021, 0.004266932010650635]
3 Clients: [0.0065076713562011715, 0.0065241150856018065, 0.00653036880493164]
4 Clients: [0.00809382724761963, 0.00810937213897705, 0.008150047779083252, 0.008157439231872558]
5 Clients: [0.010301807403564452, 0.010357067108154297, 0.010361146450042725, 0.01037150764465332, 0.010371622562408447]

"""
