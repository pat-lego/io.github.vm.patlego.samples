package io.github.vm.patlego.jms.samples;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Command(scope = "examples", name = "consume", description = "Consume a message from a JMS queue")
public class SimpleReceiver implements Action {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference
    ConnectionFactory connectionFactory;

    @Override
    public Object execute() throws Exception {
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("test");
        MessageConsumer consumer = session.createConsumer(destination);
        TextMessage message = (TextMessage) consumer.receive(60000);
        if (message == null) {
            throw new IllegalStateException("No message received");
        } else {
            logger.info(message.getText());
        }
        connection.close();
        return null;
    }
    
}
