const mongoose = require("mongoose")
const Schema = mongoose.Schema;

const UserSchema = new Schema({
    name:String,
    email:String,
    phone:Number,
    password:String,
    lastLoginTime:Date,
    isVerifiedPhone:Boolean,
    isVerifiedEmail:Boolean,
    lastScore:{value:Number,time:{type:Date},timeInstance:Number},
    TotalScore:{value:Number},
    ScoresList:[{value:Number,time:{type:Date},timeInstance:Number}],
    homeLocation:{
        lat:Number,
        lng:Number,
        time:Date,
        Accuracy:Number,
    },
    location:{
        old:[{lat:Number,lng:Number,time:Date,StringTime:String,Accuracy:Number}],
        last_lat:{value:Number,time:{type:Date},timeInstance:Number,Accuracy:Number},
        last_lng:{value:Number,time:{type:Date},timeInstance:Number,Accuracy:Number},
        latitude:{value:Number,time:{type:Date},timeInstance:Number,Accuracy:Number},
        longitude:{value:Number,time:{type:Date},timeInstance:Number,Accuracy:Number}
    }
});

module.exports = mongoose.model('users',UserSchema);
