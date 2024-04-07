import { AttemptService } from '@/services/AttemptService.js'

export default {
  namespaced: true,
  state: () => ({
    quizData: null, // Store the entire quiz object here
    quizId: null, // Add this to store the quiz ID
    totalQuizzesDone: 0, // Initialize the totalQuizzesDone state
  }),
  mutations: {
    SET_QUIZ_DATA(state, payload) {
      state.quizData = payload.quizData;
      state.quizId = payload.quizId; // Set the quiz ID
    },
    CLEAR_QUIZ_DATA(state) {
      state.quizData = null;
      state.quizId = null; // Clear the quiz ID as well
    },
    SET_TOTAL_QUIZZES_DONE(state, total) {
      state.totalQuizzesDone = total; // Correctly set the total quizzes done
    },
  },
  actions: {
    setQuizData({ commit }, payload) {
      commit('SET_QUIZ_DATA', payload);
    },
    clearQuizData({ commit }) {
      commit('CLEAR_QUIZ_DATA');
    },

    async updateTotalQuizzesDone({ commit }) {
      try {
        const userId = this.getters['user/userId']; // Assuming you have a user module
        const attempts = await AttemptService.getAttemptByUserId(userId);
        commit('SET_TOTAL_QUIZZES_DONE', attempts.length);
      } catch (error) {
        console.error('Failed to update total quizzes done:', error);
      }
    },
  },
  getters: {
    totalQuizzesDone: (state) => state.totalQuizzesDone, // Correctly reference the totalQuizzesDone
  }
};
