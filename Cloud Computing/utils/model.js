const tf = require('@tensorflow/tfjs-node');

const loadModel = async () =>{
    return await tf.loadGraphModel('https://storage.googleapis.com/capstone_mytrash/saved/saved/model.json')
}

 
module.exports = loadModel;