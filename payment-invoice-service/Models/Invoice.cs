namespace payment_invoice_service.Models;

public class Invoice
{
    public int InvoiceId { get; set; }
    public int OrderId { get; set; }
    public string UserId { get; set; }
    public decimal TotalAmount { get; set; }
    public decimal PaidAmount { get; set; }
    public InvoiceStatus Status { get; set; }
    public DateTime IssueDate { get; set; }
    public DateTime DueDate { get; set; }
    public DateTime CreatedDate { get; set; }
    public DateTime? UpdatedDate { get; set; }
}
