using System.Text.Json.Serialization;
using Newtonsoft.Json;

namespace payment_invoice_service.DTOs.cart_order_service
{
    public class CartItemDto
    {
        [JsonProperty("id")]
        public int Id { get; set; }

        [JsonProperty("productId")]
        public string ProductId { get; set; }

        [JsonProperty("quantity")]
        public int Quantity { get; set; }
    }

    public class CartDto
    {
        [JsonProperty("id")]
        public int Id { get; set; }

        [JsonProperty("userId")]
        public string UserId { get; set; }

        [JsonProperty("cartItems")]
        public List<CartItemDto> CartItems { get; set; }

        [JsonProperty("status")]
        public string Status { get; set; }

        [JsonProperty("order")]
        public OrderDto Order { get; set; }
    }

    public class OrderPositionDto
    {
        [JsonProperty("id")]
        public int Id { get; set; }

        [JsonProperty("orderPosition")]
        public int OrderPosition { get; set; }

        [JsonProperty("productId")]
        public string ProductId { get; set; }

        [JsonProperty("productName")]
        public string ProductName { get; set; }

        [JsonProperty("quantity")]
        public int Quantity { get; set; }

        [JsonProperty("priceInEuroCents")]
        public decimal PriceInEuroCents { get; set; }
    }

    public class OrderDto
    {
        [JsonProperty("id")]
        public int Id { get; set; }

        [JsonProperty("userId")]
        public string UserId { get; set; }

        [JsonProperty("cart")]
        public CartDto Cart { get; set; }

        [JsonProperty("status")]
        public string Status { get; set; }

        [JsonProperty("orderPosition")]
        public List<OrderPositionDto> OrderPosition { get; set; }
    }
}