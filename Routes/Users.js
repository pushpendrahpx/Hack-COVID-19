const express = require("express");
var router = express.Router();
const mongoose = require("mongoose");

const config = require("./../config.json"); // Configuration Files Containes Connection Details With Database & Some Security KEYS

const crypto = require("crypto");
const uuid = require("uuid");



// const LoginUserModel = require("./../Models/LoginUser");
// var x = LoginUserModel({email:""})
const secret = config.SECRET_KEY;
function getHashPassword(plainPassword){
   return crypto.createHmac('sha256', secret)
                   .update(plainPassword)
                   .digest('hex');
}
// const hash = getHashPassword('GoogleUser');
// console.log(hash);

 
router.post("/register",(req,res)=>{

    let {email,password,name,phone,loginTime } = req.body;
    try{
        console.log("\n\n Recording Body");
        console.log(req.body);
        console.log("\n\n ====== Done =======");

        if(email.length > 10 && phone.length == 10 && password.length >= 6 && name.length >= 3){
            password = getHashPassword(password);
            let UserModel = require("./../Models/UserModel");
            let User = new UserModel({name:name,phone:phone,email:email,password:password,lastLoginTime:loginTime,isVerifiedPhone:false,
                isVerifiedEmail:false});
            UserModel.countDocuments({phone:phone},(err,users)=>{
                if(users == 0){
                    User.save().then(doc=>{
                        res.status(201).json({isRegistered:true,doc})
                    })
                }else{
                    res.status(400).json({
                        error:"User Phone Already Exists"
                    })
                }
            })
            
        }else{
            throw ["Email is invalid","Phone Number is not 10 digit","password must be greater than 6","Name May be Invalid"]
        }
    }catch(error){
        res.status(400).json({
            statusCode:400,
            error:error
        });

    }
})

router.get('/getalloftheusers',(req,res)=>{

    let UserModel = require("./../Models/UserModel");
    UserModel.find({},(err,docs)=>{
        res.status(200).json(docs)
    })

})
router.post('/login',(req,res)=>{
    let {phone,password,loginTime,Accuracy} = req.body;
    try{
        console.log("\n\n Recording Body");
        console.log(req.body);
        console.log("\n\n ====== Done =======");

        if(phone.length == 10 && password.length >= 6){
            password = getHashPassword(password);
            let UserModel = require("./../Models/UserModel");
            let User = new UserModel({phone:phone,password:password,lastLoginTime:loginTime,});
            UserModel.findOne({phone:phone},(err,users)=>{
                if(err){
                    res.status(402).json({
                        statusCode:402,
                        error:"User With this Phone Doesn't exists"
                    })
                }

                try{
                    if(users.password == password){

                        
                        res.status(200).json({
                            statusCode:200,
                            success:"Logged In",
                            userData:users
                        })
                    }else{
                        res.status(401).json({
                            StatusCode:401,
                            error:"Password Entered is Invalid"
                        });
                    }
                }catch(ier){
                    res.status(402).json({
                        statusCode:402,
                        error:"Phone Number doesnt Exists"
                    })
                }
            })
            
        }else{
            throw ["Email is invalid","Phone Number is not 10 digit","password must be greater than 6","Name May be Invalid"]
        }
    }catch(error){
        res.status(400).json({
            statusCode:400,
            error:error
        });

    }
})


router.post('/update/:phone/location',(req,res)=>{
    let {phone} = req.params;
    let lat = req.body.lat;
    let lng = req.body.lng;
    let time = req.body.time;
    let acc = req.body.accuracy;


    console.log("Latitude = "+lat+ ", Longitude = " + lng +" Time = "+ time + " with Accuracy = "+acc);

    let UserModel = require("./../Models/UserModel");
    UserModel.findOne({phone:phone},(error,UserFromDB)=>{
            var lastlat = UserFromDB.location.latitude;
            var lastlng = UserFromDB.location.longitude;
        console.log("\t\t\t\Last Lat",lastlat)
            let FinalAccuracy = 0;
        if(UserFromDB.homeLocation >= acc){
                FinalAccuracy = acc; 

                UserFromDB.updateOne(
                    {location:
                            {
                                latitude:{value:lat,time:time},
                                longitude:{value:lng,time:time},
                                last_lat:{value:lastlat.value,time:lastlat.time},
                                last_lng:{value:lastlng.value,time:lastlng.time}
                            },
                            homeLocation:{lat:lat,lng:lng,time:time,Accuracy:acc},
                    $push: {'location.$.old': {lat:lat,lng:lng,time:time}}
                    },(err,docs)=>{
        console.log(UserFromDB)
        // console.log(docs)
        res.json(UserFromDB)
        })
        }else{
            UserFromDB.updateOne(
                {location:
                        {
                            latitude:{value:lat,time:time},
                            longitude:{value:lng,time:time},
                            last_lat:{value:lastlat.value,time:lastlat.time},
                            last_lng:{value:lastlng.value,time:lastlng.time}
                        },
                 homeLocation:{lat:UserFromDB.location.latitude.value,lng:UserFromDB.location.longitude.value,time:UserFromDB.location.latitude.time,Accuracy:UserFromDB.location.latitude.Accuracy},
                $push: {'location.$.old': {lat:lat,lng:lng,time:time}}
                },(err,docs)=>{
    console.log(UserFromDB)
    // console.log(docs)
    res.json(UserFromDB)
    })
        }


       


                    
    })
    
})


// Android

router.post("/register/android",(req,res)=>{
    
    let {email,password,name,phone,loginTime } = req.body;
    try{
        console.log("\n\n Recording Body");
        console.log(req.body);
        console.log("\n\n ====== Done =======");

        if(email.length > 10 && phone.length == 10 && password.length >= 6 && name.length >= 3){
            password = getHashPassword(password);
            let UserModel = require("./../Models/UserModel");
            let User = new UserModel({name:name,phone:phone,email:email,password:password,lastLoginTime:loginTime,isVerifiedPhone:false,
                isVerifiedEmail:false});
            UserModel.countDocuments({phone:phone},(err,users)=>{
                if(users == 0){
                    User.save().then(doc=>{
                        res.status(201).json({isRegistered:true,docs:{name:doc.name,email:doc.email,phone:doc.phone,scoreList:doc.TotalScore}})
                    })
                }else{
                    res.status(400).json({
                        error:"User Phone Already Exists"
                    })
                }
            })
            
        }else{
            throw ["Email is invalid","Phone Number is not 10 digit","password must be greater than 6","Name May be Invalid"]
        }
    }catch(error){
        res.status(400).json({
            statusCode:400,
            error:error
        });

    }
})
router.get('/getalloftheusers/android',(req,res)=>{

    let UserModel = require("./../Models/UserModel");
    UserModel.find({},(err,docs)=>{
        res.status(200).json(docs)
    })

})

router.post('/login/android',(req,res)=>{
    let {phone,password,loginTime} = req.body;
    try{
        console.log("\n\n Recording Body");
        console.log(req.body);
        console.log("\n\n ====== Done =======");

        if(phone.length == 10 && password.length >= 6){
            password = getHashPassword(password);
            let UserModel = require("./../Models/UserModel");
            let User = new UserModel({phone:phone,password:password,lastLoginTime:loginTime});
            UserModel.findOne({phone:phone},(err,users)=>{
                if(err){
                    res.status(402).json({
                        statusCode:402,
                        error:"User With this Phone Doesn't exists"
                    })
                }

                try{
                    if(users.password == password){

                        
                        res.status(200).json({
                            statusCode:200,
                            success:"Logged In",
                            userData:users
                        })
                    }else{
                        res.status(401).json({
                            StatusCode:401,
                            error:"Password Entered is Invalid"
                        });
                    }
                }catch(ier){
                    res.status(402).json({
                        statusCode:402,
                        error:"Phone Number doesnt Exists"
                    })
                }
            })
            
        }else{
            throw ["Email is invalid","Phone Number is not 10 digit","password must be greater than 6","Name May be Invalid"]
        }
    }catch(error){
        res.status(400).json({
            statusCode:400,
            error:error
        });

    }
})

router.post('/update/:phone/location/android',(req,res)=>{
    let {phone} = req.params;
    let lat = req.body.lat;
    let lng = req.body.lng;
    let time = req.body.time;
    console.log("Latitude = "+lat+ ", Longitude = " + lng +" Time = "+ time);

    let UserModel = require("./../Models/UserModel");
    UserModel.findOne({phone:phone},(error,UserFromDB)=>{
            var lastlat = UserFromDB.location.latitude;
            var lastlng = UserFromDB.location.longitude;
        console.log("\t\t\t\Last Lat",lastlat)
        UserFromDB.updateOne(
                                {location:
                                        {
                                            latitude:{value:lat,time:time},
                                            longitude:{value:lng,time:time},
                                            last_lat:{value:lastlat.value,time:lastlat.time},
                                            last_lng:{value:lastlng.value,time:lastlng.time}
                                        },
                                $push: {'location.$.old': {lat:lat,lng:lng,time:time}}
                                },(err,docs)=>{
                console.log(UserFromDB)
            // console.log(docs)
        res.json(UserFromDB)
        })
    })
    
})


module.exports = router;
