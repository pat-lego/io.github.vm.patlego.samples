package io.github.vm.patlego.jms.samples;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;

@Service
@Command(scope = "examples", name = "queue-consume", description = "Consume a message from a JMS queue")
public class CommandQueueReceiver implements Action {

    @Argument(index = 0, name = "queue", description = "Name of the queue", required = true, multiValued = false)
    String queue;

    @Reference
    ConnectionFactory connectionFactory;

    @Override
    public Object execute() throws Exception {
        Connection connection = null;
        Session session = null;
        String result = null;

        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queue);
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(new SimpleMessageListener());
            connection.start();
            Thread.sleep(1000);
        } finally {
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
