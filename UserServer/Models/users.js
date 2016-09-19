// grab the things we need
var mongoose = require('mongoose');


// create a schema
var userSchema = mongoose.Schema({
    id: Number,
    name: String,
    lat: Number,
    lng: Number,
    date: {type:Date, default: Date.now()},
    updated_at: {type:Date, default:Date.now()}
});

var User = mongoose.model('User', userSchema, 'users');

module.exports.getUser = function (callback, limit) {
    User.find(callback).limit(limit);
};
module.exports.getUserById = function (callback, limit) {
    var id = {id:id};
    User.findById(id, callback);
};
module.exports.addUser = function(user, callback){
    User.create(user, callback);
};
module.exports.removeUser = function (callback, limit) {
    var id = {id:id};
    User.findOneAndRemove(_id, callback);
};
