import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter, Route, Routes } from 'react-router-dom';

import './styles/index.css'
import Header from './frames/Header.jsx';
import Home from './pages/Home.jsx'
import ButtonCollections from './devPages/ButtonCollections.jsx'
import Assistance from './pages/Assistance.jsx'
import Account from './pages/Account.jsx'
import NotFound from './pages/NotFound.jsx'
import Login from './pages/SignUp.jsx';
import Password from './pages/Password.jsx';
import createStore from 'react-auth-kit/createStore';
import AuthProvider from 'react-auth-kit';

const store = createStore({
  authName:'_auth',
  authType:'cookie',
  cookieDomain: window.location.hostname,
  cookieSecure: window.location.protocol === 'https:',
});

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <AuthProvider 
    store = {store}>
      
      <BrowserRouter>
        <div style={{ display: 'flex', flexDirection: 'column', height: '100vh' }}>
          <Header />
          <div style={{ flex: 1, overflowY: 'auto' }}>
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/assistance" element={<Assistance />} />
              <Route path="/account" element={<Account />} />
              <Route path="/sign-up" element={<Login />} />
              <Route path="/forgot-password" element={<Password />} />
              <Route path="/dev/buttons" element={<ButtonCollections />} />
              <Route path="*" element={<NotFound />} />
            </Routes>
          </div>
        </div>
      </BrowserRouter>
    </AuthProvider>
  </StrictMode>
);