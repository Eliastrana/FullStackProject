import axios from 'axios'

const API_URL = 'http://localhost:8080/api/user-roles/';

export const RoleService = {
  async userHasRoleAdmin(username) {
    try {
      const response = await axios.get(`${API_URL}hasRole`, { params: { username, role: 'ADMIN' } });
      return response.data;
    } catch (error) {
      console.error('Error checking ADMIN role:', error);
      return false;
    }
  },

  async asignRoleToUser(username, role) {
    try {
      const params = new URLSearchParams();
      params.append('username', username);
      params.append('role', role);
      const response = await axios.post(`${API_URL}`, params, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      });
      return response.data;
    } catch (error) {
      console.error('Could not assign role to user:', error.response.data);
      return false;
    }
  },
}