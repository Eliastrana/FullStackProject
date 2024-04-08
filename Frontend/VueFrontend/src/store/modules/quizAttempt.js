import { AttemptService } from '@/services/AttemptService.js'

/**
 * Quiz Attempt Module
 */
export default {
  namespaced: true,
  state: () => ({
    quizData: null,
    quizId: null,
    totalQuizzesDone: 0,
  }),
  mutations: {
    SET_QUIZ_DATA(state, payload) {
      state.quizData = payload.quizData;
      state.quizId = payload.quizId;
    },
    CLEAR_QUIZ_DATA(state) {
      state.quizData = null;
      state.quizId = null;
    },
    SET_TOTAL_QUIZZES_DONE(state, total) {
      state.totalQuizzesDone = total;
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
        const userId = this.getters['user/userId'];
        const attempts = await AttemptService.getAttemptByUserId(userId);
        commit('SET_TOTAL_QUIZZES_DONE', attempts.length);
      } catch (error) {
        console.error('Failed to update total quizzes done:', error);
      }
    },
  },
  getters: {
    totalQuizzesDone: (state) => state.totalQuizzesDone,
  }
};
