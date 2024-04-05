import axios from 'axios';

const API_URL = 'http://localhost:8080/api/questions';

export const QuestionService = {
  async getQuestionsByQuizId(quizId) {
    try {
      const response = await axios.get(`${API_URL}/quiz/${quizId}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching questions:', error);
      throw error;
    }
  }
};
