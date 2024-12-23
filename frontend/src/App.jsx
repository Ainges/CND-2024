import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import AuthView from "./components/AuthView";
import Dashboard from "./components/Dashboard";
import { useCookies } from "react-cookie";

const App = () => {
  const [cookies] = useCookies(["user_id"]);

  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={
            cookies.user_id ? <Navigate to="/dashboard" replace /> : <AuthView />
          }
        />
        <Route
          path="/dashboard"
          element={
            cookies.user_id ? (
              <Dashboard />
            ) : (
              <Navigate to="/" replace />
            )
          }
        />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
