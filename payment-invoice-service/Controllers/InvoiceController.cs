using Microsoft.AspNetCore.Mvc;
using payment_invoice_service.Models;
using payment_invoice_service.Services;
using payment_invoice_service.Services.Exceptions;
using System.Collections.Generic;
using System.Threading.Tasks;

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
            return Ok(invoices);
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