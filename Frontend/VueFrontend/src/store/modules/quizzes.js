export default {
  namespaced: true,
  state: () => ({
    questions: [], // Stores question data including UUID
  }),
  mutations: {
    ADD_QUESTION(state, question) {
      state.questions.push(question);
    },
    UPDATE_QUESTION(state, updatedQuestion) {
      const index = state.questions.findIndex(question => question.uuid === updatedQuestion.uuid);
      if (index !== -1) {
        state.questions[index] = updatedQuestion;
      }
    },
  },
  actions: {
    addOrUpdateQuestion({ commit, state }, question) {
      const existingQuestion = state.questions.find(q => q.uuid === question.uuid);
      if (existingQuestion) {
        commit('UPDATE_QUESTION', question);
      } else {
        commit('ADD_QUESTION', question);
      }
    },
  },
};
