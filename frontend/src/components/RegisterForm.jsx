import React, { useState } from 'react';
import axios from 'axios';

function RegisterForm() {
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        isAdmin: false,
    });

    const [errorMessage, setErrorMessage] = useState('');

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setFormData({
            ...formData,
            [name]: type === 'checkbox' ? checked : value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            // Registriere den Benutzer
            await axios.post('http://user-service:5000/register', formData);
            console.log('User registered successfully!');

            // Logge den Benutzer direkt ein
            const loginResponse = await axios.post('http://user-service:5000/login', {
                email: formData.email,
                password: formData.password,
            });
            const token = loginResponse.data.access_token;

            // Speichere den Token im localStorage
            localStorage.setItem('access_token', token);
            console.log('Logged in successfully! Token:', token);

            // Optionale Weiterleitung
            // window.location.href = '/dashboard';
        } catch (error) {
            setErrorMessage('Registration or login failed: ', error.response?.data || error.message);
            console.error('Error during registration/login:', error.response?.data || error.message);
        }
    };

    return (
        <form className="space-y-4" onSubmit={handleSubmit}>
            <h2 className="text-2xl font-bold">Register</h2>
            {errorMessage && <p className="text-red-500">{errorMessage}</p>}
            <input
                className="w-full p-2 border rounded"
                type="text"
                name="firstName"
                placeholder="First Name"
                value={formData.firstName}
                onChange={handleChange}
            />
            <input
                className="w-full p-2 border rounded"
                type="text"
                name="lastName"
                placeholder="Last Name"
                value={formData.lastName}
                onChange={handleChange}
            />
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
            <label className="flex items-center space-x-2">
                <input
                    type="checkbox"
                    name="isAdmin"
                    checked={formData.isAdmin}
                    onChange={handleChange}
                />
                <span>Is Admin</span>
            </label>
            <button className="w-full p-2 bg-blue-500 text-white rounded" type="submit">
                Register
            </button>
        </form>
    );
}

export default RegisterForm;
