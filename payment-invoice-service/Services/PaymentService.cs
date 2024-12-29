using payment_invoice_service.Data.Repositories;
using payment_invoice_service.DTOs;
using payment_invoice_service.Models;
using payment_invoice_service.Services.Exceptions;

namespace payment_invoice_service.Services;

public class PaymentService
{
    // private readonly IPaymentRepository _paymentRepository;
    //
    // public PaymentService(IPaymentRepository paymentRepository)
    // {
    //     _paymentRepository = paymentRepository;
    // }
    //
    // public async Task<IEnumerable<Payment>> GetAllPaymentsAsync()
    // {
    //      return await _paymentRepository.GetAllAsync();
    // }
    //
    // public async Task<Payment> GetPaymentByIdAsync(int id)
    // {
    //     try
    //     {
    //     return await _paymentRepository.GetByIdAsync(id);
    //     }
    //     catch(PaymentRepositoryException e)
    //     {
    //         throw new PaymentServiceException("Could not retrieve payment with id: " + id);
    //     }
    // }
    //
    // public async Task<Payment> CreatePaymentAsync(PaymentCreateDto paymentCreateDto)
    // {
    //
    //     Payment payment = new Payment
    //     {
    //         OrderId = paymentCreateDto.orderId,
    //         Provider = paymentCreateDto.provider,
    //         Amount = paymentCreateDto.amount,
    //         Currency = paymentCreateDto.currency
    //     };
    //
    //     payment.Status = InvoiceStatus.PENDING;
    //     payment.TransactionId = null;
    //     payment.Date = DateTime.UtcNow;
    //
    //     try
    //     {
    //         return await _paymentRepository.CreateAsync(payment);
    //     }
    //     catch(PaymentRepositoryException e)
    //     {
    //         throw new PaymentServiceException("Could not create payment");
    //     }
    // }


}