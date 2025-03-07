import '../styles/SignUp.css'
import Header from '../frames/Header';
import { useState } from 'react';
import { Link } from 'react-router-dom';




export default function Login(){

    const[action,setAction] = useState("Sign Up")

    return(
        <>
        <Header></Header>
        </>
    );
};