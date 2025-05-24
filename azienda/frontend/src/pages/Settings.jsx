import '../styles/Settings.css';
import useSignOut from 'react-auth-kit/hooks/useSignOut';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';

export default function Settings() {
    const signOut = useSignOut();
    const navigate = useNavigate();
    const [showModal, setShowModal] = useState(false);

    const user = {
        email: 'user@example.com',
        password: '********',
        name: 'Mario',
        surname: 'Rossi',
        address: 'Via Roma 123, Milano'
    };

    const handleLogoutClick = () => setShowModal(true);
    const confirmLogout = () => {
        setShowModal(false);
        signOut();
        navigate("/");
        window.location.reload();
    };
    const cancelLogout = () => setShowModal(false);

    return (
        <>
            <div className="container">
                <div className="header">
                    <h1 className="text">Impostazioni Utente</h1>
                    <div className="underline"></div>
                </div>
                <div className="inputs">
                    <div className="input"><strong>Nome:</strong> {user.name}</div>
                    <div className="input"><strong>Cognome:</strong> {user.surname}</div>
                    <div className="input"><strong>Email:</strong> {user.email}</div>
                    <div className="input"><strong>Password:</strong> {user.password}</div>
                    <div className="input"><strong>Indirizzo:</strong> {user.address}</div>
                </div>

                <div className="submit-container">
                    <div className="submit logout-button" onClick={handleLogoutClick}>LOGOUT</div>
                </div>
            </div>

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
