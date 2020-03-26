import React, { Component } from 'react'
import './home.css';
import { Link, Redirect } from 'react-router-dom';
import Map from './HomeComponents/Map'
import Details from './HomeComponents/Details';



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
            accuracy:0,

            response: false,
            endpoint: "http://localhost:5000/",
            name:"",
            email:"",
            phone:0,
            Leaderboard:[],
            d:0
    }

    console.log(new Date())


    this.LoggedUserData = JSON.parse(localStorage.getItem("userData"));

    this.getCurrentLocation = this.getCurrentLocation.bind(this)

    this.getCurrentLocation();
    
    setTimeout(()=>{
      
      if(this.state.latitude == 0){
        this.getCurrentLocation();

        setInterval(()=>{

          
          this.getCurrentLocation()
          
          this.sendLocationtoServer();

          
          
        },500)
      }
    },400)

    


} // end of constructor
getUsers = async ()=>{
  let re = await fetch("/api/users/getalloftheusers");
  let data = await re.json();

  this.setState({Leaderboard:data},()=>{
    // console.log(this.state)
  })
  
}
sendLocationtoServer =  async ()=>{

  let rawData = JSON.parse(localStorage.getItem("userData"));
  // console.log("ACCURACY ====================/=========>>>>>>>>>>>>>>> "+this.state.accuracy)
    let response = await fetch('/api/users/update/'+rawData.phone+'/location',{
      method:"POST",
      headers:{
        'Content-type':'Application/json'
      },
      body:JSON.stringify({lat:this.state.latitude,lng:this.state.longitude,time:new Date(),accuracy:this.state.accuracy})
    })

    let isUpdated = await response.json();
    // localStorage.setItem("userData")
    localStorage.setItem("userData",JSON.stringify(isUpdated));
    // console.log("isUpdatedisUpdatedisUpdatedisUpdatedisUpdatedisUpdatedisUpdatedisUpdatedisUpdatedisUpdatedisUpdatedisUpdatedisUpdatedisUpdatedisUpdatedisUpdatedisUpdatedisUpdated",isUpdated)
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
  // console.log(this.lan)

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

        // console.log(this.state)
    }

    componentDidMount(){
      window.stop();
    }

    render() {
        if(this.state.isLoggedIn == false){
            return <Redirect to='/'></Redirect>
        }else{
            return (
                <div
                >
                    <nav className="navbar is-primary" role="navigation" aria-label="main navigation">
        <div className="navbar-brand">
          <a className="navbar-item waves-effect" href="/" style={{fontSize:"40px",fontWeight:200,position:"relative",width:"80%"}}>
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
      <br /><div class="tabs">
  <ul>
    <li class="is-active"><a>Home</a></li>
    <li><Link to='/home/shops'>Local Shops</Link></li>
    <li><Link>Posts</Link></li>
    <li><Link>Profile</Link></li>
  </ul>
</div>

              <center style={{margin:'2px',boxShadow:'0 0 2px grey',padding:'20px',margin:'20px'}}>

              <button className='button is-danger waves-effect' onClick={this.getCurrentLocation}>get Location</button>

              <table>
                <tr>
                  <th>Latitude</th><th>Longitude</th><th>Accuracy</th>
                </tr>
                <tr>
              <td>{this.state.latitude}</td><td>{this.state.longitude}</td><td>{this.state.accuracy}</td>
                </tr>
              </table>
              </center>
                    <Details data={this.LoggedUserData} />
    <article class="panel is-primary">
      <p class="panel-heading waves-effect" style={{position:'relative',width:'100%'}}>
        Leaderboard
      </p>
      <div class="panel-block">
        <p class="control has-icons-left">
          <input class="input is-primary" type="text" placeholder="Search" />
          <span class="icon is-left">
            <i class="fas fa-search" aria-hidden="true"></i>
          </span>
        </p>
      </div>
      {/* {
        this.state.Leaderboard.map(user=>{
                  return  <a class="panel-block is-active waves-effect" style={{width:'100%',display:'inline-flex'}}>
                  <span class="panel-icon">
                    <i class="fas fa-user-circle" aria-hidden="true"></i>
                  </span>
                  
                  <span>{user.name}</span>
                  
                </a>
                })
              } */}
    </article>
                <Map />
                </div>
            )
        }
    }
}

export default home
