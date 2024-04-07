// QuizService.js
import axios from 'axios';

const COMPLETE_API_URL = 'http://localhost:8080/api/completeQuiz';
const QUIZ_API_URL = 'http://localhost:8080/api/quiz';
const IMAGE_API_URL = 'http://localhost:8080/api/images';

  // Helper function to transform question answers for consistency
function transformQuestionAnswers(questions) {
  return questions.map(question => {
    if (['FILL_IN_BLANK', 'STUDY'].includes(question.questionType)) {
      question.answers = question.answers.map(answer =>
        Array.isArray(answer.text) ? answer.text : [{ text: answer.text, correct: answer.correct }]
      ).flat();
    }
    return question;
  });
}

export const QuizService = {
  async create(quizDetails) {
    let modifiedQuizDetails = JSON.parse(JSON.stringify(quizDetails));
    modifiedQuizDetails.questions = transformQuestionAnswers(modifiedQuizDetails.questions);

    try {
      const response = await axios.post(`${COMPLETE_API_URL}`, modifiedQuizDetails);
      return response.data;
    } catch (error) {
      console.error('Error creating quiz:', error);
      throw error; // Optionally, could return a more user-friendly error or error code
    }
  },

  async updateQuiz(quizId, quizDetails) {
    let modifiedQuizDetails = JSON.parse(JSON.stringify(quizDetails));
    modifiedQuizDetails.questions = transformQuestionAnswers(modifiedQuizDetails.questions);

    try {
      const response = await axios.put(`${COMPLETE_API_URL}/${quizId}`, modifiedQuizDetails);
      return response.data;
    } catch (error) {
      console.error('Error updating quiz:', error);
      throw error;
    }
  },

  async deleteQuiz(quizId) {
    try {
      const response = await axios.delete(`${QUIZ_API_URL}/${quizId}`);
      return response.data;
    } catch (error) {
      console.error('Error deleting quiz:', error);
      throw error;
    }
  },

  async getAllQuizzes() {
    try {
      const response = await axios.get(`${QUIZ_API_URL}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching all quizzes:', error);
      throw error;
    }
  },

  async getQuizById(quizId) {
    try {
      const response = await axios.get(`${COMPLETE_API_URL}/${quizId}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching quiz details for ID: ${quizId}`, error);
      throw error;
    }
  },

  async getImageById(imageId) {
    try {
      const response = await axios.get(`${IMAGE_API_URL}/${imageId}`, { responseType: 'arraybuffer' });
      const base64 = btoa(
        new Uint8Array(response.data).reduce((data, byte) => data + String.fromCharCode(byte), '')
      );
      return `data:${response.headers['content-type']};base64,${base64}`;
    } catch (error) {
      console.error(`Error fetching image for ID: ${imageId}`, error);
      throw error;
    }
  },
};
