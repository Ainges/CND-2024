package adapter.rabbitmq;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Startup
public class QueueSetupService {

    @ConfigProperty(name = "rabbitmq-host")
    String rabbitmqHost;

    @ConfigProperty(name = "rabbitmq-username")
    String rabbitmqUsername;

    @ConfigProperty(name = "rabbitmq-password")
    String rabbitmqPassword;

    //get profile from application.properties
    @ConfigProperty(name = "quarkus.profile")
    String profile;

    /*
     * CDI is only here available (?!), so we inject the values from the application.properties to the RabbitMQQueueInitializer
     */

    private final RabbitMQQueueInitializer initializer = new RabbitMQQueueInitializer();

    @PostConstruct
    public void setup() {
        initializer.initializeQueue(rabbitmqHost, rabbitmqUsername, rabbitmqPassword, profile);
        System.out.println("Queue initialized");
    }
}
