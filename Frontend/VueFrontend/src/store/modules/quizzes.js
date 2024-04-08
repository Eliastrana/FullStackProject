// store/modules/quizzes.js
import { v4 as uuidv4 } from 'uuid';
import { QuizService } from '@/services/QuizService.js'
import { RatingService } from '@/services/RatingService.js'

/**
 * Quizzes Module
 */

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
      isPublic: false,
    },
    quizzes: [],
  }),
  mutations: {
    ADD_QUESTION(state, question) {
      state.quizDetails.questions.push(question);
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
        isPublic: false,
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

    SET_QUESTION_ORDER(state, questions) {
      state.questions = questions;
    },
    SET_QUIZZES(state, quizzes) {
      state.quizDetails.questions = quizzes;
    },
    SET_QUIZ_ARRAY(state, quizzes) {
      state.quizzes = quizzes;
    },
    UPDATE_QUESTION_TAGS(state, { uuid, newTags }) {
      const questionIndex = state.quizDetails.questions.findIndex(q => q.uuid === uuid);
      if (questionIndex !== -1) {
        state.quizDetails.questions[questionIndex].tags = newTags;
      }
    },
    SET_QUIZ_IMAGE(state, { quizId, imageData }) {
      const quizIndex = state.quizzes.findIndex(quiz => quiz.id === quizId);
      if (quizIndex !== -1) {
        state.quizzes[quizIndex].imageData = imageData;
      }
    },
    SET_QUIZ_RATING(state, { quizId, averageRating }) {
      const quizIndex = state.quizzes.findIndex(quiz => quiz.id === quizId);
      if (quizIndex !== -1) {
        if (!state.quizzes[quizIndex].ratings) {
          state.quizzes[quizIndex].ratings = {};
        }
        state.quizzes[quizIndex].ratings.average = averageRating;
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
    updateQuestionTags({ commit }, payload) {
      commit('UPDATE_QUESTION_TAGS', payload);
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
        if (quiz.imageId) {
          try {
            const imageData = await QuizService.getImageById(quiz.imageId);
            commit('SET_QUIZ_IMAGE', { quizId: quiz.id, imageData });
          } catch (error) {
            console.error('Error fetching image for quiz', quiz.id, error);
          }
        }
      }
    },
    async addImageToQuiz({ commit, state }, { quizId }) {
      try {
        for (let quiz of state.quizzes) {
          if (quiz.id === quizId) {
            const imageData = await QuizService.getImageById(quiz.imageId);
            commit('SET_QUIZ_IMAGE', { quizId, imageData });
          }
        }
      } catch (error) {
        console.error('Error adding image to quiz:', error);
      }
    },

    async fetchAllQuizRatings({ commit, state }) {
      for (const quiz of state.quizzes) {
        try {
          let averageRating = await RatingService.getAverageRating(quiz.id);
          if (averageRating === null) {
            averageRating = 0;
          }
          commit('SET_QUIZ_RATING', { quizId: quiz.id, averageRating });
        } catch (error) {
          console.error('Error fetching average rating for quiz', quiz.id, error);
        }
      }
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
              answers: [{ text: '', correct: true }],
            };
            break;
          case 'MULTIPLE_CHOICE':
            questionTemplate = {
              uuid: uuidv4(),
              text: '',
              questionType: 'MULTIPLE_CHOICE',
              tags: [],
              answers: [{ text: '', correct: false }],
            };
            break;
          case 'STUDY':
            questionTemplate = {
              uuid: uuidv4(),
              text: '',
              questionType: 'STUDY',
              tags: [],
              answers: [{ text: '', correct: true }],
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
