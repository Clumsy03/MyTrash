const { Firestore } = require('@google-cloud/firestore');
 
async function storeData(id, data) {
  const db = new Firestore({
    projectId: 'capstone-c241-pr554',
    keyFilename: 'capstone-c241-pr554-d0583a493ffb.json'
  });
 
  const predictCollection = db.collection('prediction');
  return predictCollection.doc(id).set(data);
}
 
module.exports = storeData;