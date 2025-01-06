import React, { useState, useEffect } from "react";
import { useCookies } from "react-cookie";
import { FaSpinner } from "react-icons/fa";

const OrderPage = () => {
  const [orders, setOrders] = useState([]);
  const [productNames, setProductNames] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [cookies] = useCookies(["user_id"]);

  useEffect(() => {
    const fetchOrders = async () => {
      const userId = cookies.user_id;

      if (!userId) {
        alert("User not logged in.");
        return;
      }

      try {
        const response = await fetch(`http://localhost:8080/orders/user/${userId}`);
        if (!response.ok) {
          throw new Error("Failed to fetch orders.");
        }

        const data = await response.json();
        setOrders(data.orders);

        // Resolve product names
        const productIds = new Set();
        data.orders.forEach((order) => {
          order.cart.cartItems.forEach((item) => productIds.add(item.productId));
          order.orderPosition.forEach((position) => productIds.add(position.productId));
        });

        const productData = {};
        await Promise.all(
          Array.from(productIds).map(async (productId) => {
            const productResponse = await fetch(`http://localhost:4000/products/${productId}`);
            const product = await productResponse.json();
            productData[productId] = product.name;
          })
        );

        setProductNames(productData);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchOrders();
  }, [cookies]);

  const handlePayment = async (order) => {
    try {
      // Fetch the associated invoice
      const invoiceResponse = await fetch("http://localhost:5172/api/invoice");
      if (!invoiceResponse.ok) {
        throw new Error("Failed to fetch invoices.");
      }

      const invoices = await invoiceResponse.json();
      const matchingInvoice = invoices.find((invoice) => invoice.orderId === order.id);

      if (!matchingInvoice) {
        throw new Error("No matching invoice found for the selected order.");
      }

      // Make payment for the invoice
      const paymentResponse = await fetch("http://localhost:5172/api/payment", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          invoiceId: matchingInvoice.id,
          amount: matchingInvoice.totalAmountInEuroCents / 100, // Convert cents to euros
          paymentMethod: "CREDIT_CARD", // Example method
          transactionId: `txn-${Date.now()}`, // Generate a mock transaction ID
        }),
      });

      if (!paymentResponse.ok) {
        throw new Error("Payment failed.");
      }

      alert("Payment successful!");

      // Reload orders to fetch updated statuses
      const userId = cookies.user_id;
      const updatedOrdersResponse = await fetch(
        `http://localhost:8080/orders/user/${userId}`
      );
      const updatedOrders = await updatedOrdersResponse.json();
      setOrders(updatedOrders.orders);
    } catch (error) {
      alert(error.message);
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
      <h2 className="text-3xl font-bold text-n-1 mb-6">Your Orders</h2>
      {orders.length > 0 ? (
        <div>
          {orders.map((order) => (
            <div
              key={order.id}
              className="w-full p-6 bg-n-6 rounded-lg shadow-md mb-6"
            >
              <h3 className="text-xl font-bold text-n-1 mb-2">Order #{order.id}</h3>
              <p className="text-n-2 mb-2"><strong>Status:</strong> {order.status}</p>
              <p className="text-n-2 mb-2"><strong>User ID:</strong> {order.userId}</p>
              <h4 className="text-n-2 font-bold mt-4">Cart Details:</h4>
              <p className="text-n-2"><strong>Status:</strong> {order.cart.status}</p>
              <ul className="list-disc pl-6 text-n-2 mt-2">
                {order.cart.cartItems.map((item, index) => (
                  <li key={index}>
                    {productNames[item.productId] || "Loading..."} - Quantity: {item.quantity}
                  </li>
                ))}
              </ul>
              <h4 className="text-n-2 font-bold mt-4">Order Positions:</h4>
              <ul className="list-disc pl-6 text-n-2 mt-2">
                {order.orderPosition.map((position, index) => (
                  <li key={index}>
                    {productNames[position.productId] || "Loading..."} - Quantity: {position.quantity}
                  </li>
                ))}
              </ul>
              {order.status === "WAITING_FOR_PAYMENT" && (
                <button
                  onClick={() => handlePayment(order)}
                  className="mt-4 px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-700"
                >
                  Pay Now
                </button>
              )}
            </div>
          ))}
        </div>
      ) : (
        <p className="text-n-2">No orders found.</p>
      )}
    </div>
  );
};

export default OrderPage;
