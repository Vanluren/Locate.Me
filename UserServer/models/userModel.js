var mongoose = require('mongoose');

var UserSchema = new mongoose.Schema({
  name: String,
  email: String,
  password: String,
  lat: Number,
  lng: Number
});

module.exports = mongoose.model('User', UserSchema);
