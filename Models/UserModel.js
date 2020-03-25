const mongoose = require("mongoose")
const Schema = mongoose.Schema;

const UserSchema = new Schema({
    name:String,
    email:String,
    phone:Number,
    password:String,
    isVerifiedPhone:Boolean,
    isVerifiedEmail:Boolean,
    lastScore:{value:Number,time:{type:Date},timeInstance:Number},
    TotalScore:{value:Number},
    ScoresList:[{value:Number,time:{type:Date},timeInstance:Number}],
    location:{
        last_lat:{value:Number,time:{type:Date},timeInstance:Number},
        last_lng:{value:Number,time:{type:Date},timeInstance:Number},
        lattitude:{value:Number,time:{type:Date},timeInstance:Number},
        longitude:{value:Number,time:{type:Date},timeInstance:Number}
    }
});

module.exports = mongoose.model('users',UserSchema);
