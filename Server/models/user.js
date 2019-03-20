const mongoose = require('mongoose')

const userSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    email: {
        type: String,
        required: true,
        unique: true,
        match: /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/,
    },
    password: {
        type: String,
        required: true,
        minlength: 6
    },
    name: {
        type: String,
        required: true
    },
    phoneNumber: {
        type: String,
        required: true
    },
    gender: {
        type: String,
        enum: ["male", "female", "other"]
    },
    birthDate: {
        type: Number,
    },
    bloodType: {
        type: String,
        enum: ["A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"]
    },
    height: {
        type: Number
    },
    weight: {
        type: Number
    },
    onGoingTreatments: {
        type: [String]
    },
    allergies: {
        type: [String]
    },
    emergencyContactName: {
        type: String
    },
    emergencyContactPhoneNumber: {
        type: String
    }
})

module.exports = mongoose.model('User', userSchema)