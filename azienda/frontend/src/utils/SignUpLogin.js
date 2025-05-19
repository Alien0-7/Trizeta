import axios from "axios";

export async function SignUpApi(email, password,password2,name,surname,address) {

    const url = 'http://trizeta.duckdns.org:10001/api/register';  // Definisci l'URL
    axios.post(url, {
        email: email,
        password1: password,
        password2: password2,
        name: name,
        surname: surname,
        address: address
    }, {
        headers: {
            'Content-Type': 'application/json',
        }
    })
    .then(response => {
        if (!response.ok) {
            // Se la risposta non � ok, lancia un errore
            throw new Error(`Errore nella richiesta: ${response.statusText}`);
        }
        return response.data;
    })
    .then(data => {
        console.log("Dati ricevuti:", data);  // Gestisci i dati ricevuti
    })
    .catch(error => {
        // Stampa l'errore e l'URL in caso di errore
        console.error("Errore nella chiamata API:", error);
        console.log('URL della richiesta:', url);
        console.log('Dettagli errore:', error.message || error);
    });
}

export function LoginApi(email,password){
   
    const url = 'http://trizeta.duckdns.org:10001/api/login';  // Definisci l'URL
    axios.post(url, {
        email: email,
        password: password,
    }, {
        headers: {
            'Content-Type': 'application/json',
        }
    })
    .then(response => {
        if (!response.ok) {
            // Se la risposta non � ok, lancia un errore
            throw new Error(`Errore nella richiesta: ${response.statusText}`);
        }
    })
    .then(data => {
        console.log("Dati ricevuti:", data);  // Gestisci i dati ricevuti
        return response.data();
    })
    .catch(error => {
        // Stampa l'errore e l'URL in caso di errore
        console.error("Errore nella chiamata API:", error);
        console.log('URL della richiesta:', url);
        console.log('Dettagli errore:', error.message || error);
    if (error.response && error.response.status === 400) {
            setError('Mail o Password errati');
    };
    });
}