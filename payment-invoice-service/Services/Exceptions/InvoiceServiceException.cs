namespace payment_invoice_service.Services.Exceptions;

public class InvoiceServiceException : ApplicationException
{

    public InvoiceServiceException(string message) : base(message)
    {
    }

}