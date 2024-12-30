namespace payment_invoice_service.Models;

public class Invoice
{
    public int Id { get; set; }
    public int OrderId { get; set; }
    public string UserId { get; set; }
    public decimal TotalAmountInEuroCents { get; set; }
    public decimal PaidAmount { get; set; }
    public InvoiceStatus Status { get; set; }
    public String IssueDateAsString { get; set; }
    public String DueDateAsString { get; set; }
    public String CreatedDateAsString { get; set; }
    public String? UpdatedDate { get; set; }

    // One-to-many relationship with Payment
    public ICollection<Payment> Payments { get; set; }
}
