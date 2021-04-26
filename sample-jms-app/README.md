# JMS Sample

This sample uses ActiveMQ as a messaging agent to send messages into a Queue or Topic in order for a consumer to ingest the message. 

## ActiveMQ Information

| Item | Definition |
|------|------------|
| Queue | A queue is used with one sender and one consumer. |
| Topic | A topic has one sender but multiple consumers. |


## Commands 

### Queue Commands 

`examples:queue-send TEST TEST`

This will create a queue called TEST and then send the message TEST through it.

`examples:queue-consume TEST`

This will connect to the queue TEST and consume the message that was sent in it.

### SCR Queue Listener

The `SAMPLE_QUEUE_1` is listening to an OSGi service and thus if you send a message using `queue-send` command you will see log file entries passing in.

### Topic Commands

`examples:topic-send TEST TEST`

This will create a TOPIC called TEST and send the message TEST into the TOPIC

### SCR Topic Listener

The `SCRTopicConsumer1` and `SCRTopicConsumer2` will listen to the `SAMPLE_TOPIC_1` topic and thus if you send a message in the topic you will see 2 entries in the log file one from the `SCRTopicConsumer1` and the other from the `SCRTopicConsumer2`.
