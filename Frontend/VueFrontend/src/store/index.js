import { createStore } from 'vuex';
import createPersistedState from 'vuex-persistedstate';
import user from './modules/user';
import quizzes from './modules/quizzes';
import quizAttempt from './modules/quizAttempt.js'

export default createStore({
  modules: {
    user,
    quizzes,
    quizAttempt,
  },
  plugins: [createPersistedState({
    paths: [ 'user', 'quizzes', 'quizAttempt'],
  })],
});
