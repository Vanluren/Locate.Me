var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var User = require("./models/users.js");

app.use(express.static(__dirname+'/client'));
app.use(bodyParser.json());



mongoose.connect('mongodb://localhost/userDB', function (err, db) {
    if(err){
        console.log(err);
    }
});
var db = mongoose.connection;

app.get('/', function(req, res) {
    res.send('Please refer to our api guide for requests.');
});

app.get('/api/userLocations', function(req, res){
    User.getUser(function(err, users){
        if(err){
            throw err;
        }
        res.json(users);
    });
});


app.listen(3000);

module.exports = app;
