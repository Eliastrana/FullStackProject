export default {
  namespaced: true,
  state: () => ({
    selectedQuizTypes: [], // Holder styr på hvilke typer quizer som er valgt
    quizzes: []
  }),
  mutations: {

    ADD_QUIZ_TYPE(state, type) {
      if (!state.selectedQuizTypes.some(t => t.id === type.id)) {
        state.selectedQuizTypes.push(type);
      }
    },
    ADD_QUIZ(state, quiz) {
      const existingIndex = state.quizzes.findIndex(q => q.id === quiz.id);
      if (existingIndex !== -1) {
        // Hvis quizzen allerede finnes, oppdaterer vi den eksisterende
        state.quizzes[existingIndex] = quiz;
      } else {
        // Ellers legger vi til en ny quiz
        state.quizzes.push(quiz);
      }
    },
  },
  actions: {
    updateCurrentQuiz({ commit }, quizData) {
      commit('UPDATE_CURRENT_QUIZ', quizData);
    }
  },
};
