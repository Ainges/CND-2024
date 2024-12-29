using payment_invoice_service.Models;

namespace payment_invoice_service.Data.Repositories;


public interface IInvoiceRepository
{
    Task<IEnumerable<Payment>> GetAllAsync();
    Task<Invoice> GetByIdAsync(int id);
    Task<Invoice> CreateAsync(Payment payment);
    Task<Invoice> UpdateAsync(Payment payment);
    Task<Invoice> DeleteAsync(int id);
}
public class InvoiceRepository
{

}