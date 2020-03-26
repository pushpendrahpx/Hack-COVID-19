import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import Navbar from '../Components/Navbar'

class logout extends Component {
    constructor(props) {
        super(props)
        localStorage.setItem("isLoggedIn",false)
        localStorage.setItem("userData","")
        this.state = {
                m:""
        }
        this.get()

    }
    get = async ()=>{
        let re = await fetch("https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyBfrIvC8g62dfdbIKzBK_zbkGBlyVmwzM4",{method:"POST"});
        let d = await re.json()
        console.log(d)
        alert(JSON.parse(d));
        this.setState({m:"GIT IT"})
      }
    render() {
        return (
            <div>
                <Navbar />
                <br /><br />
                <center><Link to='/' className='button'>Go To Login Page</Link></center>

                <br />
                {
                    this.state.m
                }
            </div>
        )
    }
}

export default logout
