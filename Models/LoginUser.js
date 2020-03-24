const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const LoginUserSchema = new Schema({
    Phone:{type:Number},
    Password:{type:String},
    isValid:{type:Boolean}
});

module.exports = mongoose.model("Users",LoginUserSchema)