import axios from "axios";


export  function SingUpApi(email,password){
    axios.post('https://reqres.in/api/register', {
        email:email,
        password:password
    }).then(response =>{
        if(response.status===200){
            console.log(response.data);
        };
    }).catch(response =>{
        console.log('errore');
    });
};


export  function LoginApi(email,password){
    axios.post('https://reqres.in/api/register', {
        email:email,
        password:password
    }).then(response =>{
        if(response.status===200){
            console.log(response.data);
        };
    }).catch(response =>{
        console.log('errore');
    });
};



