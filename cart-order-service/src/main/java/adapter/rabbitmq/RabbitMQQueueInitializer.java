package adapter.rabbitmq;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Channel;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class RabbitMQQueueInitializer {

    @ConfigProperty(name = "rabbitmq-host")
    String host;

    @ConfigProperty(name = "rabbitmq-username")
    String username;

    @ConfigProperty(name = "rabbitmq-password")
    String password;

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQQueueInitializer.class);


    public void initializeQueue() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // Host konfigurieren
        factory.setUsername("guest");
        factory.setPassword("guest");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Erstelle die Queue, falls sie nicht existiert
            channel.queueDeclare(
                    "pending-orders",  // Name der Queue
                    true,              // Durable: über Neustarts hinweg persistent
                    false,             // Exclusive: nicht exklusiv
                    false,             // Auto-delete: nicht automatisch löschen
                    null               // Zusätzliche Parameter
            );

        } catch (Exception e) {


            // this is necessary because the test environment does not have a RabbitMQ server and I don't want to mock it right now
            //TODO: Maybe give the test environment a RabbitMQ server
//            if (System.getProperty("quarkus.profile").equals("test")) {
//                logger.info("Skipping RabbitMQ queue initialization in test environment...");
//                return;
//            }

            throw new RuntimeException("Failed to initialize RabbitMQ queue", e);
        }
    }
}

