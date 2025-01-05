import React from "react";
import { useCookies } from "react-cookie";

const AddToCartButton = ({ productId }) => {
  const [cookies] = useCookies(["user_id"]);

  const handleAddToCart = async () => {
    const userId = cookies.user_id;

    if (!userId) {
      alert("User not logged in.");
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/users/${userId}/cart/items`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ productId, quantity: 1 }),
      });

      if (!response.ok) {
        throw new Error("Failed to add product to cart.");
      }

      alert("Product added to cart!");
    } catch (error) {
      alert(error.message);
    }
  };

  return (
    <button onClick={handleAddToCart} className="btn btn-primary">
      Add to Cart
    </button>
  );
};

export default AddToCartButton;
