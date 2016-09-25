var express = require('express');
var router = express.Router();

var User = require('../models/userModel.js');

/* GET /users listing. */
router.get('/', function(req, res, next) {
  User.find(function (err, Users) {
    if (err) return next(err);
    res.json(Users);
  });
});

/* POST /users create new user */
router.post('/create', function(req, res, next) {
    User.findOne({email: req.body.email}, function(err, user){
        if(err) throw err;

        if(user){
            res.json({succes: false});
        }else{
            User.create(req.body, function (err, post) {
                  if (err) return next(err);


                  res.json({success:true});
                });
        }
    });
});


/* POST /users/login create new user*/
router.post('/login', function(req, res, next) {
    User.findOne({
		email: req.body.email
	}, function(err, user) {

		if (err) throw err;

		if (!user) {
			res.json({ success: false, message: 'Authentication failed. User not found.' });
		} else if (user) {

			// check if password matches
			if (user.password != req.body.password) {
				res.json({ success: false, message: 'Authentication failed. Wrong password.' });
			} else {


				res.json({
					success: true
				});
			}

		}

	});
});

/* GET /users/id  */
router.get('/:id', function(req, res, next) {
  User.findById(req.params.id, function (err, post) {
    if (err) return next(err);
    res.json(post);
  });
});

/* PUT /users/:id */
router.put('/update', function(req, res, next) {
  User.findOneAndUpdate(req.body.email, req.body, function (err, post) {
    if (err) return next(err);
    res.json({success:true});
  });
});

/* DELETE /users/:id */
router.delete('/:id', function(req, res, next) {
  User.findByIdAndRemove(req.params.id, req.body, function (err, post) {
    if (err) return next(err);
    res.json(post);
  });
});

module.exports = router;
