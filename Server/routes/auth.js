const express = require('express')
const router = express.Router()
const User = require('../Models/user.js')
const mongoose = require('mongoose')
const bcrypt = require('bcrypt')
const jwt = require('jsonwebtoken')
const { check, validationResult } = require('express-validator/check');

router.post('/signup', [
    check('email').isEmail(),
    check('password').isLength({ min: 6})
], async (req, res, next) => {

    const errors = validationResult(req);
    if (!errors.isEmpty()) {
        res.status(422).json({ errors: errors.array() })
        return
    }
    res.status(200).json({ sal: "salut" })
})

module.exports = router