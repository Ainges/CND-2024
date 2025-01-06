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
      const invoicesResponse = await fetch("http://localhost:5172/api/invoice");
      if (!invoicesResponse.ok) {
        throw new Error("Failed to fetch invoices.");
      }

      const invoicesData = await invoicesResponse.json();
      const matchingInvoice = invoicesData.find((invoice) => invoice.orderId === order.id);

      if (!matchingInvoice) {
        throw new Error("No matching invoice found for the selected order.");
      }

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

  const unpaidOrders = orders.filter(
    (order) =>
      invoices.some((invoice) => invoice.orderId === order.id && invoice.paidAmount === 0)
  );

  const paidInvoices = invoices.filter((invoice) => invoice.paidAmount > 0);

  return (
    <div className="max-w-6xl mx-auto p-6">
      {/* Orders Section */}
      <h2 className="text-3xl font-bold text-n-1 mb-6">Your Orders</h2>
      {unpaidOrders.length > 0 ? (
        <div className="space-y-6">
          {unpaidOrders.map((order) => (
            <div
              key={order.id}
              className="w-full p-6 bg-n-6 rounded-lg shadow-md"
            >
              <h3 className="text-xl font-bold text-n-1 mb-4">Order #{order.id}</h3>
              <div>
                <table className="w-full text-n-2 mb-4">
                  <thead>
                    <tr className="border-b">
                      <th className="py-2 text-left">Product</th>
                      <th className="py-2 text-left">Quantity</th>
                    </tr>
                  </thead>
                  <tbody>
                    {order.cart.cartItems.map((item, index) => (
                      <tr key={index} className="border-b">
                        <td className="py-2">{productNames[item.productId]}</td>
                        <td className="py-2">{item.quantity}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
              <button
                onClick={() => handlePayment(order)}
                className="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-700"
              >
                Pay Now
              </button>
            </div>
          ))}
        </div>
      ) : (
        <p className="text-n-2">No unpaid orders found.</p>
      )}

      {/* Invoice Section */}
      <h2 className="text-3xl font-bold text-n-1 mb-6 mt-12">Your Invoices</h2>
      {paidInvoices.length > 0 ? (
        <div className="space-y-6">
          {paidInvoices.map((invoice) => (
            <div
              key={invoice.id}
              className="w-full p-6 bg-n-6 rounded-lg shadow-md"
            >
              <h3 className="text-xl font-bold text-n-1 mb-4">
                Invoice #{invoice.id}
              </h3>
              <div className="border-t pt-4">
                <p className="text-n-2 mb-2">
                  <strong>Total Amount:</strong> €{invoice.totalAmountInEuroCents.toFixed(2)}
                </p>
                <p className="text-n-2 mb-2">
                  <strong>Paid Amount:</strong> €{invoice.paidAmount.toFixed(2)}
                </p>
                <p className="text-n-2 mb-2">
                  <strong>Status:</strong> Paid
                </p>
              </div>
              <h4 className="text-n-2 font-bold mt-4">Order Details:</h4>
              <ul className="list-disc pl-6 text-n-2 mt-2">
                {orders
                  .find((order) => order.id === invoice.orderId)
                  ?.cart.cartItems.map((item, index) => (
                    <li key={index}>
                      {productNames[item.productId]} - Quantity: {item.quantity}
                    </li>
                  ))}
              </ul>
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
