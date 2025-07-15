package com.shade.enterprise.config;

import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.messaginghub.pooled.jms.JmsPoolXAConnectionFactory;

import io.quarkus.arc.Unremovable;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.jms.Session;
import jakarta.jms.XAConnectionFactory;
import jakarta.transaction.TransactionManager;

@ApplicationScoped
@Unremovable
public class JmsConfiguration {

    @Produces
    @Named("forumXaConnectionFactory")
    public JmsPoolXAConnectionFactory jmsPoolXAConnectionFactory(
            TransactionManager transactionManager
    ) {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(10);
        redeliveryPolicy.setRedeliveryDelay(0);
        redeliveryPolicy.setMaximumRedeliveryDelay(0);

        ActiveMQXAConnectionFactory xaConnectionFactory = new ActiveMQXAConnectionFactory();
        xaConnectionFactory.setBrokerURL("tcp://localhost:11616");
        xaConnectionFactory.setUserName("admin");
        xaConnectionFactory.setPassword("admin");
        xaConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        xaConnectionFactory.setXaAckMode(Session.AUTO_ACKNOWLEDGE);

        JmsPoolXAConnectionFactory jmsPoolXAConnectionFactory = new JmsPoolXAConnectionFactory();
        jmsPoolXAConnectionFactory.setConnectionFactory(xaConnectionFactory);
        jmsPoolXAConnectionFactory.setTransactionManager(transactionManager);

        return jmsPoolXAConnectionFactory;
    }

    @Produces
    @Named("forumJmsContext")
    public JMSContext jmsContext(@Named("forumXaConnectionFactory") XAConnectionFactory connectionFactory) {
        var context = connectionFactory.createXAContext();
        context.start();
        return context;
    }

    @Produces
    @Named("forumJmsProducer")
    public JMSProducer jmsProducer(@Named("forumJmsContext") JMSContext jmsContext) {
        return jmsContext.createProducer();
    }

}
