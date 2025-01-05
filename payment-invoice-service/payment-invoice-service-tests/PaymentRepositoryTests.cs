using Microsoft.EntityFrameworkCore;
using payment_invoice_service.Data;
using payment_invoice_service.Data.Repositories;
using payment_invoice_service.Models;

namespace payment_invoice_service.Tests;

[Collection("SequentialTests")]
public class PaymentRepositoryTests
{
    private readonly DbContextOptions<ApplicationDbContext> _options;
    static String paymentMethode = "PayPal";

    public PaymentRepositoryTests()
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
    public async Task GetAllAsync_ShouldReturnAllPayments()
    {
        // Arrange
        using var context = new ApplicationDbContext(_options);
        var repository = new PaymentRepository(context);

        Invoice invoice1 = new Invoice { OrderId = 1, UserId = "1", TotalAmountInEuroCents = 500, PaidAmount = 0, Status = InvoiceStatus.PENDING, DueDateAsString = "", CreatedDateAsString = "", IssueDateAsString ="" ,Payments = new List<Payment>()};
        Invoice invoice2 = new Invoice { OrderId = 2, UserId = "2", TotalAmountInEuroCents = 1000, PaidAmount = 0, Status = InvoiceStatus.PENDING, DueDateAsString = "", CreatedDateAsString = "", IssueDateAsString = "", Payments = new List<Payment>()};

        context.Payments.Add(new Payment { PaymentDate = DateTime.Today, Amount = 500, PaymentMethod = paymentMethode, TransactionId = "1", Invoice = invoice1 });
        context.Payments.Add(new Payment { PaymentDate = DateTime.Today, Amount = 1000, PaymentMethod = "CreditCard", TransactionId = "2", Invoice = invoice2 });
        await context.SaveChangesAsync();

        // Act
        var payments = await repository.GetAllAsync();

        // Assert
        Assert.Equal(2, payments.Count());
    }

    [Fact]
    public async Task CreateAsync_ShouldCreatePayment()
    {
        // Arrange
        using var context = new ApplicationDbContext(_options);
        var repository = new PaymentRepository(context);

        Invoice invoice = new Invoice { OrderId = 1, UserId = "1", TotalAmountInEuroCents = 500, PaidAmount = 0, Status = InvoiceStatus.PENDING, DueDateAsString = "", CreatedDateAsString = "", IssueDateAsString ="" ,Payments = new List<Payment>()};

        Payment payment = new Payment { PaymentDate = DateTime.Today, Amount = 500, PaymentMethod = paymentMethode, TransactionId = "1", Invoice = invoice };

        // Act
        var result = await repository.CreateAsync(payment);

        // Assert
        Assert.NotNull(result);
        Assert.Equal(1, await context.Payments.CountAsync());
    }

    [Fact]
    public async Task UpdateAsync_ShouldUpdatePayment()
    {
        // Arrange
        using var context = new ApplicationDbContext(_options);
        var repository = new PaymentRepository(context);
        

        Invoice invoice = new Invoice { OrderId = 1, UserId = "1", TotalAmountInEuroCents = 500, PaidAmount = 0, Status = InvoiceStatus.PENDING, DueDateAsString = "01.1.01", CreatedDateAsString = "01.01.01", IssueDateAsString ="" ,Payments = new List<Payment>()};

        Payment payment = new Payment { PaymentDate = DateTime.Today, Amount = 500, PaymentMethod = paymentMethode, TransactionId = "1", Invoice = invoice };

        context.Payments.Add(payment);
        await context.SaveChangesAsync();

        payment.Amount = 1000;

        // Act
        var result = await repository.UpdateAsync(payment);

        // Assert
        Assert.NotNull(result);
        Assert.Equal(1, await context.Payments.CountAsync());
        Assert.Equal(1000, result.Amount);
    }

    [Fact]
    public async Task DeleteAsync_ShouldDeletePayment()
    {
        // Arrange
        using var context = new ApplicationDbContext(_options);
        var repository = new PaymentRepository(context);

        Invoice invoice = new Invoice { OrderId = 1, UserId = "1", TotalAmountInEuroCents = 500, PaidAmount = 0, Status = InvoiceStatus.PENDING, DueDateAsString = "01.1.01", CreatedDateAsString = "01.01.01", IssueDateAsString ="" ,Payments = new List<Payment>()};

        Payment payment = new Payment { PaymentDate = DateTime.Today, Amount = 500, PaymentMethod = paymentMethode, TransactionId = "1", Invoice = invoice };

        context.Payments.Add(payment);
        await context.SaveChangesAsync();

        // Act
        var result = await repository.DeleteAsync(payment.Id);

        // Assert
        Assert.NotNull(result);
        Assert.Equal(0, await context.Payments.CountAsync());
    }

    [Fact]
    public async Task GetByIdAsync_ShouldReturnPayment_WhenPaymentExists()
    {
        // Arrange
        using var context = new ApplicationDbContext(_options);
        var repository = new PaymentRepository(context);

        Invoice invoice = new Invoice { OrderId = 1, UserId = "1", TotalAmountInEuroCents = 500, PaidAmount = 0, Status = InvoiceStatus.PENDING, DueDateAsString = "01.1.01", CreatedDateAsString = "01.01.01", IssueDateAsString ="" ,Payments = new List<Payment>()};

        Payment payment = new Payment { PaymentDate = DateTime.Today, Amount = 500, PaymentMethod = paymentMethode, TransactionId = "1", Invoice = invoice };

        context.Payments.Add(payment);
        await context.SaveChangesAsync();

        // Act
        var result = await repository.GetByIdAsync(payment.Id);

        // Assert
        Assert.NotNull(result);
        Assert.Equal(payment.Id, result.Id);
    }

}