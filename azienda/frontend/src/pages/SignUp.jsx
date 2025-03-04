import '../styles/SignUp.css'
import Header from '../frames/Header';
import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';



export default function Login(){

    const[action,setAction] = useState("Sign Up");

    const[email,setEmail] = useState('');

    const[password,setPassword] = useState('');

    const[control_password,setControl_Password] = useState('');
    
    const[email_error,setEmail_Error] = useState('');

    const[password_error,setPassword_Error] = useState('');

    const[confirm_error,setConfirm_Error] = useState('');

    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    const passwordRegex = /^(?=.*[A-Z])(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/;


    const toSingUp = () =>{
        if(action==="Sign Up"){
            if(email===''){
                setEmail_Error('Required field');
            }
            else{
                if(!emailRegex.test(email)){
                    setEmail_Error('Please enter a valid email address.');
                }
                else
                    setEmail_Error('');
            }
            if(password===''){
                setPassword_Error('Required field');
            }
            else{
                if(!passwordRegex.test(password)){
                    setPassword_Error('Password must be at least 8 characters long, contain at least one uppercase letter and one special character.');
                }
                else
                    setPassword_Error('');
            }
            if(password!==control_password){
                setConfirm_Error('Passwords do not match');
            }
            else
                setConfirm_Error('');
        }
        else{
            setAction("Sign Up");
            setEmail('');
            setPassword('');
            setControl_Password('');
            setEmail_Error('');
            setPassword_Error('');
            
        }
    }
    

    const toLogin = () =>{
        if(action==="Login"){
            if(email===''){
                setEmail_Error('Required field');
            }
            else{
                if(!emailRegex.test(email)){
                    setEmail_Error('Please enter a valid email address.');
                }
                else
                    setEmail_Error('');
            }
            if(password===''){
                setPassword_Error('Required field');
            }
            else{
                if(!passwordRegex.test(password)){
                    setPassword_Error('Password must be at least 8 characters long, contain at least one uppercase letter and one special character.');
                }
                else
                    setPassword_Error('');
            }
        }
        else{
            setAction("Login");
            setEmail('');
            setPassword('');
            setControl_Password('');
            setEmail_Error('');
            setPassword_Error('');
            setConfirm_Error('')
        }
    }
    

    const handleMail = (e) =>{
        setEmail(e.target.value);
    }
    const handlePassword = (e) =>{
        setPassword(e.target.value);
    }
    const handleControl_Password = (e) =>{
        setControl_Password(e.target.value);
    }


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
                        <input type='email' value = {email}   onChange={handleMail} placeholder='Email'/>
                    </div>
                    {email_error && <div className="error">{email_error}</div>}
                    <div className='input'>
                        <input type='password' value = {password} onChange={handlePassword} placeholder='Password'/>
                    </div>
                    {password_error && <div className="error">{password_error}</div>}
                    <div className='input'>
                        <input type='password' value = {control_password} onChange={handleControl_Password} placeholder='Confirm Password'/>
                    </div>
                    {confirm_error && <div className="error">{confirm_error}</div>}
                </div>}


                {action==="Sign Up"?<div></div>:
                <div className="inputs">
                    <div className='input'>
                        <input type='email' value = {email}   onChange={handleMail} placeholder='Email'/>
                    </div>
                    {email_error && <div className="error">{email_error}</div>}
                    <div className='input'>
                        <input type='password' value = {password} onChange={handlePassword}  placeholder='Password'/>
                    </div>
                    {password_error && <div className="error">{password_error}</div>}
                </div>}


                {action==="Sign Up"?<div></div>:<div className="forgot-password">Lost Password? <span><Link to="/forgot-password" className='linkTag'>Click Here!</Link></span></div>}
                <div className="submit-container">
                    <div className={action==="Login"?"submit gray":"submit"} onClick={toSingUp}>Sing up</div>
                    <div className={action==="Sign Up"?"submit gray":"submit"} onClick={toLogin}>Login</div>
                </div>
            </div>    
        </div>
        </>
    );
};
