import xmlrpclib

proxy = xmlrpc.client.ServerProxy("http://localhost:8888/")

result = proxy.sumAndDifference(5, 6)
print(result)
