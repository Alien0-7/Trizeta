import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Route, Routes } from 'react-router-dom';

import './styles/index.css'
import Home from './pages/Home.jsx'
import ButtonCollections from './devPages/ButtonCollections.jsx'
import Assistance from './pages/Assistance.jsx'
import Account from './pages/Account.jsx'
import NotFound from './pages/NotFound.jsx'
import Login from './pages/SignUp.jsx';



createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/assistance" element={<Assistance />}/>
        <Route path="/account" element={<Account />}/>
        <Route path="/sign-up" element={<Login />}/>


        <Route path="/dev/buttons" element={<ButtonCollections />}/>

        <Route path="*" element={<NotFound />}/>
      </Routes>
    </BrowserRouter>
  </StrictMode>,
)
