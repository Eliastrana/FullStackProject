import { createStore } from 'vuex';
import createPersistedState from 'vuex-persistedstate';
import user from './modules/user';
import quizzes from './modules/quizzes';
import quizAttempt from './modules/quizAttempt';

/**
 *
 * @type {(store: Store<unknown>) => void}
 */
const sessionStoragePlugin = createPersistedState({
  storage: window.sessionStorage,
  paths: ['user', 'quizzes', 'quizAttempt'],
});


export default createStore({
  modules: {
    user,
    quizzes,
    quizAttempt,
  },
  plugins: [sessionStoragePlugin],
});
