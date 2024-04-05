// store/modules/quizzes.js
import router from '@/router/index.js'
import { v4 as uuidv4 } from 'uuid';


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
    REMOVE_QUESTION(state, uuid) {
      state.quizDetails.questions = state.quizDetails.questions.filter(question => question.uuid !== uuid);
    },
    setQuestionsOrder(state, questions) {
      state.questions = questions;
    }

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
    removeQuestion({ commit }, uuid) {
      commit('REMOVE_QUESTION', uuid);
    },
    updateQuestionsOrder({ commit }, questions) {
      commit('setQuestionsOrder', questions);
    },

    clearQuizzes({ commit }) {
      commit('CLEAR_QUIZZES');
    },

    addQuestionsByType({ dispatch }, { type, numberOfQuestions = 5 }) {
      for (let i = 0; i < numberOfQuestions; i++) {
        let questionTemplate;

        switch (type) {
          case 'FILL_IN_BLANK':
            questionTemplate = {
              uuid: uuidv4(),
              text: '',
              questionType: 'FILL_IN_BLANK',
              tags: [],
              answers: [{ text: '', correct: true }]
            };
            break;
          case 'MULTIPLE_CHOICE':
            questionTemplate = {
              uuid: uuidv4(),
              text: '',
              questionType: 'MULTIPLE_CHOICE',
              tags: [],
              answers: [{ text: '', correct: false }] // Adjust as needed
            };
            break;
          case 'STUDY':
            questionTemplate = {
              uuid: uuidv4(),
              text: '',
              questionType: 'STUDY',
              tags: [],
              answers: [{ text: '', correct: true }] // Adjust as needed
            };
            break;
          default:
            console.warn('Unsupported question type:', type);
            return; // Exit the function if the type is not supported
        }

        dispatch('addOrUpdateQuestion', questionTemplate);
      }
    }


  },
};
