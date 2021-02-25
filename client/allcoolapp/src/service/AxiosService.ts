import axios from 'axios';
import StorageService from './StorageService';

//Genymotion - 10.0.3.2
//Android Studio - 10.0.2.2
//Localhost - 127.0.0.1 or localhost
const requestExecutor = axios.create({
  baseURL: 'http://10.0.2.2:8080',
  timeout: 10000,
  headers: {
    'Access-Control-Allow-Origin': '*',
    'Content-Type': 'application/json',
  },
});

requestExecutor.interceptors.request.use(async (config) => {
  const token = await StorageService.getKey('JWT-Token');

  if (token) {
    config.headers.Authorization = 'Bearer ' + token;
  }

  return config;
});

export default requestExecutor;
