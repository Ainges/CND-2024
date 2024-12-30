using System.Text;
using payment_invoice_service.Models;
using payment_invoice_service.Services;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;

namespace payment_invoice_service.Messaging
{
    public class RabbitMqListener
    {
        private readonly IConfiguration _configuration;
        private readonly InvoiceService _invoiceService;

        public RabbitMqListener(IConfiguration configuration, InvoiceService invoiceService)
        {
            _configuration = configuration;
            _invoiceService = invoiceService;
        }

        public async Task ProcessQueueMessagesAsync(CancellationToken cancellationToken = default)
        {
            var factory = new ConnectionFactory
            {
                HostName = _configuration["RabbitMq:Host"] ?? "localhost",
                UserName = _configuration["RabbitMq:Username"] ?? "guest",
                Password = _configuration["RabbitMq:Password"] ?? "guest"
            };
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

                Invoice invoice = await _invoiceService.CreateInvoiceFromQueue(message);

                Console.WriteLine($" ### Invoice created with ID: {invoice.Id}");

            };

            await channel.BasicConsumeAsync("pending-orders", autoAck: true, consumer: consumer);

            // Keep the service running
            while (!cancellationToken.IsCancellationRequested)
            {
                await Task.Delay(1000, cancellationToken);
            }
        }
    }
}