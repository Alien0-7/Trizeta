import React from 'react';
import { Link } from 'react-router-dom';

import '../styles/Header.css'
import reactLogo from '../assets/react.svg'

class Header extends React.Component {

    constructor() {
        super();
    }

    handleClick(option) {
        console.log("/"+option);
    };


    render() {
        return (
            <header>
                <div className="header-items-container">
                    <Link to="/" className='linkTag'>
                        <ul id="logo-container">
                            <li><img id="logo" src={reactLogo} alt="logo"/></li>
                            <li><p>Karim & co.</p></li>
                        </ul>
                    </Link>
                    
                    <ul id="other-btns">
                        <li><Link to="/assistance" className='linkTag'>assistenza</Link></li>
                        <li><Link to="/sign-up" className='linkTag'>signUp</Link></li>
                    </ul>
                </div>
            </header>
        )
    }
}

export default Header