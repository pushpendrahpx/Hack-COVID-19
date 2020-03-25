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
        old:[{lat:Number,lng:Number,time:Date,StringTime:String}],
        last_lat:{value:Number,time:{type:Date},timeInstance:Number},
        last_lng:{value:Number,time:{type:Date},timeInstance:Number},
        latitude:{value:Number,time:{type:Date},timeInstance:Number},
        longitude:{value:Number,time:{type:Date},timeInstance:Number}
    }
});

module.exports = mongoose.model('users',UserSchema);
