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

    const errors = validationResult(req)
    if (!errors.isEmpty()) {
        return res.status(422).json({ errors: errors.array() })
    }

    try {
        console.log(req.body.email)
        const user = await User.findOne({ email: req.body.email })
        if (user) {
            return res.status(409).json({
                error: "Email already in use."
            })
        } else {

            const hash = await bcrypt.hash(req.body.password, 10);

            const user = new User({
                _id: new mongoose.Types.ObjectId(),
                email: req.body.email,
                password: hash,
                name: req.body.name,
                phoneNumber: req.body.phoneNumber,
                gender: req.body.gender,
                birthDate: req.body.birthDate,
                bloodType: req.body.bloodType,
                height: req.body.height,
                weight: req.body.weight,
                healthIssues: req.body.healthIssues,
                onGoingTreatments: req.body.onGoingTreatments,
                allergies: req.body.allergies,
                emergencyContactName: req.body.emergencyContactName,
                emergencyContactPhoneNumber: req.body.emergencyContactPhoneNumber
            })

            const token = jwt.sign({
                email: user.email,
                userId: user._id
            }, process.env.JWT_KEY, 
            {
                expiresIn: "1000d"
            })

            try
            {
                await user.save()
                const savedUser = await User.findOne({_id: user._id}).select("-password")
                return res.status(200).json({user: savedUser, token: token})
            } catch(e) {
                res.status(500).json(e)
            }
        
        }
    } catch(e) {
        return res.status(500).json(e)
    }
})

router.post('/login', [
    check('email').isEmail(),
    check('password').isLength({ min: 6})
], async (req, res, next) => {

    const errors = validationResult(req)
    if (!errors.isEmpty()) {
        return res.status(422).json({ errors: errors.array() })
    }

    try {
        const user = await User.findOne({email: req.body.email})
        if (user) {
            var ok = await bcrypt.compare(req.body.password, user.password)
            return res.status(200).json(ok)
        }
    } catch(e) {
        return res.status(500).json(e)
    }
})

module.exports = router