import React, { useEffect, useState } from "react";
import { useCookies } from "react-cookie";
import { FaSpinner } from "react-icons/fa";

const CartPage = () => {
  const [cart, setCart] = useState(null); // Der gesamte Warenkorb
  const [cookies] = useCookies(["user_id"]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [products, setProducts] = useState({}); // Speichert Produktinformationen
  const [checkoutStatus, setCheckoutStatus] = useState(null);

  useEffect(() => {
    const fetchCart = async () => {
      const userId = cookies.user_id;

      if (!userId) {
        alert("User not logged in.");
        return;
      }

      try {
        const response = await fetch(`http://localhost:8080/carts/userid/${userId}/current`);
        if (!response.ok) {
          throw new Error("You have no card yet.");
        }
        const data = await response.json();
        setCart(data.cart); // Setzt den gesamten Warenkorb
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchCart();
  }, [cookies]);

  useEffect(() => {
    const fetchProductNames = async () => {
      if (!cart || !cart.cartItems) return;

      const productData = {};
      await Promise.all(
        cart.cartItems.map(async (item) => {
          try {
            const response = await fetch(`http://localhost:4000/products/${item.productId}`);
            if (!response.ok) {
              throw new Error(`Failed to fetch product with ID ${item.productId}`);
            }
            const product = await response.json();
            productData[item.productId] = product.name;
          } catch (err) {
            console.error(err.message);
          }
        })
      );

      setProducts(productData); // Speichert Produktnamen nach productId
    };

    if (cart) {
      fetchProductNames();
    }
  }, [cart]);

  const handleCheckout = async () => {
    const userId = cookies.user_id;

    if (!userId) {
      alert("User not logged in.");
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/users/${userId}/checkout`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        throw new Error("Checkout failed.");
      }

      setCheckoutStatus("Checkout successful!");
      setCart(null); // Clear the cart after successful checkout
    } catch (error) {
      setCheckoutStatus(`Error during checkout: ${error.message}`);
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <FaSpinner className="animate-spin text-4xl text-blue-500" />
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex items-center justify-center h-screen">
        <p className="text-red-500 text-xl">{error}</p>
      </div>
    );
  }

  return (
    <div className="max-w-6xl mx-auto p-6">
      <h2 className="text-3xl font-bold text-n-1 mb-6">Your Cart</h2>
      {/* Cart Details */}
      <div className="p-4 bg-n-6 rounded-lg shadow-md mb-6">
        <p className="text-lg font-bold text-n-1 mb-2">
          <strong>Order:</strong> {cart?.order || "No order associated"}
        </p>
        <p className="text-lg font-bold text-n-1">
          <strong>Status:</strong> {cart?.status}
        </p>
      </div>
      {/* Cart Items */}
      <div className="grid grid-cols-1 gap-6 md:grid-cols-2 lg:grid-cols-3">
        {cart?.cartItems.length > 0 ? (
          cart.cartItems.map((item) => (
            <div key={item.id} className="p-4 bg-n-6 rounded-lg shadow-md">
              <h3 className="text-xl font-bold text-n-1 mb-2">
                {products[item.productId] || "Loading product name..."}
              </h3>
              <p className="text-n-2 mb-4">Quantity: {item.quantity}</p>
            </div>
          ))
        ) : (
          <p className="text-n-2">Your cart is empty.</p>
        )}
      </div>
      {/* Checkout Button */}
      <div className="mt-6 flex justify-end">
        <button
          onClick={handleCheckout}
          className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition"
        >
          Checkout
        </button>
      </div>
      {/* Checkout Status */}
      {checkoutStatus && (
        <p className="mt-4 text-center text-lg font-semibold text-blue-500">
          {checkoutStatus}
        </p>
      )}
    </div>
  );
};

export default CartPage;
