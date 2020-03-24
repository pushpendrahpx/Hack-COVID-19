import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import Navbar from '../Components/Navbar'

class logout extends Component {
    constructor(props) {
        super(props)
        localStorage.setItem("isLoggedIn",false)
        this.state = {
                 
        }
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
