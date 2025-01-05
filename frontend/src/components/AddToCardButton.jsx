import React from "react";

const AddToCartButton = ({ productId }) => {
  const handleAddToCart = async () => {
    await fetch("http://localhost:8080/api/cart", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ productId, quantity: 1 }),
    });
    alert("Product added to cart!");
  };

  return (
    <button onClick={handleAddToCart} className="btn btn-primary">
      Add to Cart
    </button>
  );
};

export default AddToCartButton;
