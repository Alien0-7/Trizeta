import axios from "axios"

export async function UserInfoApi(token){
    const url = 'http://trizeta.duckdns.org:10001/api/getuserinfo';
    const formData = new FormData()
    formData.append('token',token);

    try {
        const response = await axios.postForm(url,formData);
        console.log("Dati utente ricevuti:", response.data);
        return response.data;

    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        console.log('URL della richiesta:', url);
        console.log('Dettagli errore:', error.message || error);
        throw error;
    }
}

export async function TemperatureAPI(token){
    const url = 'http://trizeta.duckdns.org:10001/api/temperature';
    const formData = new FormData();
    const rawToken = token.replace(/^Bearer\s+/i, '');
    formData.append('token',rawToken);
    formData.append('fromDate',"2025-05-26 00:00:00")
    formData.append('toDate',"2025-05-27 00:00:00")

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

export async function HumidityAPI(token){
    const url = 'http://trizeta.duckdns.org:10001/api/humidity';
    const formData = new FormData();
    const rawToken = token.replace(/^Bearer\s+/i, '');
    formData.append('token',rawToken);
    formData.append('fromDate',"2025-05-26 00:00:00")
    formData.append('toDate',"2025-05-27 00:00:00")

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

export async function Co2API(token){
    const url = 'http://trizeta.duckdns.org:10001/api/co2';
    const formData = new FormData();
    const rawToken = token.replace(/^Bearer\s+/i, '');
    formData.append('token',rawToken);
    formData.append('fromDate',"2025-05-26 00:00:00")
    formData.append('toDate',"2025-05-27 00:00:00")

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

export async function AiAPI(token,type){
    const url = 'http://trizeta.duckdns.org:10001/api/ai/predict';
    const formData = new FormData();
    const rawToken = token.replace(/^Bearer\s+/i, '');
    formData.append('token',rawToken);
    formData.append('data_type',type)
    formData.append('fromDate',"2025-05-26 00:00:00")
    formData.append('toDate',"2025-05-27 00:00:00")

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