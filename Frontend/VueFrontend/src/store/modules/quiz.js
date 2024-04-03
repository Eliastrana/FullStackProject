// store/quiz.js

import { reactive } from 'vue';

export const quizStore = reactive({
  answers: {},

  setAnswer(questionId, answer) {
    this.answers[questionId] = answer;
  },

  getAnswer(questionId) {
    return this.answers[questionId] || '';
  }
});
