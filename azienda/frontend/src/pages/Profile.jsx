import '../styles/Profile.css'
import {Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement, 
  LineElement,
  Title,
  Tooltip,
  Legend} from "chart.js";
import { useState, useEffect, useRef } from 'react';
import {Bar, Doughnut, Line} from "react-chartjs-2";
import { TemperatureAPI, HumidityAPI, Co2API} from '../utils/InformationRequester'
import useAuthHeader from 'react-auth-kit/hooks/useAuthHeader';
import useIsAuthenticated from 'react-auth-kit/hooks/useIsAuthenticated'

ChartJS.register(
  CategoryScale, // Scala delle categorie (per le etichette)
  LinearScale, // Scala lineare (per il valore numerico sull'asse Y)
  LineElement, // Elemento lineare per il grafico a linee
  PointElement,
  Title, // Titolo del grafico
  Tooltip, // Tooltip
  Legend // Legenda
);

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
    const temp = []
    data.temperature.map(item => {
    const t = parseInt(item.time.split(' ')[1].split(':')[0]) * 60 + parseInt(item.time.split(' ')[1].split(':')[1]);            
    temp.push({'time':t,'value':item.value});
    });
    setTemperatureData(temp);
    }
    fetchData();
    hasRun.current = true;
  }, []);


  return (
    <>
      <div className='body-container'>
        {temperatureData && <div id='temperature'>
          {<Line
            data={{
              labels: temperatureData.map((data)=> data.time),
              datasets: [
                {
                  labels: "Temperature",
                  data: temperatureData.map((data)=> data.value*1),
                  backgroundColor: "#064FF0",
                  borderColor: "#064FF0",
                },
              ],
            }}
            options={{
              responsive: true,
              plugins: {
                title: {
                display: true,
                text: 'Temperature Over Time',
                },
              tooltip: {
                callbacks: {
                  label: function(tooltipItem) {
                    return `${tooltipItem.raw}°C`; 
                  },
                },
              },
            },
            scales: {
              x: {
                type: 'category', 
                labels: temperatureData.map((data) => data.time), 
              },
              y: {
                type: 'linear',
                min: 26, 
                max: 27,
              },
            },
            }}
          />}
        </div>}
        {humidityData && <div id='humidity'>{"Humidity"}</div>}
        {airData && <div id='airquality'>{"Air quality"}</div>}
        {Co2Data && <div id='co2'>{"Co2"}</div>}
      </div>
    </>
  );
};