using System.ComponentModel.DataAnnotations.Schema;

namespace payment_invoice_service.Models;

public class Payment
{
    public int Id { get; set; }
    public InvoiceStatus Status { get; set; }
    public int OrderId { get; set; }
    public string Provider { get; set; }
    public string? TransactionId { get; set; }
    public double Amount { get; set; }
    public string Currency { get; set; }
    public DateTime Date
    {
        get => _date;
        set => _date = DateTime.SpecifyKind(value, DateTimeKind.Utc);
    }
    private DateTime _date;

}