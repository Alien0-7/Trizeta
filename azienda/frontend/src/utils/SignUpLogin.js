import axios from "axios";

export async function SignUpApi(email, password, password2, name, surname, address) {
  const url = 'http://trizeta.duckdns.org:10001/api/register';
  const formData = new FormData();
  formData.append('email', email);
  formData.append('password', password);
  formData.append('password2', password2);
  formData.append('name', name);
  formData.append('surname', surname);
  formData.append('address', address);

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

export async function LoginApi(email, password) {
  const url = 'http://trizeta.duckdns.org:10001/api/login';
  const formData = new FormData();
  formData.append('email', email);
  formData.append('password', password);
  try{
    const response = await axios.postForm(url, formData);
    console.log("Dati ricevuti:", response.data);
    return response.data;
  } catch (error) {
    console.error("Errore nella chiamata API:", error);
    console.log('URL della richiesta:', url);
    console.log('Dettagli errore:', error.message || error);
    throw error;
  }
}