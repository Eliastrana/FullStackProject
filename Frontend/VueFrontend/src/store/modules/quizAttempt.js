// store/modules/quizAttempt.js

export default {
  namespaced: true,
  state: () => ({
    quizData: null, // Store the entire quiz object here
  }),
  mutations: {
    SET_QUIZ_DATA(state, data) {
      state.quizData = data;
    },
    CLEAR_QUIZ_DATA(state) {
      state.quizData = null;
    },
  },
  actions: {
    setQuizData({ commit }, data) {
      commit('SET_QUIZ_DATA', data);
    },
    clearQuizData({ commit }) {
      commit('CLEAR_QUIZ_DATA');
    },
  },
};
