using System.Runtime.InteropServices.JavaScript;
using Newtonsoft.Json;
using payment_invoice_service.DTOs.cart_order_service;
using payment_invoice_service.Models;
using payment_invoice_service.Services.Exceptions;
using JsonException = System.Text.Json.JsonException;

namespace payment_invoice_service.Services;

public class InvoiceService
{
    private readonly IInvoiceRepository _invoiceRepository;

    public InvoiceService(IInvoiceRepository invoiceRepository)
    {
        _invoiceRepository = invoiceRepository;
    }

    public async Task<IEnumerable<Invoice>> GetAllInvoicesAsync()
    {
        return await _invoiceRepository.GetAllAsync();
    }

    public async Task<Invoice> GetInvoiceByIdAsync(int id)
    {
        var invoice = await _invoiceRepository.GetByIdAsync(id);
        if (invoice == null)
        {
            throw new InvoiceServiceException($"Invoice with id {id} not found.");
        }
        return invoice;
    }

    public async Task<Invoice> CreateInvoiceAsync(Invoice invoice)
    {
        return await _invoiceRepository.CreateAsync(invoice);
    }

    public async Task<Invoice> UpdateInvoiceAsync(Invoice invoice)
    {
        var existingInvoice = await _invoiceRepository.GetByIdAsync(invoice.Id);
        if (existingInvoice == null)
        {
            throw new InvoiceServiceException($"Invoice with id {invoice.Id} not found.");
        }
        return await _invoiceRepository.UpdateAsync(invoice);
    }

    public async Task<Invoice> DeleteInvoiceAsync(int id)
    {
        var invoice = await _invoiceRepository.GetByIdAsync(id);
        if (invoice == null)
        {
            throw new InvoiceServiceException($"Invoice with id {id} not found.");
        }
        return await _invoiceRepository.DeleteAsync(id);
    }

    public async Task<Invoice> CreateInvoiceFromQueue(string json)
    {

        var order = new OrderDto();
        try
        {
            order = JsonConvert.DeserializeObject<OrderDto>(json);
            Console.WriteLine("Deserialisierung erfolgreich.");
        }
        catch (JsonException ex)
        {
            Console.WriteLine($"Fehler bei der Deserialisierung: {ex.Message}");
        }

        var daysToPay = 14;

        var invoice = new Invoice
        {
            Id = 0,
            OrderId = order.Id,
            UserId = order.UserId,
            TotalAmountInEuroCents = order.OrderPosition.Sum(op => op.PriceInEuroCents * op.Quantity),
            PaidAmount = 0,
            Status = InvoiceStatus.PENDING,
            IssueDateAsString = DateTime.Now.ToString("dd.MM.yyyy - HH:mm:ss"),
            DueDateAsString = DateTime.Now.AddDays(daysToPay).ToString("dd.MM.yyyy - HH:mm:ss"),
            CreatedDateAsString = DateTime.Now.ToString("dd.MM.yyyy - HH:mm:ss"),
            Payments = new List<Payment>()
        };

        await _invoiceRepository.CreateAsync(invoice);
        return invoice;
    }
}