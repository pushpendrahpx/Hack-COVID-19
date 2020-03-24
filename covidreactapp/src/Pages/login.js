import React, { Component } from 'react'
import { Redirect, Link } from 'react-router-dom';
import { get } from 'mongoose';
import Register from './Register';
import Navbar from '../Components/Navbar';

class login extends Component {
    constructor(props) {
        super(props)


        let loggedIn = false;
        if(localStorage.getItem("isLoggedIn") == "true"){
            loggedIn = true;
        }else{
            loggedIn = false;
        }
        this.state = {
                phone:'',
                password:'',
                loggedIn,
                isPending:false
        }
    }

     onSubmit = async (e)=>{
        e.preventDefault();
        this.setState({isPending:true})
        let phone = e.target.phone.value;
        let password = e.target.password.value;

        if(!phone || !password){
            this.setState({isPending:false})
            alert("Phone and Password are Empty")
            return;
        }


        let response = await fetch('/api/users/login',{
            headers:{
                'Content-Type': 'application/json'
            },
            method:"POST",
            body:JSON.stringify({
                phone:phone,
                password:password
            })
        });
        let data = await response.json()
        console.log(data) 

        if(data.statusCode == 200){
            localStorage.setItem("isLoggedIn",true);
            this.setState({
                loggedIn:true
            })
        }else{
            alert("Login Details are Invalid")
        }



        this.setState({isPending:false})
        
    }
    onChange = (e)=>{
       

        this.setState(
          {  [e.target.name]:e.target.value}
        )
    }


    render() {
        if(this.state.loggedIn === true){
            return <Redirect to='/home' />
        }else{
            return (<span>
                <Navbar />
                {this.state.isPending == true ? <progress class="progress is-small is-primary" max="100">15%</progress>:''}
                <form style={{padding:"20px"}} onSubmit={this.onSubmit}>
                <center style={{fontSize:'50px',fontWeight:200}}>Login Form</center>
                <div class="field">
                    <label class="label">Phone</label>
                    <div class="control">
                        <input id='phone' name='phone' class="input" type="number" placeholder="Phone Number" value={this.state.phone} onChange={this.onChange} />
                    </div>
                    {/* <p class="help">This is a help text</p> */}
                    </div>
    
                    
                <div class="field">
                    <label class="label">Password</label>
                    <div class="control">
                        <input id='password' name='password' class="input" type="password" placeholder="Password"  value={this.state.password} onChange={this.onChange}  />
                    </div>
                    {/* <p class="help">This is a help t/ext</p> */}
                    </div>
                        <Link to='/register'>Create New Account</Link>
                    <div class="control">
   <center> <button class="button is-primary">Submit</button></center>
    </div>
            </form> 
            <hr />
          
            </span>);
        }
    }



    
}

export default login
