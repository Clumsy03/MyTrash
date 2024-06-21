const express = require('express')
const router = express.Router()
const  postPredictHandler  = require('../handler/predict')
var bodyParser = require('body-parser')
const multer = require('multer')

const upload = multer({
    storage: multer.memoryStorage(),
    limits: { filesize: 3 * 1024 *1024 }
})

router.get('/predict', (req,res,next) =>{
    res.status(200).json({
        message: 'Mandaika'
    })
})

router.use('/predict', upload.single('image'), postPredictHandler)

module.exports = router