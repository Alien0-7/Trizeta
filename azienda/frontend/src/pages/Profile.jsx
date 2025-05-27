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
import { TemperatureAPI, HumidityAPI, Co2API, AiAPI, ButtonAPI } from '../utils/InformationRequester'
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
  const [temperatureAi, setTemperatureAi] = useState(null);
  const [humidityData, setHumidityData] = useState(null);
  const [humidityAi, setHumidityAi] = useState(null);
  const [airData, setAirData] = useState(null);
  const [Co2Data, setCo2Data] = useState(null);
  const [Co2Ai, setCo2Ai] = useState(null);

  const toAPI = () => {
    const box = document.getElementById('statusBox');
    if (box.classList.contains('off')) {
      box.classList.remove('off');
      box.classList.add('on');
      box.textContent = 'ON';
      //ButtonAPI(authHeader,'true');
    } else {
      box.classList.remove('on');
      box.classList.add('off');
      box.textContent = 'OFF';
      //ButtonAPI(authHeader,'false');
    }
  }


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
      const media = somma / co2.length;
      setAirData(media);

      const dataAI = await AiAPI(authHeader, 'T');
      const ai = []
      dataAI.predicted_values.map(item => {
        const a = parseInt(item.timeStr.split(' ')[1].split(':')[0]) * 60 + parseInt(item.timeStr.split(' ')[1].split(':')[1]);
        ai.push({ 'timeStr': a, 'value': item.value });
      });
      const aiMap = new Map(ai.map((d) => [d.timeStr, d.value]));
      const temperatureAiAligned = temp.map((entry) => ({
        value: aiMap.get(entry.timeStr) ?? null,
      }))
      setTemperatureAi(temperatureAiAligned);

      const dataAI2 = await AiAPI(authHeader, 'H');
      const ai2 = []
      dataAI2.predicted_values.map(item => {
        const h = parseInt(item.timeStr.split(' ')[1].split(':')[0]) * 60 + parseInt(item.timeStr.split(' ')[1].split(':')[1]);
        ai2.push({ 'timeStr': h, 'value': item.value });
      });
      const aiMap1 = new Map(ai2.map((d) => [d.timeStr, d.value]));
      const humidityAiAligned = humid.map((entry) => ({
        value: aiMap1.get(entry.timeStr) ?? null,
      }))
      setHumidityAi(humidityAiAligned);


      const dataAI3 = await AiAPI(authHeader, 'C');
      const ai3 = []
      dataAI3.predicted_values.map(item => {
        const c = parseInt(item.timeStr.split(' ')[1].split(':')[0]) * 60 + parseInt(item.timeStr.split(' ')[1].split(':')[1]);
        ai3.push({ 'timeStr': c, 'value': item.value });
      });
      const aiMap2 = new Map(ai3.map((d) => [d.timeStr, d.value]));
      const Co2AiAligned = co2.map((entry) => ({
        value: aiMap2.get(entry.timeStr) ?? null,
      }))
      setCo2Ai(Co2AiAligned);
    }
    fetchData();
    hasRun.current = true;
  }, []);


  return (
    <>
      <div className="graphs-wrapper">
        <div className="graphContainer">
          {temperatureData && temperatureAi && (
            <div id='temperature'>
              <h3 className="card-title">Temperature</h3>
              <Line
                data={{
                  labels: temperatureData.map((data) => data.timeStr),
                  datasets: [
                    {
                      label: "Temperature",
                      data: temperatureData.map((data) => data.value),
                      backgroundColor: "#064FF0",
                      borderColor: "#064FF0",
                    },
                    {
                      label: "TemperatureAI",
                      data: temperatureAi.map((data) => data.value),
                      backgroundColor: "#00C49F",
                      borderColor: "#00C49F",
                    },
                  ],
                }}
                options={{
                  spanGaps: true,
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
                      display: true,
                    },
                  },
                  scales: {
                    x: {
                      type: 'category',
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
          {humidityData && humidityAi && (
            <div id='humidity'>
              <h3 className="card-title">Humidity</h3>
              <Line
                data={{
                  labels: humidityData.map((data) => data.timeStr),
                  datasets: [
                    {
                      label: "Humidity",
                      data: humidityData.map((data) => data.value),
                      backgroundColor: "#064FF0",
                      borderColor: "#064FF0",
                    },
                    {
                      label: "HumidityAI",
                      data: humidityAi.map((data) => data.value),
                      backgroundColor: "#00C49F",
                      borderColor: "#00C49F",
                    },
                  ],
                }}
                options={{
                  spanGaps: true,
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
                      display: true,
                    },
                  },
                  scales: {
                    x: {
                      type: 'category',
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
          {Co2Data && Co2Ai && (
            <div id='Co2'>
              <h3 className="card-title">Co2</h3>
              <Line
                data={{
                  labels: Co2Data.map((data) => data.timeStr),
                  datasets: [
                    {
                      label: "Co2",
                      data: Co2Data.map((data) => data.value),
                      backgroundColor: "#064FF0",
                      borderColor: "#064FF0",
                    },
                    {
                      label: "Co2AI",
                      data: Co2Ai.map((data) => data.value),
                      backgroundColor: "#00C49F",
                      borderColor: "#00C49F",
                      spanGaps: false,
                    },
                  ],
                }}
                options={{
                  spanGaps: true,
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
                      display: true,
                    },
                  },
                  scales: {
                    x: {
                      type: 'category',
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
            <div className="co2-table">
              <h3 className="card-title">Qualità attuale dell'aria</h3>
              {[
                { range: "< 600 ppm", quality: "Eccellente", desc: "Aria fresca, buona ventilazione", color: "blue", min: 0, max: 600 },
                { range: "600 – 1.000 ppm", quality: "Buona", desc: "Accettabile per ambienti interni", color: "green", min: 600, max: 1000 },
                { range: "1.000 – 1.400 ppm", quality: "Moderata", desc: "Possibile sensazione di aria viziata", color: "yellow", min: 1000, max: 1400 },
                { range: "1.400 – 2.000 ppm", quality: "Scarsa", desc: "Qualità dell’aria bassa, necessità di ventilazione", color: "red", min: 1400, max: 2000 },
                { range: "> 2.000 ppm", quality: "Pessima", desc: "Malesseri possibili, ventilazione urgente", color: "purple", min: 2000, max: Infinity },
              ]
                .filter(item => airData >= item.min && airData < item.max)
                .map((item, idx) => (
                  <div key={idx} className={`co2-box ${item.color}`}>
                    <p className="range">{item.range}</p>
                    <p className="quality">{item.quality}</p>
                    <p className="desc">{item.desc}</p>
                  </div>
                ))}
            </div>
          )}
        </div>
        <button onClick={toAPI}>Fan</button>
        <div id="statusBox" className="status-box off">OFF</div>
      </div>
    </>
  );
};

