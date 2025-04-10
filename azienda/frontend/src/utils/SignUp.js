import axios from "axios";

export function SignUpApi(email, password,confirm,name,surname,address) {
    const url = 'http://2.37.63.53:10004/api/register';  // Definisci l'URL

    axios.post(url, {
        email: email,
        password: password,
        password2: confirm,
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
        return response.json(); // Se la risposta � OK, parsifica la risposta come JSON
    })
    .then(data => {
        console.log("Dati ricevuti:", data);  // Gestisci i dati ricevuti
        token = data["token"]
        window.localStorage.setItem("token",token)
    })
    .catch(error => {
        // Stampa l'errore e l'URL in caso di errore
        console.error("Errore nella chiamata API:", error);
        console.log('URL della richiesta:', url);
        console.log('Dettagli errore:', error.message || error);
    });
};


export function LoginApi(email, password, setError){
    const url = 'http://2.37.63.53:10004/api/login';  // Definisci l'URL

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
        return response.json(); // Se la risposta � OK, parsifica la risposta come JSON
    })
    .then(data => {
        console.log("Dati ricevuti:", data);  // Gestisci i dati ricevuti
        token = data["token"]
        window.sessionStorage.setItem("token",token)
    })
    .catch(error => {
        // Stampa l'errore e l'URL in caso di errore
        console.error("Errore nella chiamata API:", error);
        console.log('URL della richiesta:', url);
        console.log('Dettagli errore:', error.message || error);
        if (error.response && error.response.status === 400) {
            setError('Mail o Password errati');
        }
    });
};