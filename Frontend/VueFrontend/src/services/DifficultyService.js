import axios from 'axios'

const API_URL = 'http://localhost:8080/api/difficulties';

/**
 *
 * @type {{getAllDifficulties(): Promise<any|undefined>}}
 */
export const DifficultyService = {
  async getAllDifficulties() {
    try {
      const response = await axios.get(`${API_URL}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching difficulties:', error);
      throw error;
    }
  },
}