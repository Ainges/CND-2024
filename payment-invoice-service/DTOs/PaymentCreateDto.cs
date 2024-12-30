namespace payment_invoice_service.DTOs;

public class PaymentCreateDto
{
    public int InvoiceId { get; set; }
    public decimal Amount { get; set; }
    public string PaymentMethod { get; set; }
    public string TransactionId { get; set; }
}