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
      <h2 className="text-3xl font-bold text-n-1 mb-6">Your Order</h2>
      {orders.length > 0 ? (
        <div className="flex flex-col items-center">
          {orders.map((order) => (
            <div
              key={order.id}
              className="w-full lg:w-3/4 p-6 bg-n-6 rounded-lg shadow-md mb-6"
            >
              <p className="text-n-2 mb-2"><strong>Status:</strong> {order.status}</p>
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
