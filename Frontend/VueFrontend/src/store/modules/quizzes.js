export default {
  namespaced: true,
  state: () => ({
    questions: [], // Stores question data including UUID
    quizDetails: {
      title: '',
      description: '',
      category: '',
      coverImage: null,
    }
  }),
  mutations: {
    ADD_QUESTION(state, { question, userId }) {
      question.userId = userId;
      state.questions.push(question);
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
    SET_QUIZ_DETAILS(state, { title, description, category, coverImage }) {
      state.quizDetails.title = title;
      state.quizDetails.description = description;
      state.quizDetails.category = category;
      state.quizDetails.coverImage = coverImage;
    }
  },
  actions: {
    addOrUpdateQuestion({ commit, state, rootGetters }, newQuestion) {
      // Accessing userId from the user module via rootGetters
      const userId = rootGetters['user/userId'];

      // Check if userId is not found
      if (!userId) {
        console.error('User ID not found');
        return; // Stop execution if userId is undefined or null
      }

      // Finding if the question already exists for the given userId
      const existingQuestion = state.questions.find(q => q.uuid === newQuestion.uuid && q.userId === userId);

      if (existingQuestion) {
        // If the question exists, update it
        commit('UPDATE_QUESTION', { question: newQuestion, userId });
      } else {
        // If the question does not exist, add it
        commit('ADD_QUESTION', { question: newQuestion, userId });
      }
    },
    updateQuizDetails({ commit }, quizDetails) {
      commit('SET_QUIZ_DETAILS', quizDetails);
    },
  },
};
