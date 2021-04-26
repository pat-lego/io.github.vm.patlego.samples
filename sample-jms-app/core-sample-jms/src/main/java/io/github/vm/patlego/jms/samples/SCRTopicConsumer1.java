package io.github.vm.patlego.jms.samples;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class SCRTopicConsumer1 {

    @Reference
    ConnectionFactory connectionFactory;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Connection connection;
    private Session session;
    
    @Activate
    public void activate() {
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic(Constants.SAMPLE_TOPIC_1);
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(new SimpleMessageListener());
            connection.start();
        } catch (Exception e) {
            logger.error("Failed to connect to the {} topic within ActiveMQ", Constants.SAMPLE_TOPIC_1, e);
        }
    }

    @Deactivate
    public void deactivate() {
        try {
            if (session != null) {
                session.close();
            }
           
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            logger.error("Failed to close the connection and the session to the {} topic", Constants.SAMPLE_TOPIC_1, e);
        }
    }
    
}
