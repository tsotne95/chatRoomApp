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
Server - done  
Client - done  
Basic chat - done  
Multiple clients - done  
Everyone get messages - done  
New client get history at starting - done  
Recovery backup at server starting - done  
Client supported commands - done  