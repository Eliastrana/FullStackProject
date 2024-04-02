// store/modules/quizzes.js
export default {
  namespaced: true,
  state() {
    return {
      currentQuiz: {
        title: '',
        answers: [{ text: '' }, { text: '' }, { text: '' }, { text: '' }],
        correctAnswer: null,
      },
    };
  },
  mutations: {
    UPDATE_CURRENT_QUIZ(state, { title, answers, correctAnswer }) {
      state.currentQuiz = { title, answers, correctAnswer };
    },
  },
  actions: {
    updateCurrentQuiz({ commit }, quizData) {
      commit('UPDATE_CURRENT_QUIZ', quizData);
    },
  },
};
