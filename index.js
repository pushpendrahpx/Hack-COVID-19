const express = require("express")
const app = express();
const PORT = process.env.PORT || 5000;
const router = express.Router();

const config = require("./config.json"); // Configuration Files Containes Connection Details With Database

const ErrorMessageClass = require("./Models/ErrorMessage");

// Users Routes
var USERS = require("./Routes/Users");
app.use("/api/users",USERS);

app.get("/",(req,res)=>{
    console.log("Server Request Method in with GET '/' Method = "+req.secure)
    res.status(400).json(new ErrorMessageClass(400,"This API Request is Invalid"));
})
app.listen(PORT,function(){
    console.log("Node Server Started At PORT="+PORT);
})