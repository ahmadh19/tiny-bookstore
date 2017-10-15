"""
Example Python XML-RPC client talking to the Java XML-RPC server

@author: Sara Sprenkle
"""

import xmlrpc.client

proxy = xmlrpc.client.ServerProxy("http://localhost:8888/")

result = proxy.sample.sumAndDifference(5, 6)
sum = result[0]
difference = result[1]
print("Sum: ", sum)
print("Difference: ", difference)

isValid = proxy.sample.isValid(0)

print("Is 0 valid? ", isValid)

try:
    isValid = proxy.sample.isValid(-1)
except Exception as exception:
    print("got an exception:", exception)