namespace payment_invoice_service.Models;

public enum InvoiceStatus
{
    PENDING,
    PAID,
    PARTIAL,
    OVERDUE,
    CANCELLED
}