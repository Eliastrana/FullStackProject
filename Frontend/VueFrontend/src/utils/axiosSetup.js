// src/utils/axiosSetup.js
import axios from 'axios';
import router from '@/router/index.js'
import store from '@/store/index.js'

/**
 * Axios Interceptors
 */
axios.interceptors.request.use(config => {
  const openPaths = [
    "/swagger-ui/", "/swagger-ui.html", "/swagger-ui/index.html",
    "/v3/api-docs/", "/swagger-resources/", "/webjars/", "/error",
    "/home", "/login", "/api/user/register", "/api/user/login",
    "/api/difficulties/", "/api/quiz/", "/api/questions/quiz/",
    "/api/quizzes/", "/api/images/"
  ];

  const isPublicEndpoint = openPaths.some(path => config.url.includes(path));

  if (!isPublicEndpoint && typeof sessionStorage !== 'undefined') {
    const token = sessionStorage.getItem('sessionToken');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
  }

  return config;
}, error => Promise.reject(error));

/**
 * Axios Interceptors
 */

axios.interceptors.response.use(
  response => response,
  error => {
    if (error.response && (error.response.status === 401 || error.response.status === 403)) {
      store.dispatch('user/logout')
        .then(() => {
          router.push('/login');
        })
        .catch(logoutError => {
          console.error('Error during logout:', logoutError);
        });
    }
    return Promise.reject(error);
  }
);
