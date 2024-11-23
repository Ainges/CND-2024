namespace payment_invoice_service.Services.Exceptions;

public class PaymentServiceException : ApplicationException
{

        public PaymentServiceException(string message) : base(message)
        {
        }

}