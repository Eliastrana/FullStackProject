import axios from 'axios'
import { forEach } from 'lodash-es'

const API_URL = 'http://localhost:8080/api/questions';

export const QuestionService = {
  async getQuestionsByQuizId(quizId) {
    const response = await axios.get(`${API_URL}/quiz/${quizId}`);
    forEach(response.data, question => {
      console.log(question)
    });
    return response.data;
  }
}