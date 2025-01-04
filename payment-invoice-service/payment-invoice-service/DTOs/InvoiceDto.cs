using payment_invoice_service.Models;

namespace payment_invoice_service.DTOs;

public class InvoiceDto
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

    // List of payment ids
    public List<int> PaymentIds { get; set; }
}