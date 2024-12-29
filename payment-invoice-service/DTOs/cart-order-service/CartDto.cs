namespace payment_invoice_service.DTOs.cart_order_service;

public class CartDto
{
    public int Id { get; set; }
    public string UserId { get; set; }
    public List<CartItemDto> CartItemDtos { get; set; }
    public string Status { get; set; }
}
