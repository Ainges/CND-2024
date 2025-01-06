import React, { useState, useEffect } from "react";
import { useCookies } from "react-cookie";
import { FaSpinner } from "react-icons/fa";

const OrderPage = () => {
  const [orders, setOrders] = useState([]);
  const [invoices, setInvoices] = useState([]);
  const [productNames, setProductNames] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [cookies] = useCookies(["user_id"]);

  useEffect(() => {
    const fetchOrdersAndInvoices = async () => {
      const userId = cookies.user_id;

      if (!userId) {
        alert("User not logged in.");
        return;
      }

      try {
        // Fetch orders
        const ordersResponse = await fetch(`http://localhost:8080/orders/user/${userId}`);
        if (!ordersResponse.ok) {
          throw new Error("Failed to fetch orders.");
        }

        const ordersData = await ordersResponse.json();
        setOrders(ordersData.orders);

        // Fetch invoices
        const invoicesResponse = await fetch("http://localhost:5172/api/invoice");
        if (!invoicesResponse.ok) {
          throw new Error("Failed to fetch invoices.");
        }

        const invoicesData = await invoicesResponse.json();
        setInvoices(invoicesData);

        // Resolve product names
        const productIds = new Set();
        ordersData.orders.forEach((order) => {
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

    fetchOrdersAndInvoices();
  }, [cookies]);

  const handlePayment = async (order) => {
    try {
      // Fetch all invoices
      const invoicesResponse = await fetch("http://localhost:5172/api/invoice");
      if (!invoicesResponse.ok) {
        throw new Error("Failed to fetch invoices.");
      }

      const invoicesData = await invoicesResponse.json();

      // Find the matching invoice for the order
      const matchingInvoice = invoicesData.find((invoice) => invoice.orderId === order.id);
      if (!matchingInvoice) {
        throw new Error("No matching invoice found for the selected order.");
      }

      // Make payment for the invoice
      const paymentResponse = await fetch("http://localhost:5172/api/payment", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          invoiceId: matchingInvoice.id,
          amount: matchingInvoice.totalAmountInEuroCents,
          paymentMethod: "CREDIT_CARD",
          transactionId: `txn-${Date.now()}`,
        }),
      });

      if (!paymentResponse.ok) {
        throw new Error("Payment failed.");
      }

      alert("Payment successful!");

      // Reload invoices and orders to fetch updated statuses
      const updatedInvoicesResponse = await fetch("http://localhost:5172/api/invoice");
      const updatedInvoices = await updatedInvoicesResponse.json();
      setInvoices(updatedInvoices);

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

  // Orders with unpaid invoices
  const unpaidOrders = orders.filter(
    (order) =>
      invoices.some((invoice) => invoice.orderId === order.id && invoice.paidAmount === 0)
  );

  // Invoices with paid orders
  const paidInvoices = invoices.filter((invoice) => invoice.paidAmount > 0);

  return (
    <div className="max-w-6xl mx-auto p-6">
      <h2 className="text-3xl font-bold text-n-1 mb-6">Your Orders</h2>
      {unpaidOrders.length > 0 ? (
        <div>
          {unpaidOrders.map((order) => (
            <div
              key={order.id}
              className="w-full p-6 bg-n-6 rounded-lg shadow-md mb-6"
            >
              <h3 className="text-xl font-bold text-n-1 mb-2">Order #{order.id}</h3>
              <ul className="list-disc pl-6 text-n-2 mt-2">
                {order.cart.cartItems.map((item, index) => (
                  <li key={index}>
                    {productNames[item.productId]} - Quantity: {item.quantity}
                  </li>
                ))}
              </ul>
              <button
                onClick={() => handlePayment(order)}
                className="mt-4 px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-700"
              >
                Pay Now
              </button>
            </div>
          ))}
        </div>
      ) : (
        <p className="text-n-2">No unpaid orders found.</p>
      )}

      <h2 className="text-3xl font-bold text-n-1 mb-6 mt-12">Your Invoices</h2>
      {paidInvoices.length > 0 ? (
        <div>
          {paidInvoices.map((invoice) => (
            <div
              key={invoice.id}
              className="w-full p-6 bg-n-6 rounded-lg shadow-md mb-6"
            >
              <h3 className="text-xl font-bold text-n-1 mb-2">
                Invoice #{invoice.id}
              </h3>
              <div className="mt-4">
                <p className="text-n-2">
                  <strong>Total Amount:</strong> €{invoice.totalAmountInEuroCents.toFixed(2)}
                </p>
                <p className="text-n-2">
                  <strong>Paid Amount:</strong> €{invoice.paidAmount.toFixed(2)}
                </p>
              </div>
              <p className="text-n-2 mb-2">
                <strong>Status:</strong> Paid
              </p>
            </div>
          ))}
        </div>
      ) : (
        <p className="text-n-2">No paid invoices found.</p>
      )}
    </div>
  );
};

export default OrderPage;
