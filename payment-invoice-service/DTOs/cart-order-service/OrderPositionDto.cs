namespace payment_invoice_service.DTOs.cart_order_service;

public class OrderPositionDto
{
    public int Id { get; set; }
    public int OrderPositionIndex { get; set; }
    public string ProductId { get; set; }
    public string ProductName { get; set; }
    public int Quantity { get; set; }
    public decimal PriceInEuroCents { get; set; }

}