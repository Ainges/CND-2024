import React, { useState } from 'react';
import axios from 'axios';

function LoginForm() {
    const [formData, setFormData] = useState({
        email: '',
        password: '',
    });

    const [errorMessage, setErrorMessage] = useState('');

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
            const response = await axios.post('http://user-service:5000/login', formData);
            const token = response.data.access_token;

            // Speichere den Token im localStorage
            localStorage.setItem('access_token', token);
            console.log('Logged in successfully! Token:', token);

            // Optionale Weiterleitung
            // window.location.href = '/dashboard';
        } catch (error) {
            setErrorMessage('Invalid login credentials');
            console.error('Error logging in:', error.response?.data || error.message);
        }
    };

    return (
        <form className="space-y-4" onSubmit={handleSubmit}>
            <h2 className="text-2xl font-bold">Login</h2>
            {errorMessage && <p className="text-red-500">{errorMessage}</p>}
            <input
                className="w-full p-2 border rounded"
                type="email"
                name="email"
                placeholder="Email"
                value={formData.email}
                onChange={handleChange}
            />
            <input
                className="w-full p-2 border rounded"
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
