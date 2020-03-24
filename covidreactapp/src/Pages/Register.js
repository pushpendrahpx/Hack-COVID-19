import React, { Component } from 'react'
import { Link } from 'react-router-dom';

class Register extends Component {
    constructor(props) {
        super(props)

        this.state = {
                name:'',
                email:'',
                phone:'',
                password:''
        }
    }
    onSubmit = async (e)=>{
        e.preventDefault();

        let email = e.target.email.value;
        let password = e.target.password.value;
        let name = e.target.name.value;
        let phone = e.target.phone.value;


        if(email == 'A' && password == 'A')
        {

            localStorage.setItem("token","ygfehbkerbdjlobjsdgfioaejknfdekjrdgn");

            this.setState({loggedIn:true})
        }

        console.log(email,password,phone,name)

        let response = await fetch('/api/users/register',{
            headers:{
                'Content-Type': 'application/json'
            },
            method:"POST",
            body:JSON.stringify({
                name:name,
                email:email,
                phone:phone,
                password:password
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
        return (<form onSubmit={this.onSubmit}>
            <center style={{fontSize:'50px',fontWeight:200}}>Register Form</center>
            <div class="field">
                <label class="label">Name</label>
                <div class="control">
                    <input id='name' name='name' class="input" type="text" placeholder="Enter your Full Name" value={this.state.name} onChange={this.onChange} />
                </div>
                {/* <p class="help">This is a help text</p> */}
            </div>

            
            <div class="field">
                <label class="label">Email</label>
                <div class="control">
                    <input id='email' name='email' class="input" type="email" placeholder="Email ID" value={this.state.email} onChange={this.onChange} />
                </div>
                {/* <p class="help">This is a help text</p> */}
            </div>

            <div class="field">
                <label class="label">Phone</label>
                <div class="control">
                    <input id='phone' name='phone' class="input" type="number" placeholder="Enter your Phone" value={this.state.phone} onChange={this.onChange} />
                </div>
                <p class="help">An OTP will be sent to given mobile number</p>
            </div>

                
            <div class="field">
                <label class="label">Password</label>
                <div class="control">
                    <input id='password' name='password' class="input" type="password" placeholder="Password"  value={this.state.password} onChange={this.onChange}  />
                </div>
                {/* <p class="help">This is a help t/ext</p> */}
            </div>
            <Link to='/'>Already have an Account</Link>
                
                <div class="control">
                <center><button class="button is-primary">Submit</button></center>
                </div>
        </form>)
    }
}

export default Register
