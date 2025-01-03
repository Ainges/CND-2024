namespace payment_invoice_service.Messaging
{
    public class RabbitMqListenerHostedService : BackgroundService
    {
        private readonly IServiceScopeFactory _serviceScopeFactory;

        public RabbitMqListenerHostedService(IServiceScopeFactory serviceScopeFactory)
        {
            _serviceScopeFactory = serviceScopeFactory;
        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {
            using var scope = _serviceScopeFactory.CreateScope();
            var rabbitMqListener = scope.ServiceProvider.GetRequiredService<RabbitMqListener>();

            try
            {
                await rabbitMqListener.ProcessQueueMessagesAsync(stoppingToken);
            }
            catch (OperationCanceledException)
            {
                // Gracefully handle shutdown
                Console.WriteLine("RabbitMQ Listener has stopped.");
            }
        }
    }
}