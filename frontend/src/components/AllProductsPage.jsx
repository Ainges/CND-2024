import React, { useState, useEffect } from "react";
import { FaSpinner, FaShoppingCart } from "react-icons/fa";
import { useCookies } from "react-cookie";

const AllProductsPage = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [loadingProductId, setLoadingProductId] = useState(null);
  const [cookies] = useCookies(["user_id"]);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await fetch("http://localhost:4000/products");
        if (!response.ok) {
          throw new Error("Failed to fetch products");
        }

        const data = await response.json();
        setProducts(data);
        setLoading(false);
      } catch (err) {
        setError(err.message);
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  const handleAddToCart = async (productId) => {
    const userId = cookies.user_id;

    if (!userId) {
      alert("User not logged in.");
      return;
    }

    setLoadingProductId(productId); // Show spinner for the product being added
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
    } finally {
      setLoadingProductId(null); // Remove spinner
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
      <h2 className="text-3xl font-bold text-n-1 mb-6">All Products</h2>
      <div className="grid grid-cols-1 gap-6 md:grid-cols-2 lg:grid-cols-3">
        {products.map((product) => (
          <div key={product.id} className="p-4 bg-n-6 rounded-lg shadow-md">
            <h3 className="text-xl font-bold text-n-1 mb-2">{product.name}</h3>
            <p className="text-n-2 mb-4">{product.description}</p>
            <div className="flex items-center justify-between">
              <span className="text-lg font-semibold text-blue-500">
                {typeof product.price === "number" && !isNaN(product.price)
                  ? `$${product.price.toFixed(2)}`
                  : "N/A"}
              </span>
              <button
                onClick={() => handleAddToCart(product.id)}
                className="text-blue-500 hover:text-blue-700 flex items-center"
                disabled={loadingProductId === product.id}
              >
                {loadingProductId === product.id ? (
                  <FaSpinner className="animate-spin" />
                ) : (
                  <FaShoppingCart />
                )}
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default AllProductsPage;
