using Microsoft.AspNetCore.Mvc;
using payment_invoice_service.DTOs;
using payment_invoice_service.Models;
using payment_invoice_service.Services;
using payment_invoice_service.Services.Exceptions;

namespace payment_invoice_service.Controllers;

[ApiController]
[Route("api/payment")]
public class PaymentController : ControllerBase
{
     private readonly PaymentService _paymentService;

    public PaymentController(PaymentService paymentService)
    {
        _paymentService = paymentService;
    }

    [HttpGet]
    public async Task<ActionResult<IEnumerable<PaymentDto>>> GetAllPayments()
    {
        var payments = await _paymentService.GetAllPaymentsAsync();

        var paymentDtos = new List<PaymentDto>();

        foreach (var payment in payments)
        {
            PaymentDto paymentDto = new PaymentDto
            {
                Id = payment.Id,
                InvoiceId = payment.Invoice.Id,
                Amount = payment.Amount,
                PaymentMethod = payment.PaymentMethod,
                TransactionId = payment.TransactionId,
            };
            paymentDtos.Add(paymentDto);
        }
        return Ok(paymentDtos);
    }

    [HttpGet("{id}")]
    public async Task<ActionResult<Payment>> GetPaymentById(int id)
    {
        try
        {
            var payment = await _paymentService.GetPaymentByIdAsync(id);
            return Ok(payment);
        }
        catch (PaymentServiceException e)
        {
            return NotFound(e.Message);
        }
    }

    [HttpPost]
    public async Task<ActionResult<Payment>> CreatePayment([FromBody] PaymentCreateDto paymentCreateDto)
    {
        try
        {
            var payment = await _paymentService.CreatePaymentAsync(paymentCreateDto);

            // create Dto of payment
            PaymentDto paymentDto = new PaymentDto
            {
                Id = payment.Id,
                InvoiceId = payment.Invoice.Id,
                Amount = payment.Amount,
                PaymentMethod = payment.PaymentMethod,
                TransactionId = payment.TransactionId,

            };


            return CreatedAtAction(nameof(GetPaymentById), new { id = payment.Id }, paymentDto);
        }
        catch (PaymentServiceException e)
        {
            return BadRequest(e.Message);
        }
    }
    // Currently not needed...
    // [HttpPut("{id}")]
    // public async Task<ActionResult<Payment>> UpdatePayment(int id, [FromBody] PaymentCreateDto paymentCreateDto)
    // {
    //     return BadRequest();
    // }
    //
    // [HttpDelete("{id}")]
    // public async Task<ActionResult<Payment>> DeletePayment(int id)
    // {
    //     try
    //     {
    //         var payment = await _paymentService.DeletePaymentAsync(id);
    //         return Ok(payment);
    //     }
    //     catch (PaymentServiceException e)
    //     {
    //         return NotFound(e.Message);
    //     }
    // }
}