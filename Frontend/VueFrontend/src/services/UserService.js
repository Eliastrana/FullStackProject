// src/services/UserService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/user';

export const UserService = {
  async register(userDetails) {
    // Directly return the response data from the registration API call.
    // Token handling is done in the Vuex action.
    const response = await axios.post(`${API_URL}/register`, userDetails);
    return response.data;
  },

  async login(credentials) {
    // Directly return the response data from the login API call.
    // Token handling is done in the Vuex action.
    const response = await axios.post(`${API_URL}/login`, credentials);
    return response.data;
  },

  logout() {
    // Assuming any server-side logout process if necessary.
    // Clearing the session token is handled in the Vuex action.
  },

  async getUserDetails() {
    // Remove the token parameter since the Axios interceptor will automatically add the Authorization header.
    // The response should include the user's details without explicitly passing the token.
    const response = await axios.get(`${API_URL}/details`);
    return response.data;
  },




};
