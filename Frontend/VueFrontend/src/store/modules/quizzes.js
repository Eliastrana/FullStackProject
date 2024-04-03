export default {
  namespaced: true,
  state: () => ({
    questions: [], // Stores question data including UUID
  }),
  mutations: {
    ADD_QUESTION(state, { question, userId }) {
      console.log('Adding question with mutation', question, userId);
      question.userId = userId;
      console.log('Adding question 2', question, userId)
      state.questions.push(question);
      console.log('Adding question 3', question, userId)
    },
    UPDATE_QUESTION(state, updatedQuestion) {
      const index = state.questions.findIndex(question => question.uuid === updatedQuestion.uuid);
      if (index !== -1) {
        state.questions[index] = updatedQuestion;
      }
    },
    CLEAR_QUIZZES(state) {
      state.questions = [];
    },
  },
  actions: {
    addOrUpdateQuestion({ commit, state, rootGetters }, newQuestion) {
      // Accessing userId from the user module via rootGetters
      const userId = rootGetters['user/userId'];

      // Logging for debugging purposes
      console.log('UserId from getter:', userId);

      // Check if userId is not found
      if (!userId) {
        console.error('User ID not found');
        return; // Stop execution if userId is undefined or null
      }

      // Finding if the question already exists for the given userId
      const existingQuestion = state.questions.find(q => q.uuid === newQuestion.uuid && q.userId === userId);

      if (existingQuestion) {
        // If the question exists, update it
        console.log('Updating question', newQuestion, userId);
        commit('UPDATE_QUESTION', { question: newQuestion, userId });
      } else {
        // If the question does not exist, add it
        console.log('Adding new question', newQuestion, userId);
        commit('ADD_QUESTION', { question: newQuestion, userId });
      }
    },
  },
};
