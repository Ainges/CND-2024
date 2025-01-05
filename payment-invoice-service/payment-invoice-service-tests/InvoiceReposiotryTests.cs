using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using payment_invoice_service.Data;
using Xunit;
using payment_invoice_service.Data.Repositories;
using payment_invoice_service.Models;



namespace payment_invoice_service.Tests
{
    [Collection("SequentialTests")]
    public class InvoiceRepositoryTests
    {
        private readonly DbContextOptions<ApplicationDbContext> _options;

        public InvoiceRepositoryTests()
        {
            // In-Memory-Datenbank initialisieren
            _options = new DbContextOptionsBuilder<ApplicationDbContext>()
                .UseInMemoryDatabase(databaseName: "TestDatabase")
                .Options;

            // Datenbank vor jedem Test leeren
            using var context = new ApplicationDbContext(_options);
            context.Database.EnsureDeleted();
            context.Database.EnsureCreated();
        }

        [Fact]
        public async Task GetAllAsync_ShouldReturnAllInvoices()
        {
            // Arrange
            using var context = new ApplicationDbContext(_options);
            var repository = new InvoiceRepository(context);

            context.Invoices.Add(new Invoice { OrderId = 1, UserId = "1", TotalAmountInEuroCents = 500, PaidAmount = 0, Status = InvoiceStatus.PENDING, DueDateAsString = "", CreatedDateAsString = "", IssueDateAsString ="" ,Payments = new List<Payment>()});
            context.Invoices.Add(new Invoice { OrderId = 2, UserId = "2", TotalAmountInEuroCents = 1000, PaidAmount = 0, Status = InvoiceStatus.PENDING, DueDateAsString = "", CreatedDateAsString = "", IssueDateAsString = "", Payments = new List<Payment>()});
            await context.SaveChangesAsync();

            // Act
            var invoices = await repository.GetAllAsync();

            // Assert
            Assert.Equal(2, invoices.Count());
        }

        [Fact]
        public async Task DeleteAsync_ShouldDoNothing_WhenInvoiceDoesNotExist()
        {
            // Arrange
            using var context = new ApplicationDbContext(_options);
            var repository = new InvoiceRepository(context);

            // Act
            var result = await repository.DeleteAsync(1);

            // Assert
            Assert.Null(result);
            Assert.Equal(0, await context.Invoices.CountAsync());
        }

        [Fact]
        public async Task DeleteAsync_ShouldDeleteInvoice_WhenInvoiceExists()
        {
            // Arrange
            using var context = new ApplicationDbContext(_options);
            var repository = new InvoiceRepository(context);

            var invoice = new Invoice { OrderId = 1, UserId = "1", TotalAmountInEuroCents = 500, PaidAmount = 0, Status = InvoiceStatus.PENDING, DueDateAsString = "", CreatedDateAsString = "", IssueDateAsString = "", Payments = new List<Payment>()};
            context.Invoices.Add(invoice);
            await context.SaveChangesAsync();

            // Act
            var result = await repository.DeleteAsync(invoice.Id);

            // Assert
            Assert.NotNull(result);
            Assert.Equal(0, await context.Invoices.CountAsync());
        }

        [Fact]
        public async Task UpdateAsync_ShouldUpdateInvoice_WhenInvoiceExists()
        {
            // Arrange
            using var context = new ApplicationDbContext(_options);
            var repository = new InvoiceRepository(context);

            var invoice = new Invoice { OrderId = 1, UserId = "1", TotalAmountInEuroCents = 500, PaidAmount = 0, Status = InvoiceStatus.PENDING, DueDateAsString = "", CreatedDateAsString = "", IssueDateAsString = "", Payments = new List<Payment>()};
            context.Invoices.Add(invoice);
            await context.SaveChangesAsync();

            // Act
            invoice.Status = InvoiceStatus.PAID;
            var result = await repository.UpdateAsync(invoice);

            // Assert
            Assert.NotNull(result);
            Assert.Equal(InvoiceStatus.PAID, result.Status);
        }

        [Fact]
        public async Task GetByIdAsync_ShouldReturnInvoice_WhenInvoiceExists()
        {
            // Arrange
            using var context = new ApplicationDbContext(_options);
            var repository = new InvoiceRepository(context);

            var invoice = new Invoice { OrderId = 1, UserId = "1", TotalAmountInEuroCents = 500, PaidAmount = 0, Status = InvoiceStatus.PENDING, DueDateAsString = "", CreatedDateAsString = "", IssueDateAsString = "", Payments = new List<Payment>()};
            context.Invoices.Add(invoice);
            await context.SaveChangesAsync();

            // Act
            var result = await repository.GetByIdAsync(invoice.Id);

            // Assert
            Assert.NotNull(result);
            Assert.Equal(invoice.Id, result.Id);
        }


        [Fact]
        public async Task CreateAsync_ShouldCreateInvoice()
        {
            // Arrange
            using var context = new ApplicationDbContext(_options);
            var repository = new InvoiceRepository(context);

            var invoice = new Invoice { OrderId = 1, UserId = "1", TotalAmountInEuroCents = 500, PaidAmount = 0, Status = InvoiceStatus.PENDING, DueDateAsString = "", CreatedDateAsString = "", IssueDateAsString = "", Payments = new List<Payment>()};

            // Act
            var result = await repository.CreateAsync(invoice);

            // Assert
            Assert.NotNull(result);
            Assert.Equal(1, await context.Invoices.CountAsync());
        }

        [Fact]
        public async Task CreateAsync_ShouldReturnCreatedInvoice()
        {
            // Arrange
            using var context = new ApplicationDbContext(_options);
            var repository = new InvoiceRepository(context);

            var invoice = new Invoice { OrderId = 1, UserId = "1", TotalAmountInEuroCents = 500, PaidAmount = 0, Status = InvoiceStatus.PENDING, DueDateAsString = "", CreatedDateAsString = "", IssueDateAsString = "", Payments = new List<Payment>()};

            // Act
            var result = await repository.CreateAsync(invoice);

            // Assert
            Assert.NotNull(result);
            Assert.Equal(invoice.Id, result.Id);
        }

    }
}
