import axios from 'axios';
import store from '@/store/index.js';

const ATTEMPT_API_URL = 'http://localhost:8080/api/userQuizAttempts';

/**
 *
 * @type {{create(*): Promise<any|undefined>, getAttemptByUserId(*): Promise<any|undefined>, getAllAttempts(): Promise<any|undefined>, getAttemptById(*): Promise<any|undefined>}}
 */
export const AttemptService = {
  async create(attemptDetails) {
    try {
      const response = await axios.post(`${ATTEMPT_API_URL}`, attemptDetails);
      return response.data; // The created attempt details
    } catch (error) {
      console.error('Error creating attempt:', error);
      throw error;
    }
  },

  /**
   *
   * @returns {Promise<any|undefined>}
   */

  async getAllAttempts() {
    const userId = store.getters['user/userId']; // Access userId using the getter
    try {
      const response = await axios.get(`${ATTEMPT_API_URL}`, {
        params: { userId } // Pass userId as a query parameter
      });
      return response.data; // An array of all attempts for the user
    } catch (error) {
      console.error('Error fetching attempts:', error);
      throw error;
    }
  },

  /**
   *
   * @param attemptId
   * @returns {Promise<any|undefined>}
   */
  async getAttemptById(attemptId) {
    try {
      const response = await axios.get(`${ATTEMPT_API_URL}/${attemptId}`);
      return response.data; // The details of the specific attempt
    } catch (error) {
      console.error('Error fetching attempt by ID:', error);
      throw error;
    }
  },

  /**
   *
   * @param userId
   * @returns {Promise<any>}
   */

  async getAttemptByUserId(userId) {
    try {
      const response = await axios.get(`${ATTEMPT_API_URL}/user/${userId}`);
      return response.data; // An array of all attempts for the user
    } catch (error) {
      console.error('Error fetching attempts by user ID:', error);
      throw error;
    }
  }
};
