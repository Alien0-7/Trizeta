import axios from "axios";

export function SignUpApi(email, password) {
    const url = 'http://185.58.120.179:10002/api/create';  // Definisci l'URL

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            type: singup,
            email: email,
            password: password
        })
    })
    .then(response => {
        if (!response.ok) {
            // Se la risposta non � ok, lancia un errore
            throw new Error(`Errore nella richiesta: ${response.statusText}`);
        }
        return response.json(); // Se la risposta � OK, parsifica la risposta come JSON
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
};


export function LoginApi(email, password){
    const url = 'http://185.58.120.179:10002/api/create';  // Definisci l'URL
    
    fetch(url, {
        method: 'Post',
        headers: {
            'Content-type': 'application/json',
        },
        body: JSON.stringify({
            type:login,
            email:email,
            password:password
        })
    })
    .then(response => {
        if (!response.ok) {
            // Se la risposta non � ok, lancia un errore
            throw new Error(`Errore nella richiesta: ${response.statusText}`);
        }
        return response.json(); // Se la risposta � OK, parsifica la risposta come JSON
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
};



