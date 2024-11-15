const axios = require('axios');

const verifyUser = async (req, res, next) => {
    const token = req.headers.authorization?.split(' ')[1];
    if (!token) {
        return res.status(401).json({ error: 'Unauthorized: No token provided' });
    }

    try {
        // Anfrage an den /verify Endpoint des User-Services
        const response = await axios.get('http://user-service:5000/verify', {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });

        // Die verifizierten Benutzerdaten werden dem Request-Objekt hinzugefügt
        req.user = response.data; // Enthält first_name, last_name, is_admin
        next();
    } catch (error) {
        // Fehlerbehandlung bei nicht erfolgreicher Verifizierung
        const status = error.response?.status || 500;
        const message = error.response?.data?.error || 'Verification failed';
        res.status(status).json({ error: message });
    }
};

const isAdmin = (req, res, next) => {
    if (!req.user || !req.user.is_admin) {
        return res.status(403).json({ error: 'Admin access required' });
    }
    next();
};

module.exports = { verifyUser, isAdmin };
