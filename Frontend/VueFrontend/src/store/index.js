import { createStore } from 'vuex';
import createPersistedState from 'vuex-persistedstate';
import user from './modules/user';
import quizzes from './modules/quizzes';

export default createStore({
  modules: {
    user,
    quizzes,
  },
  plugins: [createPersistedState({
    paths: [ 'user', 'quizzes'],
  })],
});
