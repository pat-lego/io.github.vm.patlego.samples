package io.github.vm.patlego.jms.samples;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Command(scope = "examples", name = "consume", description = "Consume a message from a JMS queue")
public class SimpleReceiver implements Action {

    @Argument(index = 0, name = "queue", description = "Name of the queue", required = true, multiValued = false)
    String queue;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference(filter = "(name=patlego-vm-jms-sample1)")
    ConnectionFactory connectionFactory;

    @Override
    public Object execute() throws Exception {
        Connection connection = null;
        Session session = null;
        String result = null;

        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queue);
            MessageConsumer consumer = session.createConsumer(destination);
            TextMessage message = (TextMessage) consumer.receive(60000);
            if (message == null) {
                throw new IllegalStateException("No message received");
            } else {
                logger.info("Received a message from the JMS queue");
                result = message.getText();
            }
        }
        finally {
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
         return result;
    }
    
}
