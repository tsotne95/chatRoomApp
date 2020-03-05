**Chat application using Java RMI technology**

for compile run:
```bash
make
```

for clean:
```bash 
make clean
```

after go to **src** folder:
```bash
cd src
```

run server:
```bash
java server.ChatAppServer
```

for each client (each in new terminal or tab):
```bash
java client.ChatAppClient localhost nickname
```
example:
```bash
java client.ChatAppClient localhost Tsotne
java client.ChatAppClient localhost Eslam
```

Supported commands during chatting:
```bash
/quit -- to quit 
/who -- to see active users 
/nick <new name> -- change nickname
```

Chat support **history** feature, each new client get already existed messages. Also it is persistent, so if backup file exist (if not delete it by hand or clean project) for each server start all messages history would be recovered.


Application tested and everything works, like a charm:  
Server - :heavy_check_mark:  
Client - :heavy_check_mark:  
Basic chat - :heavy_check_mark:  
Multiple clients - :heavy_check_mark:  
Everyone get messages - :heavy_check_mark:  
New client get history at starting - :heavy_check_mark:  
Recovery backup at server starting - :heavy_check_mark:  
Client supported commands - :heavy_check_mark:  
