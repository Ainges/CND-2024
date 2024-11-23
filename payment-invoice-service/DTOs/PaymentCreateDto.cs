namespace payment_invoice_service.DTOs;

public class PaymentCreateDto
{
    public int orderId { get; set; }
    public string provider { get; set; }
    public double amount { get; set; }
    public String currency { get; set; }
}