import React, { useState } from "react";
import { useCookies } from "react-cookie";
import { FaSpinner } from "react-icons/fa";

const ProfilePage = ({ user }) => {
  const [userData, setUserData] = useState(user);
  const [updateStatus, setUpdateStatus] = useState(null);
  const [isUpdating, setIsUpdating] = useState(false);
  const [isDeleting, setIsDeleting] = useState(false);
  const [cookies, setCookie, removeCookie] = useCookies(["user_id"]);

  const handleUpdate = async () => {
    setIsUpdating(true);
    setUpdateStatus(null);
    try {
      const userId = cookies.user_id;
      const response = await fetch(`http://localhost:5000/users/${userId}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          new_email: userData.email,
          new_password: "", // Leave empty for now
          new_first_name: userData.first_name,
          new_last_name: userData.last_name,
        }),
      });

      if (!response.ok) {
        if (response.status === 400) handleLogout();
        throw new Error("Failed to update profile");
      }

      setUpdateStatus("Profile updated successfully.");
    } catch (err) {
      setUpdateStatus(`Error: ${err.message}`);
    } finally {
      setIsUpdating(false);
    }
  };

  const handleDelete = async () => {
    setIsDeleting(true);
    try {
      const userId = cookies.user_id;
      const response = await fetch(`http://localhost:5000/users/${userId}`, {
        method: "DELETE",
      });

      if (!response.ok) {
        if (response.status === 400) handleLogout();
        throw new Error("Failed to delete account");
      }

      removeCookie("user_id", { path: "/" });
      window.location.href = "/";
    } catch (err) {
      alert(`Error: ${err.message}`);
    } finally {
      setIsDeleting(false);
    }
  };

  const handleLogout = () => {
    removeCookie("user_id", { path: "/" });
    window.location.href = "/";
  };

  return (
    <div className="max-w-4xl mx-auto p-6 rounded-lg shadow-md mt-10">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-3xl font-bold text-n-1">Profile Information</h2>
        <button
          onClick={handleLogout}
          className="bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-600"
        >
          Logout
        </button>
      </div>

      <div className="grid grid-cols-1 gap-6 md:grid-cols-2">
        <div>
          <label className="block text-n-1 font-medium mb-2">First Name</label>
          <input
            type="text"
            value={userData.first_name}
            onChange={(e) => setUserData({ ...userData, first_name: e.target.value })}
            className="w-full p-2 border bg-n-6 text-n-1 border-gray-300 rounded"
          />
        </div>
        <div>
          <label className="block text-n-1 font-medium mb-2">Last Name</label>
          <input
            type="text"
            value={userData.last_name}
            onChange={(e) => setUserData({ ...userData, last_name: e.target.value })}
            className="w-full p-2 border bg-n-6 text-n-1 border-gray-300 rounded"
          />
        </div>
        <div>
          <label className="block text-n-1 font-medium mb-2">Email</label>
          <input
            type="email"
            value={userData.email}
            onChange={(e) => setUserData({ ...userData, email: e.target.value })}
            className="w-full p-2 border bg-n-6 text-n-1 border-gray-300 rounded"
          />
        </div>
        <div>
          <label className="block text-n-1 font-medium mb-2">Role</label>
          <input
            type="text"
            value={userData.role}
            readOnly
            className="w-full p-2 border bg-n-5 text-n-1 border-gray-300 rounded"
            disabled
          />
        </div>
      </div>

      <div className="mt-6 flex items-center">
        <button
          onClick={handleUpdate}
          className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 flex items-center justify-center w-36"
        >
          {isUpdating ? <FaSpinner className="animate-spin" /> : "Update Profile"}
        </button>
        {updateStatus && <span className="ml-auto text-sm text-n-1">{updateStatus}</span>}
      </div>

      <div className="mt-6 flex items-center">
        <button
          onClick={handleDelete}
          className="bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-600 flex items-center justify-center w-36"
        >
          {isDeleting ? <FaSpinner className="animate-spin" /> : "Delete Account"}
        </button>
      </div>
    </div>
  );
};

export default ProfilePage;
