export default {
  namespaced: true,
  state: () => ({
    quizData: null, // Store the entire quiz object here
    quizId: null, // Add this to store the quiz ID
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
  },
  actions: {
    setQuizData({ commit }, payload) {
      commit('SET_QUIZ_DATA', payload);
    },
    clearQuizData({ commit }) {
      commit('CLEAR_QUIZ_DATA');
    },
  },
};
