const express = require("express")
const app = express();
const PORT = process.env.PORT || 5000;
const router = express.Router;

// Users Routes
var USERS = require("./Routes/Users");
app.use("/api/users",USERS);

app.listen(PORT,function(){
    console.log("Node Server Started At PORT="+PORT);
})