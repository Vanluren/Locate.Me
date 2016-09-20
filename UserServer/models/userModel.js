var mongoose = require('mongoose');

var UserSchema = new mongoose.Schema({
  name: String,
  lat: Number,
  lng: Number
});

module.exports = mongoose.model('User', UserSchema);
