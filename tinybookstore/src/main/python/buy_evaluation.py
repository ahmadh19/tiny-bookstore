from client import Client
import time

client = Client()
times = []

for i in range(500):
    start = time.time()
    client.buy(53477) # buy book with ID 53477
    end = time.time() 
    times.append(end - start)
    # add the time to a graph?
    
avg = sum(times)/len(times)
print("Average time to complete 500 buy requests: " + str(avg) + " seconds.")