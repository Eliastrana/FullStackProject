// src/utils/axiosSetup.js
import axios from 'axios';
import { SessionToken } from '@/features/SessionToken';
import router from '@/router/index.js'

axios.interceptors.request.use(config => {
  // Define a list of paths that don't require authentication
  const openPaths = [
    "/swagger-ui/", "/swagger-ui.html", "/swagger-ui/index.html",
    "/v3/api-docs/", "/swagger-resources/", "/webjars/", "/error",
    "/home", "/login", "/api/user/register", "/api/user/login",
    "/api/difficulties/", "/api/quiz/", "/api/questions/quiz/",
    "/api/quizzes/", "/api/images/"
  ];

  // Check if the request's URL ends with any of the open paths
  const isPublicEndpoint = openPaths.some(path => config.url.includes(path));

  // If the token exists and the endpoint is not public, add the Authorization header
  if (!isPublicEndpoint && typeof sessionStorage !== 'undefined') {
    const token = sessionStorage.getItem('sessionToken'); // Adjust based on where you store the token
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
  }

  return config;
}, error => Promise.reject(error));


axios.interceptors.response.use(
  response => response,
  error => {
    if (error.response && error.response.status === 401 || error.response.status === 403) {
      SessionToken.clearToken();
      router.push('/login');
    }
    return Promise.reject(error);
  }
);
