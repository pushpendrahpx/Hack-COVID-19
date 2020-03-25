import React, { Component } from 'react'
import Home from '../home'
import { Link } from 'react-router-dom'

class SearchShops extends Component {
    constructor(props) {
        super(props)

        this.state = {
                 
        }
    }

    render() {
        return (
            <span>
                <div class="tabs">
  <ul>
    <li class="is-active"><a>Home</a></li>
    <li><Link to='/home/s'>Local Shops</Link></li>
    <li><Link>Posts</Link></li>
    <li><Link>Profile</Link></li>
  </ul>
</div>

            </span>
        )
    }
}

export default SearchShops
