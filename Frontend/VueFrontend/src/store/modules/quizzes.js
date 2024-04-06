// store/modules/quizzes.js
import router from '@/router/index.js'
import { v4 as uuidv4 } from 'uuid';
import { QuizService } from '@/services/QuizService.js'


export default {
  namespaced: true,
  state: () => ({
    questions: [],
    quizDetails: {
      title: '',
      description: '',
      creatorId: '',
      categoryId: '',
      questions: [],
      coverImage: null,
      imageName: '',
      imageType: '',
    },
    quizzes: [],
  }),
  mutations: {
    ADD_QUESTION(state, question) {
      state.quizDetails.questions.push(question); // Now handles all types of questions including Study with front/back images
    },
    UPDATE_QUESTION(state, updatedQuestion) {
      const index = state.quizDetails.questions.findIndex(q => q.uuid === updatedQuestion.uuid);
      if (index !== -1) {
        state.quizDetails.questions[index] = updatedQuestion;
      }
    },

    CLEAR_QUIZZES(state) {
      state.questions = [];
      state.quizDetails = {
        title: '',
        description: '',
        creatorId: '',
        categoryId: '',
        questions: [],
        coverImage: null,
        imageName: '',
        imageType: '',
      };
    },
    SET_QUIZ_DETAILS(state, details) {
      state.quizDetails = { ...state.quizDetails, ...details };
    },
    REMOVE_QUESTION(state, uuid) {
      const index = state.quizDetails.questions.findIndex(q => q.uuid === uuid);
      if (index !== -1) {
        state.quizDetails.questions.splice(index, 1);
      }
    },
    UPDATE_QUESTION_IMAGE(state, { uuid, imageData }) {
      const questionIndex = state.quizDetails.questions.findIndex(question => question.uuid === uuid);
      if (questionIndex !== -1) {
        state.quizDetails.questions[questionIndex].image = imageData;
      }
    },
    SET_QUESTION_ORDER(state, questions) {
      state.questions = questions;
    },
    SET_QUIZZES(state, quizzes) {
      state.quizDetails.questions = quizzes;
    },
    SET_QUIZ_ARRAY(state, quizzes) {
      state.quizzes = quizzes;
    },
    SET_QUIZ_IMAGE(state, { quizId, imageData }) {
      const quizIndex = state.quizzes.findIndex(quiz => quiz.id === quizId);
      if (quizIndex !== -1) {
        state.quizzes[quizIndex].imageData = imageData;
      }
    },

  },
  actions: {
    addOrUpdateQuestion({ commit, state, rootGetters }, questionData) {
      state.quizDetails.creatorId = rootGetters['user/userId'];
      const existingIndex = state.quizDetails.questions.findIndex(q => q.uuid === questionData.uuid);
      if (existingIndex !== -1) {
        commit('UPDATE_QUESTION', questionData);
      } else {
        commit('ADD_QUESTION', questionData);
      }
      if (questionData.coverImage) {
        commit('SET_QUIZ_COVER_IMAGE', {
          imageName: questionData.imageName,
          imageType: questionData.imageType,
          coverImage: questionData.coverImage
        });
      }
    },
    setQuizDetails({ commit }, details) {
      commit('SET_QUIZ_DETAILS', details);
    },
    removeQuestion({ commit }, uuid) {
      commit('REMOVE_QUESTION', uuid);
    },
    updateQuestionsOrder({ commit }, questions) {
      commit('SET_QUESTION_ORDER', questions);
    },
    clearQuizzes({ commit }) {
      commit('CLEAR_QUIZZES');
    },
    updateQuestionImage({ commit }, { uuid, image, imageType }) {
      // Find the question by UUID and update its image
       commit('UPDATE_QUESTION_IMAGE', { uuid, image, imageType });
    },
    async fetchAllQuizzes({ commit }) {
      try {
        const quizzesData = await QuizService.getAllQuizzes();
        commit('SET_QUIZ_ARRAY', quizzesData);
      } catch (error) {
        console.error('Error fetching all quizzes:', error);
      }
    },
    async fetchQuizImages({ commit, state }) {
      for (let quiz of state.quizzes) {
        console.log(quiz)
        if (quiz.imageId) {
          try {
            const imageData = await QuizService.getImageById(quiz.imageId);
            commit('SET_QUIZ_IMAGE', { quizId: quiz.id, imageData });
            console.log(imageData)
          } catch (error) {
            console.error('Error fetching image for quiz', quiz.id, error);
          }
        }
      }
    },
    addQuestionsByType({ dispatch }, { type, numberOfQuestions = 5 }) {
      for (let i = 0; i < numberOfQuestions; i++) {
        let questionTemplate;

        console.log(type)

        switch (type) {
          case 'FILL_IN_BLANK':
            questionTemplate = {
              uuid: uuidv4(),
              text: '',
              questionType: 'FILL_IN_BLANK',
              tags: [],
              answers: [{ text: '', correct: true }],
              image: null,
            };
            break;
          case 'MULTIPLE_CHOICE':
            questionTemplate = {
              uuid: uuidv4(),
              text: '',
              questionType: 'MULTIPLE_CHOICE',
              tags: [],
              answers: [{ text: '', correct: false }],
              image: null,
            };
            break;
          case 'STUDY':
            questionTemplate = {
              uuid: uuidv4(),
              text: '',
              questionType: 'STUDY',
              tags: [],
              answers: [{ text: '', correct: true }],
              imageFront: null,
              imageBack: null,
            };
            break;
          default:
            console.warn('Unsupported question type:', type);
            return;
        }
        dispatch('addOrUpdateQuestion', questionTemplate);
      }
    }
  },
};
