from pylab import *

def get_avg(times):
    sum = 0
    for t in times:
        sum += t
    return sum/len(times)

print("This script will generate a graph for the average time with respect to number of clients with pre-stored data.")
 
x = arange(1, 6)
y = [get_avg([0.008265544891357422]),
    get_avg([0.011538910388946532, 0.011554590225219727]),
    get_avg([0.014438920974731446, 0.01444758176803589, 0.014508554458618164]),
    get_avg([0.0198220911026001, 0.019915132522583007, 0.01997739839553833, 0.020008012771606444]),
    get_avg([0.020066205024719237, 0.020072597980499266, 0.020117639541625976, 0.02012364435195923, 0.020132964134216308])]
    
y2 = [get_avg([0.0027765598297119142]),
      get_avg([0.0042491774559021, 0.004266932010650635]),
      get_avg([0.0065076713562011715, 0.0065241150856018065, 0.00653036880493164]),
      get_avg([0.00809382724761963, 0.00810937213897705, 0.008150047779083252, 0.008157439231872558]),
      get_avg([0.010301807403564452, 0.010357067108154297, 0.010361146450042725, 0.01037150764465332, 0.010371622562408447])]
    
plot(x, y)
plot(x, y2)
    
xticks(arange(min(x), max(x)+1, 1.0))
     
xlabel('Number of Clients')
ylabel('Average time to execute request')
title('Average times to execute requests with respect to the number of clients')
grid(True)
show()

print("Script finished.")

