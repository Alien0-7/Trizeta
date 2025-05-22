import '../styles/SignUp.css'
import { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { SignUpApi, LoginApi} from '../utils/SignUpLogin.js';
import { Eye, EyeOff } from 'lucide-react';
import useSignIn from 'react-auth-kit/hooks/useSignIn';

export default function Login(){

    const[action,setAction] = useState("Login");

    const[email,setEmail] = useState('');

    const[password,setPassword] = useState('');

    const[control_password,setControl_Password] = useState('');
    
    const[email_error,setEmail_Error] = useState('');

    const[password_error,setPassword_Error] = useState('');

    const[confirm_error,setConfirm_Error] = useState('');

    const[name,setName] = useState('');

    const[surname,setSurname] = useState('');

    const[address,setAddress] = useState('');

    const[name_error,setName_Error] = useState('');

    const[surname_error,setSurname_Error] = useState('');

    const[address_error,setAddress_Error] = useState('');
     
    const[return_error,setReturn_Error] = useState('');

    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    const passwordRegex = /^(?=.*[A-Z])(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/;

    const signIn = useSignIn();
    const navigate = useNavigate();

    const toSingUp = () =>{
        if(action==="Sign Up"){
            let control = false;
            if(email===''){
                setEmail_Error('Required field');
                control=true
            }
            else{
                if(!emailRegex.test(email)){
                    setEmail_Error('Please enter a valid email address.');
                    control=true
                }
                else
                    setEmail_Error('');
            };
            if(password===''){
                setPassword_Error('Required field');
                control=true
            }
            else{
                if(!passwordRegex.test(password)){
                    setPassword_Error('Password must be at least 8 characters long, contain at least one uppercase letter and one special character.');
                    control=true
                }
                else
                    setPassword_Error('');
            };
            if(control_password===''){
                setConfirm_Error('Required field')
                control=true
            }
            else{
                if(password!==control_password){
                    setConfirm_Error('Passwords do not match');
                    control=true
                }
                else
                    setConfirm_Error('');
            }
            
            if(name===''){
                setName_Error('Required field');
                control=true
            }
            else{
                setName_Error('');
            }
            if(surname===''){
                setSurname_Error('Required field');
                control=true
            }
            else{
                setSurname_Error('');
            }
            if(address===''){
                setAddress_Error('Required field');
                control=true
            }
            else{
                setAddress_Error('');
            }
            if(control===false){
                const data = SignUpApi(email,password,control_password,name,surname,address);
                if (!data || !data.token || !data.email) {
                    throw new Error("Dati incompleti ricevuti dal server");
                }
                else{
                    signIn({
                        token: data.token,
                        expireIn: 259200,
                        tokenType: "Bearer",
                        authState: {email: data.email},
                    });
                    navigate('profile')
                }
            };
        }
        else{
            setAction("Sign Up");
            setEmail('');
            setPassword('');
            setControl_Password('');
            setEmail_Error('');
            setPassword_Error('');
            setName('');
            setSurname('');
            setAddress('');
            setName_Error('');
            setSurname_Error('');
            setAddress_Error('');
            setReturn_Error('');
        };
    };
    

    const toLogin = () =>{
        if(action==="Login"){
            let control = false;
            if(email===''){
                setEmail_Error('Required field');
                control = true;
            }
            else{
                if(!emailRegex.test(email)){
                    setEmail_Error('Please enter a valid email address.');
                    control = true;
                }
                else
                    setEmail_Error('');
            };
            if(password===''){
                setPassword_Error('Required field');
                control = true;
            }
            else{
                if(!passwordRegex.test(password)){
                    setPassword_Error('Password must be at least 8 characters long, contain at least one uppercase letter and one special character.');
                    control = true;
                }
                else
                    setPassword_Error('');
            };
            if(control === false){
                const data = LoginApi(email,password);
                if (!data || !data.token || !data.email) {
                    throw new Error("Dati incompleti ricevuti dal server");
                }
                else{
                    signIn({
                        token: data.token,
                        expireIn: 259200,
                        tokenType: "Bearer",
                        authState: {email: data.email},
                    });
                    navigate('profile')
                }
            };
        }
        else{
            setAction("Login");
            setEmail('');
            setPassword('');
            setControl_Password('');
            setEmail_Error('');
            setPassword_Error('');
            setConfirm_Error('');
            setName_Error('');
            setSurname_Error('');
            setAddress_Error('');
        };
    };
    

    const handleMail = (e) =>{
        setEmail(e.target.value);
    };
    const handlePassword = (e) =>{
        setPassword(e.target.value);
    };
    const handleControl_Password = (e) =>{
        setControl_Password(e.target.value);
    };
    const handleName = (e) =>{
        setName(e.target.value);  
    };
    const handleSurname = (e) =>{
        setSurname(e.target.value);
    };
    const handleAddress = (e) =>{
        setAddress(e.target.value);
    };
    function handleKeyPress(e){
        if (e.key === 'Enter') {
          if(action==="Login"){
            toLogin();
          }
          else{
            toSingUp();
          }
        };
    };
    window.addEventListener('keydown',handleKeyPress);
    

    return(
        <>
        <div className='container'>
            <div className="second-container">
                <div className='header'>
                    <div className='text'>{action}</div>
                    <div className='underline'></div>
                </div>

                <div className='inputs'>
                    <div className='input'>
                        <input type='email' value = {email}   onChange={handleMail} placeholder='Email'/>
                    </div>
                    {email_error && <div className="error">{email_error}</div>}
                    <div className='input'>
                        <input type='password' value = {password} onChange={handlePassword} placeholder='Password'/>
                    </div>
                    {password_error && <div className="error">{password_error}</div>}
                    {return_error && <div className="error">{return_error}</div>}
                    {action==="Sign Up"&&
                    <>
                    <div className='input'>
                        <input type='password' value = {control_password} onChange={handleControl_Password} placeholder='Confirm Password'/>
                    </div>
                    {confirm_error && <div className="error">{confirm_error}</div>}
                    <div className='input'>
                        <input type='text' value = {name} onChange={handleName} placeholder='Name'/>
                    </div>
                    {name_error && <div className="error">{name_error}</div>}
                    <div className='input'>
                        <input type='text' value = {surname} onChange={handleSurname} placeholder='Surname'/>
                    </div>
                    {surname_error && <div className="error">{surname_error}</div>}
                    <div className='input'>
                        <input type='text' value = {address} onChange={handleAddress} placeholder='Address'/>
                    </div>
                    {address_error && <div className="error">{address_error}</div>}
                    </>
                    }
                </div>
                {action==='Login'&&<div className="forgot-password">Lost Password? <span><Link to="/forgot-password" className='linkTag'>Click Here!</Link></span></div>}

                <div className="submit-container">
                    <div className={action==="Login"?"submit gray":"submit"} onClick={toSingUp}>Sign up</div>
                    <div className={action==="Sign Up"?"submit gray":"submit"} onClick={toLogin}>Login</div>
                </div>
            </div>    
        </div>
        </>
    );
};
