import { useState } from 'react'
import './styles/App.css'
import { BrowserRouter, Routes, Route} from "react-router-dom"
import ButtonsCollection from './devPages/ButtonCollections';
import Home from './pages/Home';


function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <div>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Home />}/>
            <Route path="/buttons" element={<ButtonsCollection />}/>
          </Routes>
        </BrowserRouter>
      </div>
    </>
  )
}

export default App
