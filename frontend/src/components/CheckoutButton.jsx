import React from "react";

const CheckoutButton = () => {
  const handleCheckout = async () => {
    const response = await fetch("http://localhost:8080/api/cart/checkout", {
      method: "POST",
    });
    if (response.ok) {
      alert("Checkout successful! Order created.");
    } else {
      alert("Checkout failed. Please try again.");
    }
  };

  return (
    <button onClick={handleCheckout} className="btn btn-success">
      Checkout
    </button>
  );
};

export default CheckoutButton;
