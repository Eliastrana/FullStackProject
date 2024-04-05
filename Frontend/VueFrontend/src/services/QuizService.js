import axios from 'axios';

const COMPLETE_API_URL = 'http://localhost:8080/api/completeQuiz';
const QUIZ_API_URL = 'http://localhost:8080/api/quiz';

export const QuizService = {
  async create(quizDetails) {
    // Clone quizDetails to avoid modifying the original state
    let modifiedQuizDetails = JSON.parse(JSON.stringify(quizDetails));

    // Adjust answers for each question based on type
    modifiedQuizDetails.questions = modifiedQuizDetails.questions.map(question => {
      if (question.questionType === 'STUDY' || question.questionType === 'FILL_IN_BLANK') {
        if (Array.isArray(question.answers) && question.answers.length) {
          // Wrap the first answer object in an array to maintain the expected structure
          question.answers = [question.answers[0]];
        } else {
          // If no answers are present, ensure it remains an array, possibly empty
          question.answers = [];
        }
      }
      return question;
    });

    console.log(modifiedQuizDetails)
    const response = await axios.post(`${COMPLETE_API_URL}`, modifiedQuizDetails);
    return response.data;
  },


  async getAllQuizzes() {
    const response = await axios.get(`${QUIZ_API_URL}`);
    return response.data;
  },

  async getQuizById(quizId) {
    const response = await axios.get(`${COMPLETE_API_URL}/${quizId}`);
    return response.data;
  }
};
