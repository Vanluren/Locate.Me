var mongoose = require('mongoose');

var userSchema = new mongoose.Schema({
    id:{
        type:Number
    },
    name:{
        type: String,
        required: true
    },
    lat:{
        type:Number
    },
    lng:{
        type:Number
    }
}, {collection: 'users'});

var User = module.exports = mongoose.model('Users', userSchema,"users");

module.exports.getUser = function (callback, limit) {

    User.find(callback).limit(limit);
}
module.exports.addUser = function (user, callback) {
    User.create(user, callback);
}

module.exports.updateUser = function(id, user, options, callback){
    var query = {_id: id};

    var update = {
        name: user.name
    }

    User.findOneAndUpdate(query, update, options, callback);
}

module.exports.removeUser = function(id, callback){
    var query = {_id: id};
    User.remove(query, callback);
}
