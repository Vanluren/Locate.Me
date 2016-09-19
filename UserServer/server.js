var express = require('express');
var app = express();

var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var logger = require('morgan');
var path = require('path');
var favico

var User = require("./models/userModel.js");
var routes = require('./routes/index');
var users = require('./routes/users.js');

app.use('/', routes);
app.use('/users', users);

app.use(logger('dev'));
app.use(bodyParser.urlencoded({ extended: false }))
app.use(express.static(__dirname+'/client'));
app.use(bodyParser.json());

mongoose.connect('mongodb://localhost/users', function (err, db) {
    if(err){
        console.log(err);
    }
});

var db = mongoose.connection;

app.listen(3000);

module.exports = app;

app.get('/', function(req, res) {
    res.send('Please refer to our api guide for requests.');
});

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
  app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
      message: err.message,
      error: err
    });
  });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
  res.status(err.status || 500);
  res.render('error', {
    message: err.message,
    error: {}
  });
});

module.exports = app;
