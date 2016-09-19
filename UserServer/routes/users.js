var express = require('express');
var router = express.Router();
var User = require('../models/userModel.js');

router.get('/', function(req, res, next) {
  User.find(function (err, users) {
    if (err) return next(err);
    res.json(users);
  });
});

router.post('/', function(req,res,next){
    User.create(function(err, user){
        if(err) return next(err);
        res.json(user);
    });
});

router.get('/:id', function(req, res, next){
    User.findById(req.params.id, function(err, user){
        if(err) return next(err);
        res.json(user);
    });

});

router.put('/:id', function(req, res, next){
    User.findByIdAndUpdate(req.params.id, req.body, function(err, user){
        if(err) return next(err);
        res.json(user);
    });

});

router.delete('/:id', function(req, res, next){
    User.findByIdAndRemove(req.params.id, function(err, user){
        if(err) return next(err);
        res.json(user);
    });

});

module.exports = router;
