import React, { useEffect, useState } from "react";

const CartPage = () => {
  const [cart, setCart] = useState([]);

  useEffect(() => {
    const fetchCart = async () => {
      const response = await fetch("http://localhost:8080/api/cart");
      const data = await response.json();
      setCart(data.items);
    };

    fetchCart();
  }, []);

  return (
    <div>
      <h1>Your Cart</h1>
      <ul>
        {cart.map((item) => (
          <li key={item.id}>
            {item.productName} - Quantity: {item.quantity}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CartPage;
