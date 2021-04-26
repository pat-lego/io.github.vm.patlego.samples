package io.github.vm.patlego.jms.samples;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleMessageListener implements MessageListener {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textmessage = (TextMessage) message;
            if (message == null) {
                throw new IllegalStateException("No message received");
            } else {
                logger.info("Received a message from the JMS queue - {}", textmessage.getText());
            }
        } catch (Exception e) {
            logger.error("Caught error when processing queue message", e);
        }

    }

}
