import React, { Component } from 'react'
import { Redirect, Link } from 'react-router-dom';
import { get } from 'mongoose';
import Register from './Register';

class login extends Component {
    constructor(props) {
        super(props)


        let loggedIn = false;

        this.state = {
                email:'',
                password:'',
                loggedIn
        }
    }

     onSubmit = async (e)=>{
        e.preventDefault();

        let email = e.target.email.value;
        let password = e.target.password.value;

        if(email == 'A' && password == 'A')
        {

            localStorage.setItem("token","ygfehbkerbdjlobjsdgfioaejknfdekjrdgn");

            this.setState({loggedIn:true})
        }



        let response = await fetch('http://localhost:5000/api/users/register',{
            headers:{
                'Content-Type': 'application/json'
            },
            method:"POST",
            body:JSON.stringify({
                email:"pushpendj@gmail.com",
                pass:"uhvbjzkbvnrd",
                name:"SIEUJFK"
            })
        });
        let data = await response.json()
        console.log(data) 


        
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
                <form onSubmit={this.onSubmit}>
                <center style={{fontSize:'50px',fontWeight:200}}>Login Form</center>
                <div class="field">
                    <label class="label">Email</label>
                    <div class="control">
                        <input id='email' name='email' class="input" type="text" placeholder="Email ID" value={this.state.email} onChange={this.onChange} />
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
