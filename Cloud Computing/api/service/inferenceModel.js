const tf = require('@tensorflow/tfjs-node');
const loadModel = require('../../utils/model')

async function predictClassification(model,image) {
        const tensor = tf.node
            .decodeJpeg(image)
            .resizeNearestNeighbor([224, 224])
            .expandDims()
            .toFloat();

        const prediction = model.predict(tensor);
        const score = await prediction.data();
        const confidenceScore = Math.max(...score);
        

        const classes = [
            'battery',
             'biological',
             'brown-glass',
             'cardboard',
             'clothes',
             'green-glass',
             'metal',
             'paper',
             'plastic',
             'shoes',
             'trash',
             'white-glass'
        ];
        
        const classResult = tf.argMax(prediction, 1).dataSync()[0];
        
        const label = classes[classResult];

        const organik = ['biological'];
        const anorganik = [
            'cardboard',
            'clothes',
            'metal',
            'paper',
            'plastic',
            'shoes',
            'green-glass',
            'white-glass'
        ];
        const berbahaya = ['battery','brown-glass','trash'];
        
        let result;
        let suggestion;


        if(organik.includes(label)){
            result = 'Organik';
            suggestion = `Buang Sampahmu di Tempat yang Tepat! Gunakan Tempat Sampah Hijau untuk membuang sampah ini`;
        }
        else if(anorganik.includes(label)){
            result = 'Anorganik';
            suggestion = `Buang Sampahmu di Tempat yang Tepat! Gunakan Tempat Sampah Kuning untuk membuang sampah ini`;
        }
        else if(berbahaya.includes(label)){
            result = 'Sampah Berbahaya';
            suggestion = `Buang Sampahmu di Tempat yang Tepat! Gunakan Tempat Sampah Merah untuk membuang sampah ini`;
        }
            
        
        return { result, suggestion, confidenceScore };
}

module.exports = { predictClassification };