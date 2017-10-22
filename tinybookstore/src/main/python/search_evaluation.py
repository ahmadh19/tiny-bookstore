from client import Client
import time

search_client = Client()
search_times = []

for i in range(500):
    start = time.time()
    # do the same search keyword for each search for consistent data
    search_client.search("distributed systems")
    end = time.time() 
    search_times.append(end - start)
    
average = sum(search_times)/len(search_times)
print("Average search time for 500 searches: " + str(average) + " seconds.")