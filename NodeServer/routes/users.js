var express = require('express');
var router = express.Router();
var server;
//Her starter vi med at fortælle node at vi bruger MongoDB til at håndtere vores data.
var mongo = require('mongodb');

//Her definerer vi hvad en Server er.
var Server = mongo.Server,
    Db = mongo.Db,
    BSON = mongo.BSONPure;

//Her instantiere vi en Server, og giver den de forskellige parametre den skal bruge for at kunne kontakte den.
var server = new Server('localhost', 27017, {auto_reconnect: true});
db = new Db('userDB', server);

//Her åbner vi reelt forbindelsen til DB
db.open(function(err, db) {
    if(!err) {
        console.log("Connected to 'user_DB' database");
        db.collection('Users', {strict:true}, function(err, collection) {
            if (err) {
                console.log("The 'Users' collection doesn't exist.");
                sampleDBData();
            }
        });
    }
});

// herfra og ned definerer vi hvilke routes vi har til vores server
exports.getAllUserLocations = function(req, res) {
    db.collection('Users', function (err, collection) {
       collection.find().toArray(function (err, items) {
           res.send(items);
       }) 
    });
};

exports.getUserLocationById = function (req, res) {
    var id = req.params.id;
    db.collection('users', function (err, collection) {
        collection.findOne({'_id': new BSON.objectId(id)}, function (err, item) {
           res.send(item);
        });
    });
    
};
exports.postNewUser = function(req, res) {

};
exports.putUserUpdate = function (req, res) {


};
exports.deleteUser = function (req, res) {

};


var sampleDBData = function () {
    var users = [{
        id:20160901,
        name: "User One Nielsen",
        lat:56.1463826,
        lng:10.1919681}];

    db.collection('users', function (err, collection) {
        collection.insert(users, {safe:true},function (err,result) {});
    });
};


module.exports = router;
