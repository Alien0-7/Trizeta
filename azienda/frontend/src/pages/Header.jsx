import '../styles/Header.css'
import reactLogo from '../assets/react.svg'

function Header(){
    return(
        <header>
            <div class="header-items-container">
                <ul id="logo-container">
                    <li><img id="logo" src={reactLogo} alt="logo"/></li>
                    <li>Karim & co.</li>
                </ul>
                
                <ul id="other-btns">
                    <li><a href="">contacts</a></li>
                    <li><a href="">sign up</a></li>
                </ul>
            </div>
        </header>
    ); 
}

export default Header