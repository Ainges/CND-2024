import React from "react";
import RegisterForm from "./RegisterForm";
import LoginForm from "./LoginForm";

const AuthView = () => {
  return (
    <div className="min-h-screen flex items-center justify-center bg-n-8">
      <div className="w-full max-w-5xl p-8 rounded-lg shadow-lg grid grid-cols-1 md:grid-cols-2 gap-8">
        <div className="flex flex-col items-center bg-n-6 p-6 rounded-md shadow-md">
          <h2 className="text-2xl font-bold text-n-1 mb-4">Register</h2>
          <RegisterForm />
        </div>
        <div className="flex flex-col items-center bg-n-6 p-6 rounded-md shadow-md">
          <h2 className="text-2xl font-bold text-n-1 mb-4">Login</h2>
          <LoginForm />
        </div>
      </div>
    </div>
  );
};

export default AuthView;
