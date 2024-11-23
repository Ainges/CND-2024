namespace payment_invoice_service.Data.Repositories;

public class PaymentRepositoryException : ApplicationException
{

    public PaymentRepositoryException(string message) : base(message)
    {
    }

}