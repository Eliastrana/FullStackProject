// store/modules/quizzes.js
export default {
  namespaced: true,
  state: () => ({
    questions: [], // Retain for storing question data
    quizDetails: {
      title: '',
      description: '',
      creatorId: '',
      categoryName: '',
      questions: [],
    },
  }),
  mutations: {
    ADD_QUESTION(state, question) {
      console.log('ADD_QUESTION', question)
      state.quizDetails.questions.push(question);
      console.log(state.quizDetails.questions)
    },
    UPDATE_QUESTION(state, updatedQuestion) {
      const index = state.quizDetails.questions.findIndex(q => q.uuid === updatedQuestion.uuid);
      if (index !== -1) {
        state.quizDetails.questions[index] = updatedQuestion;
      }
    },
    CLEAR_QUIZZES(state) {
      state.questions = [];
      state.quizDetails = { title: '', description: '', creatorId: '', categoryName: '', questions: [] };
    },
    SET_QUIZ_DETAILS(state, details) {
      state.quizDetails = { ...state.quizDetails, ...details };
    },
  },
  actions: {
    addOrUpdateQuestion({ commit, state, rootGetters }, questionData) {
      console.log('addOrUpdateQuestion', questionData)
      state.quizDetails.creatorId = rootGetters['user/userId'];
      const existingIndex = state.quizDetails.questions.findIndex(q => q.uuid === questionData.uuid);
      if (existingIndex !== -1) {
        commit('UPDATE_QUESTION', questionData);
      } else {
        console.log('addOrUpdateQuestion2', questionData)
        commit('ADD_QUESTION', questionData);
      }
    },
    setQuizDetails({ commit }, details) {
      commit('SET_QUIZ_DETAILS', details);
    },
  },
};
