var mongoose = require('mongoose');

var UserSchema = new mongoose.Schema({
  name: String,
  email: String,
  password: String,
  lat: Number,
  lng: Number,
  mac_address: String
});

module.exports = mongoose.model('User', UserSchema);
