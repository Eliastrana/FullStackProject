// store/modules/quizzes.js
import router from '@/router/index.js'
import { v4 as uuidv4 } from 'uuid';
import * as Vue from 'lodash-es'


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
    UPDATE_QUESTION(state, payload) {
      const index = state.quizDetails.questions.findIndex(q => q.uuid === payload.uuid);
      if (index !== -1) {
        // Check if the update is for an image and handle accordingly
        if (payload.imageType) {
          // Assuming 'imageFront' and 'imageBack' are properties on the question
          // Adjust according to your actual data structure
          if (payload.imageType === 'front') {
            state.quizDetails.questions[index].imageFront = payload.image;
          } else if (payload.imageType === 'back') {
            state.quizDetails.questions[index].imageBack = payload.image;
          }
        } else {
          // Handle general question updates
          state.quizDetails.questions[index] = { ...state.quizDetails.questions[index], ...payload };
        }
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
    UPDATE_QUESTION_IMAGE(state, { uuid, imageData }) {
      const questionIndex = state.quizDetails.questions.findIndex(question => question.uuid === uuid);
      if (questionIndex !== -1) {
        // Directly assign the image data to the question object
        state.quizDetails.questions[questionIndex].image = imageData;
      }
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

    updateQuestionImage({ commit }, { uuid, image, imageType }) {
      // Find the question by UUID and update its image
       commit('UPDATE_QUESTION_IMAGE', { uuid, image, imageType });
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
