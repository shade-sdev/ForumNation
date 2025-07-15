package com.shade.enterprise.shared.infrastructure.config.jms;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.quarkus.arc.Unremovable;

import lombok.extern.jbosslog.JBossLog;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.jms.JMSException;
import jakarta.jms.MessageConsumer;
import jakarta.jms.MessageListener;
import jakarta.jms.Queue;
import jakarta.jms.XAConnection;
import jakarta.jms.XAConnectionFactory;
import jakarta.jms.XASession;

@ApplicationScoped
@Unremovable
@JBossLog
public class MessageListenerContainer {

    private final Map<String, XAConnection> connections = new ConcurrentHashMap<>();

    public void startListener(
            String id,
            XAConnectionFactory connectionFactory,
            String queueName,
            MessageListener listener
    ) {
        try {
            XAConnection connection = connectionFactory.createXAConnection();
            connection.start();

            XASession session = connection.createXASession();
            Queue queue = session.createQueue(queueName);
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(listener);

            connections.put(id, connection);

            log.infof("✅ Listener '%s' started for queue '%s'", id, queueName);
        } catch (JMSException e) {
            throw new RuntimeException("Failed to start listener for queue: " + queueName, e);
        }
    }

    public void stopListener(String id) {
        XAConnection connection = connections.remove(id);
        if (connection != null) {
            try {
                connection.close();
                log.infof("❎ Listener '%s' stopped", id);
            } catch (JMSException e) {
                log.errorf("Failed to stop listener '%s'", id, e);
            }
        }
    }

    @PreDestroy
    public void shutdownAll() {
        connections.keySet().forEach(this::stopListener);
    }

}
