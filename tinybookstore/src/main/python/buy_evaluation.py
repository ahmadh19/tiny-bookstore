from client import Client
import time

client = Client()
buy_times = []

for i in range(500):
    start = time.time()
    # do the same search keyword for each search for consistent data
    client.buy(53477)
    end = time.time() 
    buy_times.append(end - start)
    
average = sum(buy_times)/len(buy_times)
print("Average time to complete 500 buy requests: " + str(average) + " seconds.")