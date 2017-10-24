from client import Client
import time

import matplotlib.pyplot as plt; plt.rcdefaults()
import numpy as np
import matplotlib.pyplot as plt

client = Client()
buy_times = []

for i in range(500):
    start = time.time()
    client.buy(53477) # buy book with ID 53477
    end = time.time() 
    buy_times.append(end - start)
    # add the time to a graph?
    
avg = sum(buy_times)/len(buy_times)
print("Average time to complete 500 buy requests: " + str(avg) + " seconds.")

search_times = []

for i in range(500):
    start = time.time()
    # do the same search keyword for each search for consistent data
    client.search("distributed systems")
    end = time.time() 
    search_times.append(end - start)
    
average = sum(search_times)/len(search_times)
print("Average search time for 500 searches: " + str(average) + " seconds.")

#----------------------------------------------------------------------------------------

objects = ('Buy', 'Search')
y_pos = np.arange(len(objects))
performance = [avg, average]

plt.bar(y_pos, performance, align='center', alpha=0.5)
plt.xticks(y_pos, objects)
plt.ylabel('Average time')
plt.title('Operation')
 
plt.show()