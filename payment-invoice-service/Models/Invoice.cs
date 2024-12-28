namespace payment_invoice_service.Models;

public class Invoice
{
    public int Id { get; set; }
    public InvoiceStatus Status { get; set; }
    public int OrderId { get; set; }
    public string Provider { get; set; }
    public double Amount { get; set; }
    public string Currency { get; set; }
    public DateTime Date { get; set; }
}