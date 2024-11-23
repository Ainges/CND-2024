using Microsoft.AspNetCore.Mvc;

namespace payment_invoice_service.Controller;

[ApiController]
[Route("api/checkout")]
public class CheckoutController : ControllerBase
{
    // Add service class

    public CheckoutController()
    {
    }

    [HttpGet()]
    public async Task<IActionResult> testEndpoint(string someString)
    {
        // write someString to body
        return Ok(someString);

    }
}