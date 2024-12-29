using Microsoft.Extensions.Hosting;
using System.Threading;
using System.Threading.Tasks;

namespace payment_invoice_service.Messaging
{
    public class RabbitMqListenerHostedService : BackgroundService
    {
        private readonly RabbitMqListener _listener;

        public RabbitMqListenerHostedService(RabbitMqListener listener)
        {
            _listener = listener;
        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {
            // Ensure the listener is properly stopped when the service stops
            await _listener.StartListeningAsync(stoppingToken);
        }

        public override async Task StopAsync(CancellationToken cancellationToken)
        {
            await _listener.StopListeningAsync();
            await base.StopAsync(cancellationToken);
        }
    }
}
