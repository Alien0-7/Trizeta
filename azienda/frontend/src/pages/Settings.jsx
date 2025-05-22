import '../styles/Settings.css';
import useSignOut from 'react-auth-kit/hooks/useSignOut';
import '../styles/Profile.css';
import { useNavigate } from 'react-router-dom';

export default function Settings() {

    const signOut = useSignOut();
    const navigate = useNavigate();

    const logout = () => {
        const confirmed = window.confirm("Sei sicuro di voler effettuare il logout?");
        if (confirmed) {
            signOut();
            navigate("/");
        }
    }

    return (
        <>
            <div className="LOGOUT" onClick={logout}>LOGOUT</div>
        </>
    )
}
