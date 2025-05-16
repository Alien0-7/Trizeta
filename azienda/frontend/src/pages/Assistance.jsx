import styles from '../styles/Assistance.module.css'

export default function Assistance() {
  return (
    <div className={styles.centerDiv}>
        <div className={styles.firstContainer}>
          <div className={styles.assistance}>
              <h1>Assistenza</h1>
              <p>Hai bisogno di aiuto? Il nostro team è a tua disposizione per rispondere a qualsiasi domanda e 
                offrirti supporto rapido ed efficace. Consulta le FAQ o contattaci subito! </p>
              <button>Vai al centro assistenza</button>
          </div>
        </div>
    </div>
  );
};