using Microsoft.AspNetCore.Mvc;
using payment_invoice_service.DTOs;
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

    [HttpGet()]
    public async Task<IActionResult> testEndpoint()
    {

        return Ok(await _paymentService.GetAllPaymentsAsync());

    }

    [HttpGet("{id}")]
    public async Task<IActionResult> testEndpoint(int id)
    {
        try
        {
            return Ok(await _paymentService.GetPaymentByIdAsync(id));
        }
        catch(PaymentServiceException e)
        {
            return NotFound(e.Message);
        }
    }

    [HttpPost()]
    public async Task<IActionResult> testEndpoint([FromBody] PaymentCreateDto paymentCreateDto)
    {
        try
        {
            return Ok(await _paymentService.CreatePaymentAsync(paymentCreateDto));
        }
        catch(PaymentServiceException e)
        {
            return BadRequest(e.Message);
        }
    }
}