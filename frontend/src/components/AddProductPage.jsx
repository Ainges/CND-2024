import React, { useState } from "react";
import { FaSpinner } from "react-icons/fa";

const AddProductPage = () => {
  const [productData, setProductData] = useState({
    name: "",
    description: "",
    price: "",
  });
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [submitStatus, setSubmitStatus] = useState(null);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setProductData({ ...productData, [name]: value });
  };

  const handleSubmit = async () => {
    setIsSubmitting(true);
    setSubmitStatus(null);
    try {
      const response = await fetch("http://localhost:4000/products", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          name: productData.name,
          description: productData.description,
          price: parseFloat(productData.price),
        }),
      });

      if (!response.ok) {
        throw new Error("Failed to add product");
      }

      setSubmitStatus("Product added successfully.");
      setProductData({ name: "", description: "", price: "" });
    } catch (err) {
      setSubmitStatus(`Error: ${err.message}`);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="max-w-4xl mx-auto p-6 rounded-lg shadow-md mt-10">
      <h2 className="text-3xl font-bold text-n-1 mb-6">Add New Product</h2>
      <div className="grid grid-cols-1 gap-6">
        <div>
          <label className="block text-n-1 font-medium mb-2">Product Name</label>
          <input
            type="text"
            name="name"
            value={productData.name}
            onChange={handleInputChange}
            className="w-full p-2 border bg-n-6 text-n-1 border-gray-300 rounded"
          />
        </div>
        <div>
          <label className="block text-n-1 font-medium mb-2">Description</label>
          <textarea
            name="description"
            value={productData.description}
            onChange={handleInputChange}
            className="w-full p-2 border bg-n-6 text-n-1 border-gray-300 rounded"
          />
        </div>
        <div>
          <label className="block text-n-1 font-medium mb-2">Price (in €)</label>
          <input
            type="number"
            name="price"
            value={productData.price}
            onChange={handleInputChange}
            className="w-full p-2 border bg-n-6 text-n-1 border-gray-300 rounded"
          />
        </div>
      </div>

      <div className="mt-6 flex items-center">
        <button
          onClick={handleSubmit}
          disabled={isSubmitting}
          className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 flex items-center justify-center w-36"
        >
          {isSubmitting ? <FaSpinner className="animate-spin" /> : "Add Product"}
        </button>
        {submitStatus && <span className="ml-auto text-sm text-n-1">{submitStatus}</span>}
      </div>
    </div>
  );
};

export default AddProductPage;
