package io.github.vm.patlego.jms.samples.commands;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;

@Service
@Command(scope = "examples", name = "topic-send", description = "Send a message to a JMS topic")
public class CommandTopicSender implements Action {

    @Argument(index = 0, name = "topic", description = "Name of the topic", required = true, multiValued = false)
    String queue;

    @Argument(index = 1, name = "message", description = "Message payload to send", required = true, multiValued = false)
    String message;

    @Reference
    ConnectionFactory connectionFactory;

    @Override
    public Object execute() throws Exception {
        Connection connection = null;
        Session session = null;
        
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic(queue);
            MessageProducer producer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage(message);
            producer.send(textMessage);
            session.close();
            connection.close();
        } finally {
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return null;
    }

    
}
