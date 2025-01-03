using System.ComponentModel.DataAnnotations.Schema;

namespace payment_invoice_service.Models;

public class Payment
{
    public int Id { get; set; }
    public DateTime PaymentDate { get; set; }
    public decimal Amount { get; set; }
    public string PaymentMethod { get; set; }
    public string TransactionId { get; set; }

    [ForeignKey("InvoiceId")]
    public Invoice Invoice { get; set; }
}