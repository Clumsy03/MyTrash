const express = require('express')
const app = express()
const Routes = require('./api/routes/index')
const cookieParser = require('cookie-parser')

app.use(express.json())
app.use(cookieParser())

app.use('/',Routes)

module.exports = app