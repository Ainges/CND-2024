namespace payment_invoice_service.DTOs.cart_order_service;

public class OrderDto
{
    public int Id { get; set; }
    public string UserId { get; set; }
    public CartDto CartDto { get; set; }
    public string Status { get; set; }
    public List<OrderPositionDto> OrderPositionDto { get; set; }

}