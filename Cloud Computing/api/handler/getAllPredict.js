const express = require('express')
const verifyToken = require('../../middleware/index')
const admin = require('firebase-admin')
const router = express.Router()
const {
    getAuth,
    createUserWithEmailAndPassword,
    signInWithEmailAndPassword,
    signOut,
    sendEmailVerification,
    sendPasswordResetEmail
} = require('../../config/firebase');


router.get("/", (req,res)=>{
    const uid  = admin.auth().getUser(uid)
    console.log(uid)
    res.status(201).json({
        status: 'succes',
        message: 'good',
        data: data
    })
    return res
})

module.exports = router