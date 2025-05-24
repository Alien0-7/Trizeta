import axios from axios

export async function UserInfoApi(){
    const url = 'http://trizeta.duckdns.org:10001/api/getuserinfo';
    const formData = new FormData();

    try {
        const response = await axios.postForm(url,formData);
        console.log("Dati ricevuti:", response.data);
        return response.data;

    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        console.log('URL della richiesta:', url);
        console.log('Dettagli errore:', error.message || error);
        throw error;
    }

}

export async function TemperatureAPI(){
    const url = 'http://trizeta.duckdns.org:10001/api/temperature';
    const formData = new FormData();


    try {
        const response = await axios.postForm(url,formData);
        console.log("Dati ricevuti:", response.data);
        return response.data;

    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        console.log('URL della richiesta:', url);
        console.log('Dettagli errore:', error.message || error);
        throw error;
    }
}

export async function HumidityAPI(){
    const url = 'http://trizeta.duckdns.org:10001/api/humidity';
    const formData = new FormData();


    try {
        const response = await axios.postForm(url,formData);
        console.log("Dati ricevuti:", response.data);
        return response.data;

    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        console.log('URL della richiesta:', url);
        console.log('Dettagli errore:', error.message || error);
        throw error;
    }
}

export async function Co2API(){
    const url = 'http://trizeta.duckdns.org:10001/api/co2';
    const formData = new FormData();


    try {
        const response = await axios.postForm(url,formData);
        console.log("Dati ricevuti:", response.data);
        return response.data;

    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        console.log('URL della richiesta:', url);
        console.log('Dettagli errore:', error.message || error);
        throw error;
    }
}

export async function AiApi(){
    const url = 'http://trizeta.duckdns.org:10001/ai/predict';
    const formData = new FormData();


    try {
        const response = await axios.postForm(url,formData);
        console.log("Dati ricevuti:", response.data);
        return response.data;

    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        console.log('URL della richiesta:', url);
        console.log('Dettagli errore:', error.message || error);
        throw error;
    }
}