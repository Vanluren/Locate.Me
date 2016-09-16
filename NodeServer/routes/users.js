var express = require('express');
var router = express.Router();

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});


exports.getAllUserLocations = function(req, res) {

};
exports.getUserLocation = function (req, res) {

};
exports.postNewUser = function(req, res) {

};
exports.putUserUpdate = function (req, res) {

}
exports.deleteUser = function (req, res) {

};


module.exports = router;
