const API_URL = 'http://localhost:8080/api/ratings';

/**
 *
 * @type {{getRatingsByUser: ((function(*): Promise<any|undefined>)|*), getAverageRating: ((function(*): Promise<any|undefined>)|*), saveOrUpdateRating: ((function(*): Promise<any|undefined>)|*)}}
 */
export const RatingService = {
  saveOrUpdateRating: async (ratingDTO) => {
    try {
      const response = await fetch(`${API_URL}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(ratingDTO),
      });
      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Failed to save or update rating: ${errorText}`);
      }
      return await response.json();
    } catch (error) {
      console.error('Error saving or updating rating:', error);
      throw error;
    }
  },

  getAverageRating: async (quizId) => {
    try {
      const response = await fetch(`${API_URL}/average/${quizId}`);
      if (!response.ok) {
        throw new Error('Failed to get average rating');
      }
      return await response.json();
    } catch (error) {
      console.error('Error getting average rating:', error);
      throw error;
    }
  },

  getRatingsByUser: async (userId) => {
    try {
      const response = await fetch(`${API_URL}/user/${userId}`);
      if (!response.ok) {
        throw new Error('Failed to get user ratings');
      }
      return await response.json();
    } catch (error) {
      console.error('Error getting user ratings:', error);
      throw error;
    }
  },
};
