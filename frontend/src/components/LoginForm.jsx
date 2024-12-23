import React, { useState } from "react";
import { useCookies } from "react-cookie";

function LoginForm() {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
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
      const response = await fetch("http://localhost:5000/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (!response.ok) {
        throw new Error("Invalid login credentials");
      }

      const { user_id } = await response.json();

      // Benutzer-ID speichern
      setCookie("user_id", user_id, { path: "/" });

      // Weiterleitung
      window.location.href = "/dashboard";
    } catch (error) {
      setErrorMessage(error.message || "Invalid login credentials");
      console.error("Error logging in:", error);
    }
  };

  return (
    <form className="space-y-4" onSubmit={handleSubmit}>
      {errorMessage && <p className="text-red-500">{errorMessage}</p>}
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
      <button className="w-full p-2 bg-green-500 text-white rounded" type="submit">
        Login
      </button>
    </form>
  );
}

export default LoginForm;
