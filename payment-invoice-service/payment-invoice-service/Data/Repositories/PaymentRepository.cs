using Microsoft.EntityFrameworkCore;
using payment_invoice_service.Models;

namespace payment_invoice_service.Data.Repositories;


public interface IPaymentRepository
{
    Task<IEnumerable<Payment>> GetAllAsync();
    Task<Payment> GetByIdAsync(int id);
    Task<Payment> CreateAsync(Payment payment);
    Task<Payment> UpdateAsync(Payment payment);
    Task<Payment> DeleteAsync(int id);
}

public class PaymentRepository : IPaymentRepository
{
    private readonly ApplicationDbContext _context;

    public PaymentRepository(ApplicationDbContext context)
    {
        _context = context;
    }

    public async Task<IEnumerable<Payment>> GetAllAsync()
    {
        return await _context.Payments.Include(a => a.Invoice).ToListAsync();
    }

    public async Task<Payment> GetByIdAsync(int id)
    {
        Payment? payment = await _context.Payments.Include(a => a.Invoice).Where(a => a.Id == id).FirstOrDefaultAsync();

        if (payment == null)
        {
            throw new PaymentRepositoryException("Payment not found");
        }

        return payment;
    }

    public async Task<Payment> CreateAsync(Payment payment)
    {

        _context.Payments.Add(payment);

        // check if payment was created
        if(await _context.SaveChangesAsync() > 0)
        {
            return payment;
        }
        else
        {
            throw new PaymentRepositoryException("Payment not created");
        }

    }

    public Task<Payment> UpdateAsync(Payment payment)
    {

        _context.Payments.Update(payment);

        // check if payment was updated
        if(_context.SaveChanges() > 0)
        {
            return Task.FromResult(payment);
        }
        else
        {
            throw new PaymentRepositoryException("Payment not updated");
        }
    }

    public async Task<Payment> DeleteAsync(int id)
    {
        Payment? payment = await _context.Payments.FindAsync(id);

        if (payment == null)
        {
            throw new PaymentRepositoryException("Payment not found");
        }

        _context.Payments.Remove(payment);

        // check if payment was deleted
        if(await _context.SaveChangesAsync() > 0)
        {
            return payment;
        }
        else
        {
            throw new PaymentRepositoryException("Payment not deleted");
        }
    }

}