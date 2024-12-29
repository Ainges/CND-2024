using System.Text;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;

namespace payment_invoice_service.Messaging
{
    public class RabbitMqListener
    {
        public async Task StartListeningAsync()
        {
            var factory = new ConnectionFactory { HostName = "localhost" };
            await using var connection = await factory.CreateConnectionAsync();
            await using var channel = await connection.CreateChannelAsync();

            await channel.QueueDeclareAsync(
                queue: "pending-orders",
                durable: true,
                exclusive: false,
                autoDelete: false,
                arguments: null);

            Console.WriteLine(" [*] Waiting for messages.");

            var consumer = new AsyncEventingBasicConsumer(channel);
            consumer.ReceivedAsync += async (model, ea) =>
            {
                var body = ea.Body.ToArray();
                var message = Encoding.UTF8.GetString(body);
                Console.WriteLine($" [x] Received {message}");
                await Task.CompletedTask; // Hier bleibt es beim CompletedTask
            };

            await channel.BasicConsumeAsync("pending-orders", autoAck: true, consumer: consumer);

            Console.WriteLine(" Press [enter] to exit.");
            Console.ReadLine();
        }
    }
}