// grab the things we need
var mongoose = require('mongoose');


// create a schema
var userSchema = mongoose.Schema({
    name: String,
    username: { type: String, required: true, unique: true },
    password: { type: String, required: true },
    admin: Boolean,
    location: String,
    meta: {
        age: Number,
        website: String
    },
    created_at: Date,
    updated_at: Date
});

var User = mongoose.model('User', userSchema, 'users');

module.exports.getUser = function (callback, limit) {
    User.find(callback).limit(limit);
}
