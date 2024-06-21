const express = require('express')
const bodyParser = require('body-parser')
const crypto = require('crypto')
const router = express.Router()
const { predictClassification } = require('../service/inferenceModel')
const  storeData  = require('../service/storeData')

const app = express()

app.use(bodyParser.json())

router.post('/',async(req,res)=>{
    const  image  = req.file.buffer
    const { model } = req.app.locals
    console.log(req)
    const id = crypto.randomUUID()
    const createdAt = new Date().toISOString()
    const { result, suggestion, confidenceScore } = await predictClassification(model,image);

    const data = {
        "data": "Data sudah masuk",
        "id": id,
        "createdAt": createdAt,
        "result": result,
        "suggestion": suggestion
    }

    await storeData( id, { result, suggestion, confidenceScore } )

    res.status(201).json({
        status: 'succes',
        message: 'good',
        data: data
    })
    return res
})


module.exports = router