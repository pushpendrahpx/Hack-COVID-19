import React, { Component } from 'react'
import { Redirect } from 'react-router-dom';

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

    onSubmit = (e)=>{
        e.preventDefault();

        let email = e.target.email.value;
        let password = e.target.password.value;

        if(email == 'A' && password == 'A')
        {

            localStorage.setItem("token","ygfehbkerbdjlobjsdgfioaejknfdekjrdgn");
            
            this.setState({loggedIn:true})
        }
        
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
            return (<form onSubmit={this.onSubmit}>
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
                    
                    <div class="control">
    <button class="button is-primary">Submit</button>
    </div>
            </form>);
        }
    }



    
}

export default login
