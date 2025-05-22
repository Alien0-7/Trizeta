import axios from "axios";

export async function SignUpApi(email, password, password2, name, surname, address) {
  const url = 'http://trizeta.duckdns.org:10001/api/register';

  try {
    const response = await axios.postForm(url, {
      email: email,
      password1: password,
      password2: password2,
      name: name,
      surname: surname,
      address: address
    }, {
      headers: {
        'Content-Type': 'multipart/form-data',
      }
    });

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

  try {
    const response = await axios.postForm(url, {
      email: email,
      password: password
    }, {
      headers: {
        'Content-Type': 'multipart/form-data',
      }
    });
    console.log("Dati ricevuti:", response.data);
    return response.data; 

  } catch (error) {
    console.error("Errore nella chiamata API:", error);
    console.log('URL della richiesta:', url);
    console.log('Dettagli errore:', error.message || error);
    throw error;
  }
}