const express = require("express");
var router = express.Router();

router.get("/run",(req,res)=>{
    console.log("run")
    res.status(200).json({e:"S"})
})

module.exports = router;
