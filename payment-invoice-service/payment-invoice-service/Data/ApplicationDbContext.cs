using Microsoft.EntityFrameworkCore;
using payment_invoice_service.Models;

namespace payment_invoice_service.Data;

public class ApplicationDbContext : DbContext
{
    public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : base(options)
    {
    }

    public DbSet<Payment> Payments { get; set; }
    public DbSet<Invoice> Invoices { get; set; }

}