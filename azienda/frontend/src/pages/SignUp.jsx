import '../styles/SignUp.css'
import Header from '../frames/Header';
import { useState } from 'react';




export default function Login(){

    const[action,setAction] = useState("Sign Up")

    return(
        <>
        <Header></Header>
        <div className='container'>
            <div className="second-container">
                <div className='header'>
                    <div className='text'>{action}</div>
                    <div className='underline'></div>
                </div>



                {action==="Login"?<div></div>:
                <div className='inputs'>
                    <div className='input'>
                        <input type='email' placeholder='Email'/>
                    </div>
                    <div className='input'>
                        <input type='password' placeholder='Password'/>
                    </div>
                    <div className='input'>
                        <input type='password' placeholder='Confirm Password'/>
                    </div>
                </div>}

                {action==="Sign Up"?<div></div>:
                <div className="inputs">
                    <div className='input'>
                        <input type='email' placeholder='Email'/>
                    </div>
                    <div className='input'>
                        <input type='password' placeholder='Password'/>
                    </div>
                </div>}




                {action==="Sign Up"?<div></div>:<div className="forgot-password">Lost Password? <span>Click Here!</span></div>}
                <div className="submit-container">
                    <div className={action==="Login"?"submit gray":"submit"} onClick={()=>{setAction("Sign Up")}}>Sing up</div>
                    <div className={action==="Sign Up"?"submit gray":"submit"} onClick={()=>{setAction("Login")}}>Login</div>
                </div>
            </div>    
        </div>
        </>
    );
};