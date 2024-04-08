// src/services/UserService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/user';

/**
 *
 * @type {{getUserDetails(): Promise<any>, getAllUsers(): Promise<any>, logout(), updatePassword(*): Promise<AxiosResponse<any>> | Promise<axios.AxiosResponse<any>> | *, deleteUser(*): Promise<any>, login(*): Promise<any>, register(*): Promise<any>}}
 */
export const UserService = {
  async register(userDetails) {
    const response = await axios.post(`${API_URL}/register`, userDetails);
    return response.data;
  },

  async login(credentials) {
    const response = await axios.post(`${API_URL}/login`, credentials);
    return response.data;
  },

  logout() {
  },

  async getUserDetails() {
    const response = await axios.get(`${API_URL}/details`);
    return response.data;
  },

  async getAllUsers() {
    const response = await axios.get(`${API_URL}/all`);
    return response.data;
  },

  async deleteUser(username) {
    const response = await axios.delete(`${API_URL}/${username}`);
    return response.data;
  },
  
  updatePassword(passwordUpdateData) {
    return axios.put(`${API_URL}/update/password`, passwordUpdateData);
  }

};
