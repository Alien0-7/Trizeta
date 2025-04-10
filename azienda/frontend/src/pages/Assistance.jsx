import Header from '../frames/Header.jsx'
import styles from '../styles/Assistance.module.css'

export default function Assistance() {
  return (
    <div className={styles.centerDiv}>
        <div className={styles.firstContainer}>
          <div className={styles.assistance}>
              <h1>Assistenza</h1>
              <p>Se stai avendo difficoltà ad utilizzare i nostri prodotti, sul nostro Centro Assistenza puoi trovare guide e suggerimenti che possono aiutarti a risolvere il tuo problema: </p>
              <button>vai al centro assistenza</button>
          </div>
          <div className={styles.contacts}>
              <h1>Contattaci</h1>
              <p>se non trovi una trovi una risposta sul nostro Help Center, contatta un nostro operatore. Ti faremo qualche domanda preventiva per inquadrare i dettagli della tua problematica o richiesta: </p>
              <button className='three-d-button'>Contattaci</button>
          </div>
        </div>
    </div>
  );
};