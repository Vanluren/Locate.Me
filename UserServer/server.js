var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var User = require("./models/users.js");

app.use(express.static(__dirname+'/client'));
app.use(bodyParser.json());



mongoose.connect('mongodb://localhost/users', function (err, db) {
    if(err){
        console.log(err);
    }
});
var db = mongoose.connection;

app.get('/', function(req, res) {
    res.send('Please refer to our api guide for requests.');
});

app.get('/api/users', function(req, res){
    User.getUser(function(err, users){
        if(err){
            throw err;
        }
        res.json(users);
    });
});

app.post('/api/users', function(req, res){
    var user = req.body;
    User.addUser(user, function(err, post){
        if(err){
            throw err;
        }
        res.json(post);
    });
});


app.get('/api/users/:_id', function (req, res) {
    var id = req.params.id;
    User.getUserById(id, function (err, user) {
        if(err){
            throw err;
        }
        res.json(user);
    });
});

app.delete('/api/users/:id', function (req, res) {
    var id = req.params._id;
    User.removeUser(id, function (err, user) {
        if(err){
            throw err;
        }
        res.json(user);
    });
});

app.listen(3000);

module.exports = app;
