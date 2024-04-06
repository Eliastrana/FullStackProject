import axios from 'axios';

const COMPLETE_API_URL = 'http://localhost:8080/api/completeQuiz';
const QUIZ_API_URL = 'http://localhost:8080/api/quiz';
const IMAGE_API_URL = 'http://localhost:8080/api/images';

export const QuizService = {
  async create(quizDetails) {
    let modifiedQuizDetails = JSON.parse(JSON.stringify(quizDetails));

    modifiedQuizDetails.questions = modifiedQuizDetails.questions.map(question => {
      if (question.questionType === 'FILL_IN_BLANK' || question.questionType === 'STUDY') {
        // Check if the answers structure is not consistent with the expected format
        question.answers = question.answers.map(answer => {
          if (Array.isArray(answer.text)) {
            // Since 'text' is an array, it's assumed to contain objects with 'text' and 'correct' fields
            return answer.text; // Assign the first element since 'FILL_IN_BLANK' should only have one correct answer
          }
          // If 'text' is not an array, ensure it is wrapped in an object with the expected fields
          return [{ text: answer.text, correct: answer.correct }];
        }).flat(); // Flatten the array in case there are nested arrays
      }
      return question;
    });

    const response = await axios.post(`${COMPLETE_API_URL}`, modifiedQuizDetails);
    return response.data;
  },

  async deleteQuiz(quizId) {
    const response = await axios.delete(`${QUIZ_API_URL}/${quizId}`);
    return response.data;
  },


  async getAllQuizzes() {
    const response = await axios.get(`${QUIZ_API_URL}`);
    return response.data;
  },

  async getQuizById(quizId) {
    const response = await axios.get(`${COMPLETE_API_URL}/${quizId}`);
    return response.data;
  },

  async getCompleteQuizDetails(quizId) {
    // Construct the URL for fetching complete quiz details by ID
    const url = `${COMPLETE_API_URL}/${quizId}`;
    try {
      const response = await axios.get(url);
      return response.data; // The complete details of the quiz
    } catch (error) {
      // It's a good practice to handle errors specifically
      console.error('Failed to fetch complete quiz details:', error);
      throw error; // Rethrow the error if you want to handle it in the calling context
    }
  },

  async getImageById(imageId) {
    console.log(imageId)
    const response = await axios.get(`${IMAGE_API_URL}/${imageId}`, { responseType: 'arraybuffer' });
    const base64 = btoa(
      new Uint8Array(response.data).reduce((data, byte) => data + String.fromCharCode(byte), '')
    );
    return `data:${response.headers['content-type']};base64,${base64}`;
  }
};
