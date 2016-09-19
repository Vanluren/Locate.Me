// grab the things we need
var mongoose = require('mongoose');


// create a schema
var userSchema = mongoose.Schema({
    id: Number,
    name: String,
    lat: Number,
    lng: Number,
    date: {type:Date, default: Date.now()}
});

var User = mongoose.model('User', userSchema, 'users');

module.exports = User;
