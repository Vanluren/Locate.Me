var express = require('express');
var app = express();
var path = require('path');
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');

Users = require('./models/users')


mongoose.connect('mongodb://localhost/UserDB')
var db = mongoose.connection;

app.get('/', function(req, res) {
    res.send('Please refer to our api guide for requests.');
});

app.get('/userLocations', function(req, res){
        Users.getUser(function (err, users) {
         res.json(users);
        });
});


app.listen(3000);

module.exports = app;
