// file: src/middleware/index.js

const { admin } = require("../config/firebase");

const verifyToken = async (req, res, next) => {
    const authHeader = req.headers.authorization;
    const idToken = authHeader.split('Bearer ')[1];
    console.log(idToken)
    if (!idToken) {
        return res.status(403).json({ error: 'No token provided' });
    }

    try {
        const decodedToken = await admin.auth().verifyIdToken(idToken); 
        req.user = decodedToken;
        next();
    } catch (error) {
        console.error('Error verifying token:', error);
        return res.status(403).json({ error: 'Unauthorized' });
    }
};

module.exports = verifyToken;
