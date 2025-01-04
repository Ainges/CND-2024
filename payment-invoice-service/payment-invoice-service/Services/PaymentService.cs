using payment_invoice_service.Data.Repositories;
using payment_invoice_service.DTOs;
using payment_invoice_service.Models;
using payment_invoice_service.Services.Exceptions;

namespace payment_invoice_service.Services;

public class PaymentService
{
    private readonly IPaymentRepository _paymentRepository;
    private readonly IInvoiceRepository _invoiceRepository;

    public PaymentService(IPaymentRepository paymentRepository, IInvoiceRepository invoiceRepository)
    {
        _paymentRepository = paymentRepository;
        _invoiceRepository = invoiceRepository;
    }

    public async Task<IEnumerable<Payment>> GetAllPaymentsAsync()
    {
        IEnumerable<Payment> payments = await _paymentRepository.GetAllAsync();

        return payments;
    }

    public async Task<Payment> GetPaymentByIdAsync(int id)
    {
        var payment = await _paymentRepository.GetByIdAsync(id);
        if (payment == null)
        {
            throw new PaymentServiceException($"Payment with id {id} not found.");
        }
        return payment;
    }

    public async Task<Payment> CreatePaymentAsync(PaymentCreateDto paymentCreateDto)
    {

        Invoice invoice = await _invoiceRepository.GetByIdAsync(paymentCreateDto.InvoiceId);

        Payment payment = new Payment();

            payment.Invoice = invoice;
            payment.Amount = paymentCreateDto.Amount;
            payment.PaymentMethod = paymentCreateDto.PaymentMethod;
            payment.TransactionId = paymentCreateDto.TransactionId;
            payment.Invoice = invoice;


        // Update invoice paid amount
        invoice.PaidAmount += payment.Amount;

        if(invoice.Payments == null)
        {
            invoice.Payments = new List<Payment>();
        }

        invoice.Payments.Add(payment);

        if(invoice.PaidAmount >= invoice.TotalAmountInEuroCents)
        {
            invoice.Status = InvoiceStatus.PAID;
        }

        payment = await _paymentRepository.CreateAsync(payment);
        await _invoiceRepository.UpdateAsync(invoice);




        return payment;
    }

    public async Task<Payment> UpdatePaymentAsync(Payment payment)
    {
        var existingPayment = await _paymentRepository.GetByIdAsync(payment.Id);
        if (existingPayment == null)
        {
            throw new PaymentServiceException($"Payment with id {payment.Id} not found.");
        }
        return await _paymentRepository.UpdateAsync(payment);
    }

    public async Task<Payment> DeletePaymentAsync(int id)
    {
        var payment = await _paymentRepository.GetByIdAsync(id);
        if (payment == null)
        {
            throw new PaymentServiceException($"Payment with id {id} not found.");
        }
        return await _paymentRepository.DeleteAsync(id);
    }
}