import '../styles/Profile.css'
import {Chart as ChartJS} from "chart.js";
import {Bar,Doughnut,Line} from "react-chartjs-2";



export default function Profile() {

  return (
    <>
      <div className='body-container'>
        <div id='temperature'>Temperature</div>
        <div id='humidity'>Humidity</div>
        <div id='airquality'>Air quality</div>
        <div id='co2'>Co2</div>
      </div>
    </>
  );
};