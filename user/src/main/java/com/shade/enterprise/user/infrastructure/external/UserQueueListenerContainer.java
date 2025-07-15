package com.shade.enterprise.user.infrastructure.external;

import com.shade.enterprise.shared.infrastructure.config.jms.MessageListenerContainer;

import io.quarkus.arc.Unremovable;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.jms.XAConnectionFactory;

@ApplicationScoped
@Unremovable
public class UserQueueListenerContainer {

    private final MessageListenerContainer container;

    private final XAConnectionFactory connectionFactory;

    private final UserExternalListener userExternalListener;

    @Inject
    public UserQueueListenerContainer(
            MessageListenerContainer container,
            @Named("forumXaConnectionFactory") XAConnectionFactory connectionFactory,
            UserExternalListener userExternalListener
    ) {
        this.container = container;
        this.connectionFactory = connectionFactory;
        this.userExternalListener = userExternalListener;
    }

    void onStart(@Observes StartupEvent ev) {
        container.startListener("usersQueueListener", connectionFactory, "users.queue", userExternalListener);
    }

    void onStop(@Observes ShutdownEvent ev) {
        container.stopListener("usersQueueListener");
    }

}
