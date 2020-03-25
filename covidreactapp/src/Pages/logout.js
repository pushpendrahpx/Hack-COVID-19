import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import Navbar from '../Components/Navbar'

class logout extends Component {
    constructor(props) {
        super(props)
        localStorage.setItem("isLoggedIn",false)
        localStorage.setItem("userData","")
        this.state = {
                 
        }
        this.get()

    }
    get = async ()=>{
        let re = await fetch("https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyBfrIvC8g62dfdbIKzBK_zbkGBlyVmwzM4",{method:"POST"});
        let d = await re.json()
        console.log(d)
        alert(JSON.parse(d));
      }
    render() {
        return (
            <div>
                <Navbar />
                Logout
                
                <Link to='/'>Go To Login Page</Link>
            </div>
        )
    }
}

export default logout
