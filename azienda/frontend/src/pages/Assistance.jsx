import styles from '../styles/Assistance.module.css'

export default function Assistance() {
  return (
    <div className={styles.centerDiv}>
        <div className={styles.firstContainer}>
          <div className={styles.assistance}>
              <h1>Assistenza</h1>
              <p>Hai una domanda o un problema? Il nostro Centro Assistenza è sempre al tuo fianco, pronto a risolvere ogni esigenza con rapidità e professionalità. </p>
              <button>vai al centro assistenza</button>
          </div>
          <div className={styles.contacts}>
              <h1>Contattaci</h1>
              <p>Ogni tua richiesta è importante per noi. Il nostro team è pronto a risponderti con rapidità e attenzione, quando ne hai bisogno. </p>
              <button className='three-d-button'>Contattaci</button>
          </div>
        </div>
    </div>
  );
};