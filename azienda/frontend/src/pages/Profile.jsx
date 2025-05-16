import useSignOut from 'react-auth-kit/hooks/useSignOut';
import '../styles/Profile.css'
import { useNavigate } from 'react-router-dom';




export default function Profile() {

  const signOut = useSignOut();
  const navigate = useNavigate();

  const logout = () =>{
    signOut();
    navigate("/")
  }


  return (
    <>
      <div className='container'>
        <div className="second-container">
          suca
        </div>    
        <div className="submit-container">
          <div className="LOGOUT" onClick={logout}>Sign up</div>
        </div>
      </div>
    </>
  );
};