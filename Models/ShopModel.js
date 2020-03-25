const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const ShopSchema = new Schema({
    ShopName:String,
    Shop
});

// https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=cruise&key=YOUR_API_KEY