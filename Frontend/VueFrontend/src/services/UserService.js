// src/services/UserService.js
import axios from 'axios';
import { SessionToken } from '../features/SessionToken';

const API_URL = 'http://localhost:8080/api/user'; // Adjust this to your actual API endpoint

export const UserService = {
  async register(userDetails) {
    const response = await axios.post(`${API_URL}/register`, userDetails);
    SessionToken.setToken(response.data.token); // Assuming the token is returned upon registration
    return response.data;
  },

  async login(credentials) {
    const response = await axios.post(`${API_URL}/login`, credentials);
    SessionToken.setToken(response.data.token); // Save the session token
    return {
      token: response.data.token,
      name: response.data.name
    };
  },

  logout() {
    SessionToken.clearToken();
  },

  async getUserDetails(token) {
    // Assuming you have an endpoint like '/api/user/details' that returns user details
    const config = {
      headers: { Authorization: `Bearer ${token}` }
    };
    const response = await axios.get(`${API_URL}/details`, config);
    return response.data; // Assuming this includes the user's name
  },
};
