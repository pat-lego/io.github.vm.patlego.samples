# JMS Sample

This sample uses ActiveMQ as a messaging agent to send messages into a Queue or Topic in order for a consumer to ingest the message. 

## ActiveMQ Information

| Item | Definition |
|------|------------|
| Queue | A queue is used with one sender and one consumer. |
| Topic | A topic has one sender but multiple consumers. |


## Commands 

Drop the feature.xml file into the deploy folder of the running Karaf instance and then execute the following command

`examples:send TEST TEST`

This will create a queue called TEST and then send the message TEST through it.

`examples:consume TEST`

This will connect to the queue TEST and consume the message that was sent in it.
