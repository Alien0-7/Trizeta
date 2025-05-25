import '../styles/Profile.css'
import {Chart as ChartJS} from "chart.js";
import { useState, useEffect, useRef } from 'react';
import {Bar, Doughnut, Line} from "react-chartjs-2";
import { TemperatureAPI, HumidityAPI, Co2API} from '../utils/InformationRequester'
import useAuthHeader from 'react-auth-kit/hooks/useAuthHeader';
import useIsAuthenticated from 'react-auth-kit/hooks/useIsAuthenticated'



export default function Profile() {

  const isAuthenticated = useIsAuthenticated();
  const authHeader = useAuthHeader();
  const hasRun = useRef(false);
  const [temperatureData,setTemperatureData] = useState(null);
  const [humidityData,setHumidityData] = useState(null);
  const [airData,setAirData] = useState(null);
  const [Co2Data,setCo2Data] = useState(null);
  


  useEffect(() => {
    if (hasRun.current) return;
    async function fetchData() {
    const data = await TemperatureAPI(authHeader);
    setTemperatureData(data);
    console.log(data['temperature'][0]['value'])
    const data2 = await HumidityAPI(authHeader);
    setHumidityData(data2);
    console.log(data2['humidity'][0]['value'])
    const data3 = await Co2API(authHeader);
    setCo2Data(data3);
    console.log(data3['Co2'][0]['value'])
    setAirData(data3);
    }
    fetchData();
    hasRun.current = true;
  }, []);





  return (
    <>
      <div className='body-container'>
        {temperatureData && <div id='temperature'>{"Temperatura"}</div>}
        {humidityData && <div id='humidity'>{"Humidity"}</div>}
        {airData && <div id='airquality'>{"Air quality"}</div>}
        {Co2Data && <div id='co2'>{"Co2"}</div>}
      </div>
    </>
  );
};