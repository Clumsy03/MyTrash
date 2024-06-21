const express = require('express')
const router = express.Router()
const  postPredictHandler  = require('../handler/predict')
const  showall  = require('../handler/getAllPredict')
var bodyParser = require('body-parser')
const multer = require('multer')
const FirebaseAuthController = require('../../controllers/firebase-auth-controllers')
const verifyToken = require('../../middleware/index')
const app = require('../../app')
require('dotenv').config();



const upload = multer({
    storage: multer.memoryStorage(),
    limits: { filesize: 3 * 1024 *1024 }
})

function multerErrorHandler(err, req, res, next) {
    if (err instanceof multer.MulterError) {
      if (err.code === 'LIMIT_FILE_SIZE') {
        return res.status(413).send('File terlalu besar. Batas maksimal adalah 1MB.');
      }
    }
    next(err);
  }

router.get('/', (req,rest,next) => {
    res.status(200).json({
        message: 'success'
    })
})

router.get('/predict', (req,res,next) =>{
    res.status(200).json({
        message: 'success'
    })
})

router.use('/history',showall)

router.post('/register', FirebaseAuthController.registerUser);
router.post('/login', FirebaseAuthController.loginUser);
router.post('/logout', FirebaseAuthController.logoutUser);
router.post('/reset-password', FirebaseAuthController.resetPassword);


router.use('/predict',verifyToken,upload.single('image'), multerErrorHandler,postPredictHandler)


module.exports = router