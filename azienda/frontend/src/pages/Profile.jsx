import '../styles/Profile.css'
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
} from "chart.js";
import { useState, useEffect, useRef } from 'react';
import { Bar, Doughnut, Line } from "react-chartjs-2";
import { TemperatureAPI, HumidityAPI, Co2API } from '../utils/InformationRequester'
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
  const [temperatureData, setTemperatureData] = useState(null);
  const [humidityData, setHumidityData] = useState(null);
  const [airData, setAirData] = useState(null);
  const [Co2Data, setCo2Data] = useState(null);



  useEffect(() => {
    if (hasRun.current) return;
    async function fetchData() {
      const data = await TemperatureAPI(authHeader);
      const temp = []
      data.temperature.map(item => {
        const t = parseInt(item.timeStr.split(' ')[1].split(':')[0]) * 60 + parseInt(item.timeStr.split(' ')[1].split(':')[1]);
        temp.push({ 'timeStr': t, 'value': item.value });
      });
      setTemperatureData(temp);
      const data2 = await HumidityAPI(authHeader);
      const humid = []
      data2.humidity.map(item => {
        const h = parseInt(item.timeStr.split(' ')[1].split(':')[0]) * 60 + parseInt(item.timeStr.split(' ')[1].split(':')[1]);
        humid.push({ 'timeStr': h, 'value': item.value });
      });
      setHumidityData(humid);
      const data3 = await Co2API(authHeader);
      const co2 = []
      data3.Co2.map(item => {
        const c = parseInt(item.timeStr.split(' ')[1].split(':')[0]) * 60 + parseInt(item.timeStr.split(' ')[1].split(':')[1]);
        co2.push({ 'timeStr': c, 'value': item.value });
      });
      setCo2Data(co2);
      const somma = co2.reduce((accumulatore, elemento) => accumulatore + elemento.value, 0);
      const media = somma/co2.length;
      setAirData(media);
    }
    fetchData();
    hasRun.current = true;
  }, []);


  return (
    <>
      <div className="graphs-wrapper">
        <div className="graphContainer">
          {temperatureData && (
            <div id='temperature'>
              <h3 className="card-title">Temperature</h3>
              <Line
                data={{
                  labels: temperatureData.map((data) => data.timeStr),
                  datasets: [
                    {
                      label: "Temperature",
                      data: temperatureData.map((data) => data.value * 1),
                      backgroundColor: "#064FF0",
                      borderColor: "#064FF0",
                    },
                  ],
                }}
                options={{
                  responsive: true,
                  maintainAspectRatio: false,
                  plugins: {
                    title: {
                      display: false,
                    },
                    tooltip: {
                      callbacks: {
                        label: function (tooltipItem) {
                          return `${tooltipItem.raw}°C`;
                        },
                      },
                    },
                    legend: {
                      display: false, 
                    },
                  },
                  scales: {
                    x: {
                      type: 'category',
                      labels: temperatureData.map((data) => data.timeStr),
                    },
                    y: {
                      type: 'linear',
                      min: 25,
                      max: 30,
                    },
                  },
                }}
              />
            </div>
          )}
        </div>

        <div className="graphContainer">
          {humidityData && (
            <div id='humidity'>
              <h3 className="card-title">Humidity</h3>
              <Line
                data={{
                  labels: humidityData.map((data) => data.timeStr),
                  datasets: [
                    {
                      label: "Humidity",
                      data: humidityData.map((data) => data.value * 1),
                      backgroundColor: "#064FF0",
                      borderColor: "#064FF0",
                    },
                  ],
                }}
                options={{
                  responsive: true,
                  maintainAspectRatio: false,
                  plugins: {
                    title: {
                      display: false,
                    },
                    tooltip: {
                      callbacks: {
                        label: function (tooltipItem) {
                          return `${tooltipItem.raw}%`;
                        },
                      },
                    },
                    legend: {
                      display: false, 
                    },
                  },
                  scales: {
                    x: {
                      type: 'category',
                      labels: humidityData.map((data) => data.timeStr),
                    },
                    y: {
                      type: 'linear',
                      min: 0,
                      max: 100,
                    },
                  },
                }}
              />
            </div>
          )}
        </div><div className="graphContainer">
          {Co2Data && (
            <div id='Co2'>
              <h3 className="card-title">Co2</h3>
              <Line
                data={{
                  labels: Co2Data.map((data) => data.timeStr),
                  datasets: [
                    {
                      label: "Temperature",
                      data: Co2Data.map((data) => data.value * 1),
                      backgroundColor: "#064FF0",
                      borderColor: "#064FF0",
                    },
                  ],
                }}
                options={{
                  responsive: true,
                  maintainAspectRatio: false,
                  plugins: {
                    title: {
                      display: false,
                    },
                    tooltip: {
                      callbacks: {
                        label: function (tooltipItem) {
                          return `${tooltipItem.raw}PPM`;
                        },
                      },
                    },
                    legend: {
                      display: false, 
                    },
                  },
                  scales: {
                    x: {
                      type: 'category',
                      labels: Co2Data.map((data) => data.timeStr),
                    },
                    y: {
                      type: 'linear',
                      min: 0,
                      max: 1500,
                    },
                  },
                }}
              />
            </div>
          )}
        </div><div className="graphContainer">
          {airData && (
            <div id = "Air">
              
            </div>
          )}
        </div>        
      </div>
    </>
  );
};

