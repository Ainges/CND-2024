import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useCookies } from "react-cookie";
import ProfilePage from "./ProfilePage";
import AddProductPage from "./AddProductPage";
import AllProductsPage from "./AllProductsPage";

const Dashboard = () => {
  const [user, setUser] = useState(null);
  const [activeTab, setActiveTab] = useState("profile");
  const navigate = useNavigate();
  const [cookies, setCookie] = useCookies(["user_id"]);

  useEffect(() => {
    const userId = cookies.user_id;
    if (!userId) {
      navigate("/");
      return;
    }

    fetch(`http://localhost:5000/users/${userId}`)
      .then((response) => response.json())
      .then((data) => setUser(data))
      .catch((error) => console.error("Error fetching user data:", error));
  }, [cookies, navigate]);

  const handleTabChange = (tab) => {
    setActiveTab(tab);
  };

  const renderTabContent = () => {
    switch (activeTab) {
      case "profile":
        return (
          <ProfilePage user={user} />
        );
      case "all-products":
        return <AllProductsPage />;
      case "add-product":
        return <AddProductPage />;
      default:
        return <h2 className="text-2xl font-bold">Welcome to the Dashboard</h2>;
    }
  };

  return (
    <div className="flex flex-col lg:flex-row min-h-screen bg-n-8">
      <div className="hidden lg:block lg:w-1/4 p-6 rounded-l-lg flex flex-col items-center border-r-2 border-n-6">
        <h3 className="text-lg font-semibold text-n-1 mb-6">Dashboard</h3>
        <ul className="w-full">
          <li
            className={`py-3 px-4 mb-2 text-n-1 rounded-lg cursor-pointer transition ${activeTab === "profile" ? "bg-n-6" : "hover:bg-n-6"
              }`}
            onClick={() => handleTabChange("profile")}
          >
            Profile
          </li>
          <li
            className={`py-3 px-4 mb-2 text-n-1 rounded-lg cursor-pointer transition ${activeTab === "all-products" ? "bg-n-6" : "hover:bg-n-6"
              }`}
            onClick={() => handleTabChange("all-products")}
          >
            All Products
          </li>
          {user?.role === "Admin" && (
            <li
              className={`py-3 px-4 mb-2 text-n-1 rounded-lg cursor-pointer transition ${activeTab === "add-product" ? "bg-n-6" : "hover:bg-n-6"
                }`}
              onClick={() => handleTabChange("add-product")}
            >
              Add New Product
            </li>
          )}
        </ul>
      </div>

      <div className="w-full lg:w-3/4 bg-n-8 rounded-r-lg p-8">
        {user ? renderTabContent() : <p>Loading user data...</p>}
      </div>
    </div>
  );
};

export default Dashboard;
