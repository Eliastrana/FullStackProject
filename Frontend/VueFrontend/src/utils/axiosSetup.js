// src/utils/axiosSetup.js
import axios from 'axios';
import { SessionToken } from '@/features/SessionToken';
import router from '@/router/index.js'

axios.interceptors.request.use(
  config => {
    const token = SessionToken.getToken();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  error => Promise.reject(error)
);

axios.interceptors.response.use(
  response => response,
  error => {
    if (error.response && error.response.status === 401) {
      SessionToken.clearToken();
      router.push('/login');
    }
    return Promise.reject(error);
  }
);
