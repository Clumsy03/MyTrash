const http = require('http')
const app = require('./app')
const loadModel = require('./utils/model')

const port = process.env.PORT || 5000

async function initializeapp(){
    try{
        const model = await loadModel()
        app.locals.model = model
        const server = http.createServer(app)
        server.listen(port, () =>{
            console.log('server berjalan')
        })
    }
    catch (error){
        console.error(error)
    }
}

initializeapp()





