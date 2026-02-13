import React from 'react';
import Banner from '../components/Banner.js';

function LoginPage(){

 
 
    return (
        <div>
            <Banner />
            <h1>Login</h1>
            <label htmlFor="username">Username:</label>
            <input className='userin' type="text" placeholder="Username"></input>
            <br />
            <label htmlFor="password">Password:</label>
            <input className = 'passin' type="password" placeholder="Password"></input>
            <br />
            <button>Login</button>
        
        </div>
    );
}

export default LoginPage