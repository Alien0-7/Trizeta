import '../styles/Settings.css';
import '../styles/Profile.css';
import useSignOut from 'react-auth-kit/hooks/useSignOut';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';

export default function Settings() {

    const signOut = useSignOut();
    const navigate = useNavigate();
    const [showModal, setShowModal] = useState(false);

    const handleLogoutClick = () => {
        setShowModal(true);
    };

    const confirmLogout = () => {
        setShowModal(false);
        signOut();
        navigate("/");
    };

    const cancelLogout = () => {
        setShowModal(false);
    };

    return (
        <>
            <div className="submit logout-button" onClick={handleLogoutClick}>LOGOUT</div>

            {showModal && (
                <div className="modal-overlay">
                    <div className="modal">
                        <h2>Conferma Logout</h2>
                        <p>Sei sicuro di voler uscire?</p>
                        <div className="modal-buttons">
                            <div className="submit confirm-button" onClick={confirmLogout}>Conferma</div>
                            <div className="submit cancel-button" onClick={cancelLogout}>Annulla</div>
                        </div>
                    </div>
                </div>
            )}
        </>
    );
}
