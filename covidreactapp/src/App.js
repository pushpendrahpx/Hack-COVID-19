import React from 'react';
// import logo from './logo.svg';
import './App.css';
import Navbar from './Components/Navbar';
import Login from './Pages/login';
import Home from './Pages/home';
import Logout from './Pages/logout';

import {BrowserRouter,Switch,Route} from 'react-router-dom'
import Register from './Pages/Register';
import { get } from 'mongoose';
import SearchShops from './Pages/HomeComponents/SearchShops';
function App(){

 
  return(<span>
    
    

    <BrowserRouter>
    
      <Switch>
        <Route path='/' exact component={Login} />
        <Route path='/register' component={Register} />
        <Route path='/home' exact component={Home} />
        <Route path='/logout' component={Logout} />
        {/* <Route path='/home/shops' component={SearchShops} /> */}
      </Switch>
    </BrowserRouter>
  </span>
  );
}

export default App;
