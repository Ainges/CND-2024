package adapter.rabbitmq;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
@Startup
public class QueueSetupService {



    private final RabbitMQQueueInitializer initializer = new RabbitMQQueueInitializer();

    @PostConstruct
    public void setup() {
        initializer.initializeQueue();
        System.out.println("Queue initialized");
    }
}
