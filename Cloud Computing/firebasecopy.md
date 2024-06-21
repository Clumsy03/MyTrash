// file: src/config/firebase.js 
// change the name file to firebase.js
const admin = require('firebase-admin');
const serviceAccount = require("../FirebaseService.json");
require("dotenv").config();
const firebase = require("firebase/app");

const firebaseConfig = {
    apiKey: "",
    authDomain: "",
    projectId: "",
    storageBucket: "",
    messagingSenderId: "",
    appId: "",
    measurementId: ""
  };

firebase.initializeApp(firebaseConfig);


const {
    getAuth,
    createUserWithEmailAndPassword,
    signInWithEmailAndPassword,
    signOut,
    sendEmailVerification,
    sendPasswordResetEmail
} = require("firebase/auth");

module.exports = {
    getAuth,
    signInWithEmailAndPassword,
    createUserWithEmailAndPassword,
    signOut,
    sendEmailVerification,
    sendPasswordResetEmail,
    admin
};



admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
});

module.exports = {
    getAuth,
    createUserWithEmailAndPassword,
    signInWithEmailAndPassword,
    signOut,
    sendEmailVerification,
    sendPasswordResetEmail,
    admin
};
