import axios from 'axios'
import store from '@/store/index.js'

const API_URL = 'http://localhost:8080/api/completeQuiz';
const token = store.state.user.token;

export const QuizService = {
  async create(quizDetails) {
    const config = {
      headers: { Authorization: `Bearer ${token}` }
    };

    // Clone quizDetails to avoid modifying the original state
    let modifiedQuizDetails = JSON.parse(JSON.stringify(quizDetails));

    // Adjust answers for each question based on type
    modifiedQuizDetails.questions = modifiedQuizDetails.questions.map(question => {
      if (question.questionType === 'STUDY' || question.questionType === 'FILL_IN_BLANK') {
        if (Array.isArray(question.answers) && question.answers.length) {
          // Assuming the first answer is the correct one as per your UI's logic
          question.answers = question.answers[0].text;
        } else {
          // Fallback to an empty string if no answers are present
          question.answers = '';
        }
      }
      return question;
    });

    const response = await axios.post(`${API_URL}`, modifiedQuizDetails, config);
    return response.data;
  }
}
