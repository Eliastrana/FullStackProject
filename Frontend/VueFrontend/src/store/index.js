import { createStore } from 'vuex';
import createPersistedState from 'vuex-persistedstate';
import user from './modules/user';
import quizzes from './modules/quizzes';
import quizComponents from './modules/quizComponents'; // Assuming this module exists as per your initial setup

export default createStore({
  modules: {
    user,
    quizzes,
    quizComponents,
  },
  plugins: [createPersistedState({
    paths: ['quizzes'],
  })],
});
