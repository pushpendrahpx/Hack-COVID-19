import React, { Component } from 'react'
import './home.css';
import { Link, Redirect } from 'react-router-dom';
import MainHome from './HomeComponents/MainHome';
import Map from './HomeComponents/Map'
class home extends Component {
    constructor(props) {
        super(props)


        let value = true;
        if(localStorage.getItem("isLoggedIn") == "true"){
            value = true;
        }else{
            value = false;
        }
        this.state = {
            status:false,
            isLoggedIn:value,

            latitude:0,
            longitude:0,
            accuracy:0
    }


    console.log(new Date())



    
    this.getCurrentLocation = this.getCurrentLocation.bind(this)

    this.getCurrentLocation()
    let x = setTimeout(()=>{
      
      if(this.state.latitude == 0){
        this.getCurrentLocation();
      }
    },500)
}


getCurrentLocation = ()=>{
          
  var self = this;
  navigator.geolocation.getCurrentPosition(function(location) {
    // console.log(location.coords.latitude);
    // console.log(location.coords.longitude);
    // console.log(location.coords.accuracy );

    self.lan = location.coords.latitude;
    self.long = location.coords.longitude;
    self.acc = location.coords.accuracy;
    // console.log(self)
  });
  console.log(this.lan)
  
  this.setState({
    latitude:this.lan,
    longitude:this.long,
    accuracy:this.acc
  })




  
  } // End of GetCurrentLocation()



    toggle = ()=>{
        if(this.state.status === false){
            this.setState({status:true});
            
        }else{
            this.setState({status:false})
        }

        console.log(this.state)
    }



    render() {
        if(this.state.isLoggedIn == false){
            return <Redirect to='/'></Redirect>
        }else{
            return (
                <div
                >
                    <nav className="navbar" role="navigation" aria-label="main navigation">
        <div className="navbar-brand">
          <a className="navbar-item" href="/" style={{fontSize:"40px",fontWeight:200}}>
            Stay Quaratine
          </a>
      
          <a href="#ToggleNavbar" role="button" onClick={this.toggle} className="navbar-burger burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
          </a>
        </div>
      
        <div id="navbarBasicExample" className={this.state.status === true ? 'navbar-menu is-active':"navbar-menu"}>
          <div className="navbar-start">
            <Link to='/home' className="navbar-item" href="#Home">
              Home
            </Link>
      
            <a className="navbar-item" href="#services">
              Services
            </a>
      
            <div className="navbar-item has-dropdown is-hoverable">
              <Link to='/home/settings' className="navbar-link" href="#login">
                Settings
              </Link>
      
              <div className="navbar-dropdown">
                <a className="navbar-item">
                    Account Settings
                </a>
                <a className="navbar-item">
                Location Services
                </a>
                <a className="navbar-item">
                  Privacy
                </a>
                <hr className="navbar-divider" />
                <a className="navbar-item">
                    Contact Us
                </a>
                <a className="navbar-item">
                  Report an issue
                </a>
              </div>
            </div>
    
            
          </div>
      
          <div className="navbar-end">
              <div className="navbar-item">
                <div className="buttons">
                  <Link to='logout' className="button is-danger">
                    Logout
                  </Link>
                </div>
              </div>
            </div>
        </div>
        
        
      </nav>
              <button className='button is-danger' onClick={this.getCurrentLocation}>get Location</button>

              <table>
                <tr>
                  <th>latitude</th><th>longitude</th><th>Accuracy</th>
                </tr>
                <tr>
            <td>{this.state.latitude}</td><td>{this.state.longitude}</td><td>{this.state.accuracy}</td>
                </tr>
              </table>
                    <article class="message is-primary">
                    <div class="message-header">
                        <p>Your Account Details</p>
                        <button class="delete" aria-label="delete"></button>
                    </div>
                    <div class="message-body">
    
                    </div>
                    </article>
    <article class="panel is-primary">
      <p class="panel-heading">
        Primary
      </p>
      <p class="panel-tabs">
        <a class="is-active">All</a>
        <a>Public</a>
        <a>Private</a>
        <a>Sources</a>
        <a>Forks</a>
      </p>
      <div class="panel-block">
        <p class="control has-icons-left">
          <input class="input is-primary" type="text" placeholder="Search" />
          <span class="icon is-left">
            <i class="fas fa-search" aria-hidden="true"></i>
          </span>
        </p>
      </div>
      <a class="panel-block is-active">
        <span class="panel-icon">
          <i class="fas fa-book" aria-hidden="true"></i>
        </span>
        bulma
      </a>
      <a class="panel-block">
        <span class="panel-icon">
          <i class="fas fa-book" aria-hidden="true"></i>
        </span>
        marksheet
      </a>
      <a class="panel-block">
        <span class="panel-icon">
          <i class="fas fa-book" aria-hidden="true"></i>
        </span>
        minireset.css
      </a>
      <a class="panel-block">
        <span class="panel-icon">
          <i class="fas fa-book" aria-hidden="true"></i>
        </span>
        jgthms.github.io
      </a>
    </article>
                <Map />
                </div>
            )
        }
    }
}

export default home
