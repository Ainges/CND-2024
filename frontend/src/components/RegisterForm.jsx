import React, { useState } from "react";
import { useCookies } from "react-cookie";

function RegisterForm() {
  const [formData, setFormData] = useState({
    first_name: "",
    last_name: "",
    email: "",
    password: "",
    role: "User",
  });

  const [errorMessage, setErrorMessage] = useState("");
  const [, setCookie] = useCookies(["user_id"]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:5000/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.error || "Registration failed");
      }

      const { user_id } = await response.json();
      setCookie("user_id", user_id, { path: "/" });

      // Weiterleitung
      window.location.href = "/dashboard";
    } catch (error) {
      setErrorMessage(`Registration failed: ${error.message}`);
      console.error("Error during registration:", error);
    }
  };

  return (
    <form className="space-y-4" onSubmit={handleSubmit}>
      {errorMessage && <p className="text-red-500">{errorMessage}</p>}
      <input
        className="w-full p-2 border rounded text-neutral-900"
        type="text"
        name="first_name"
        placeholder="First Name"
        value={formData.first_name}
        onChange={handleChange}
      />
      <input
        className="w-full p-2 border rounded text-neutral-900"
        type="text"
        name="last_name"
        placeholder="Last Name"
        value={formData.last_name}
        onChange={handleChange}
      />
      <input
        className="w-full p-2 border rounded text-neutral-900"
        type="email"
        name="email"
        placeholder="Email"
        value={formData.email}
        onChange={handleChange}
      />
      <input
        className="w-full p-2 border rounded text-neutral-900"
        type="password"
        name="password"
        placeholder="Password"
        value={formData.password}
        onChange={handleChange}
      />
      <label className="flex items-center space-x-2">
        <span className="text-neutral-100">Role:</span>
        <select
          name="role"
          value={formData.role}
          onChange={handleChange}
          className="w-full p-2 border rounded text-neutral-900"
        >
          <option value="User">User</option>
          <option value="Admin">Admin</option>
        </select>
      </label>
      <button className="w-full p-2 bg-blue-500 text-white rounded" type="submit">
        Register
      </button>
    </form>
  );
}

export default RegisterForm;
