import React, { Component } from 'react'
import { Link } from 'react-router-dom'

class logout extends Component {
    constructor(props) {
        super(props)

        this.state = {
                 
        }
    }

    render() {
        return (
            <div>
                Logout

                <Link to='/'>Go To Login Page</Link>
            </div>
        )
    }
}

export default logout
