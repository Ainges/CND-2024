using Microsoft.AspNetCore.Mvc;
using payment_invoice_service.Models;
using payment_invoice_service.Services;
using payment_invoice_service.Services.Exceptions;
using System.Collections.Generic;
using System.Threading.Tasks;
using payment_invoice_service.DTOs;

namespace payment_invoice_service.Controllers
{
    [ApiController]
    [Route("api/invoice")]
    public class InvoiceController : ControllerBase
    {
        private readonly InvoiceService _invoiceService;

        public InvoiceController(InvoiceService invoiceService)
        {
            _invoiceService = invoiceService;
        }

        [HttpGet]
        public async Task<IActionResult> GetAllInvoices()
        {
            var invoices = await _invoiceService.GetAllInvoicesAsync();
            var invoiceDtos = new List<InvoiceDto>();

            foreach (var invoice in invoices)
            {
                InvoiceDto invoiceDto = new InvoiceDto
                {
                    Id = invoice.Id,
                    UserId = invoice.UserId,
                    TotalAmountInEuroCents = invoice.TotalAmountInEuroCents,
                    PaidAmount = invoice.PaidAmount,
                    OrderId = invoice.OrderId,
                    Status = invoice.Status,
                    IssueDateAsString = invoice.IssueDateAsString,
                    DueDateAsString = invoice.DueDateAsString,
                    CreatedDateAsString = invoice.CreatedDateAsString,
                    UpdatedDate = invoice.UpdatedDate,
                    PaymentIds = invoice.Payments.Select(p => p.Id).ToList()
                };
                invoiceDtos.Add(invoiceDto);
            }

            return Ok(invoiceDtos);
        }

        [HttpGet("{id}")]
        public async Task<IActionResult> GetInvoiceById(int id)
        {
            try
            {
                var invoice = await _invoiceService.GetInvoiceByIdAsync(id);
                return Ok(invoice);
            }
            catch (InvoiceServiceException e)
            {
                return NotFound(e.Message);
            }
        }

        [HttpPost]
        public async Task<IActionResult> CreateInvoice([FromBody] Invoice invoice)
        {
            var createdInvoice = await _invoiceService.CreateInvoiceAsync(invoice);
            return CreatedAtAction(nameof(GetInvoiceById), new { id = createdInvoice.Id }, createdInvoice);
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> UpdateInvoice(int id, [FromBody] Invoice invoice)
        {
            if (id != invoice.Id)
            {
                return BadRequest("Invoice ID mismatch");
            }

            try
            {
                var updatedInvoice = await _invoiceService.UpdateInvoiceAsync(invoice);
                return Ok(updatedInvoice);
            }
            catch (InvoiceServiceException e)
            {
                return NotFound(e.Message);
            }
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteInvoice(int id)
        {
            try
            {
                var deletedInvoice = await _invoiceService.DeleteInvoiceAsync(id);
                return Ok(deletedInvoice);
            }
            catch (InvoiceServiceException e)
            {
                return NotFound(e.Message);
            }
        }
    }

}