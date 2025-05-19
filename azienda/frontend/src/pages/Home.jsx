import {Settings, Shield, Cpu, Wrench, Sliders } from 'lucide-react';

export default function Home() {
  return (
    
    <>
    
       <div className="home-container">
      {/* Sezione Chi Siamo */}
      <section className="chi-siamo">
        <div className="container">
          <h2>Chi Siamo</h2>
          <p>
            Siamo un team di ingegneri, architetti e sviluppatori con una missione: trasformare le abitazioni tradizionali
            in ambienti smart, sostenibili e sicuri. Offriamo soluzioni domotiche su misura, integrando tecnologia,
            comfort ed efficienza energetica.
          </p>
        </div>
      </section>

      {/* Sezione Slideshow */}
      <section className="slideshow-section">
        <div className="slideshow-container">
          <div className="slides">
            <img src="src/images/domotica1.jpg" alt="Domotica 1" />
            <img src="src/images/domotica2.jpg" alt="Domotica 2" />
            <img src="src/images/domotica3.jpg" alt="Domotica 3" />
            <img src="src/images/domotica4.jpg" alt="Domotica 4" />
            <img src="src/images/domotica5.jpg" alt="Domotica 5" />

            <img src="src/images/domotica1.jpg" alt="Domotica 1" />
            <img src="src/images/domotica2.jpg" alt="Domotica 2" />
            <img src="src/images/domotica3.jpg" alt="Domotica 3" />
            <img src="src/images/domotica4.jpg" alt="Domotica 4" />
            <img src="src/images/domotica5.jpg" alt="Domotica 5" />
          </div>
        </div>
      </section>
    </div>

    <div className="servizi-grid">

  <div className="servizio-card">
    <Cpu size={48} strokeWidth={1.5} className="icon" />
    <h3>Efficienza Energetica</h3>
    <p>Monitoraggio dei consumi e ottimizzazione per un’abitazione sostenibile e intelligente.</p>
  </div>

  <div className="servizio-card">
    <Settings size={48} strokeWidth={1.5} className="icon" />
    <h3>Integrazione Smart Home</h3>
    <p>Compatibilità con Alexa, Google Home, KNX e altri sistemi per un ecosistema intelligente e centralizzato.</p>
  </div>

  <div className="servizio-card">
    <Sliders size={48} strokeWidth={1.5} className="icon" />
    <h3>Automazione Luci, Clima, Tapparelle e Irrigazione</h3>
    <p>Gestione centralizzata e scenari personalizzati per ogni ambiente della casa.</p>
  </div>

  <div className="servizio-card">
    <Wrench size={48} strokeWidth={1.5} className="icon" />
    <h3>Assistenza e Manutenzione</h3>
    <p>Supporto tecnico dedicato e interventi tempestivi per garantire continuità e sicurezza.</p>
  </div>
</div>


    </>
  );
};