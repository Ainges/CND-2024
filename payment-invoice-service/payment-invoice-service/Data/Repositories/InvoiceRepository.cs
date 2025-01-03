using payment_invoice_service.Models;
using payment_invoice_service.Models;
using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
public interface IInvoiceRepository
{
    Task<IEnumerable<Invoice>> GetAllAsync();
    Task<Invoice> GetByIdAsync(int id);
    Task<Invoice> CreateAsync(Invoice invoice);
    Task<Invoice> UpdateAsync(Invoice invoice);
    Task<Invoice> DeleteAsync(int id);
}


namespace payment_invoice_service.Data.Repositories
{
    public class InvoiceRepository : IInvoiceRepository
    {
        private readonly ApplicationDbContext _context;

        public InvoiceRepository(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<Invoice>> GetAllAsync()
        {
            return await _context.Invoices.ToListAsync();
        }

        public async Task<Invoice> GetByIdAsync(int id)
        {
            return await _context.Invoices.FindAsync(id);
        }

        public async Task<Invoice> CreateAsync(Invoice invoice)
        {
            _context.Invoices.Add(invoice);
            await _context.SaveChangesAsync();
            return invoice;
        }

        public async Task<Invoice> UpdateAsync(Invoice invoice)
        {
            _context.Invoices.Update(invoice);
            await _context.SaveChangesAsync();
            return invoice;
        }

        public async Task<Invoice> DeleteAsync(int id)
        {
            var invoice = await _context.Invoices.FindAsync(id);
            if (invoice != null)
            {
                _context.Invoices.Remove(invoice);
                await _context.SaveChangesAsync();
            }
            return invoice;
        }
    }
}