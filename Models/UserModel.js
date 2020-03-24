const mongoose = require("mongoose")
const Schema = mongoose.Schema;

const UserSchema = new Schema({
    name:String,
    email:String,
    phone:Number,
    password:String,
    isVerifiedPhone:Boolean,
    isVerifiedEmail:Boolean,
    location:{
        lattitude:{type:Number},
        longitude:{type:Number}
    }
});

module.exports = mongoose.model('users',UserSchema);
