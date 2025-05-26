import { Link } from 'react-router-dom';
import useIsAuthenticated from 'react-auth-kit/hooks/useIsAuthenticated'
import '../styles/Header.css'
import reactLogo from '../assets/react.svg'

export default function Header() {

    const isAuthenticated = useIsAuthenticated();
    
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
                    {isAuthenticated ? (<li><Link to="/profile" className='linkTag'>Statistics</Link></li>):(<div></div>)}
                    <li><Link to="/assistance" className='linkTag'>assistenza</Link></li>
                    {!isAuthenticated ? (
                        <li><Link to="/sign-up" className="linkTag">Login</Link></li>
                    ) : (
                        <li><Link to="/setting" className="linkTag">Settings</Link></li>
                    )}

                </ul>
            </div>
        </header>
    )

}
