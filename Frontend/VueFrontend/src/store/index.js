import { createStore } from 'vuex';
import createPersistedState from 'vuex-persistedstate';
import user from './modules/user';
import quizzes from './modules/quizzes';
import quizAttempt from './modules/quizAttempt';

// Define custom storage that utilizes sessionStorage
const sessionStoragePlugin = createPersistedState({
  storage: window.sessionStorage,
  paths: ['user', 'quizzes', 'quizAttempt'], // Specify your paths here
});

export default createStore({
  modules: {
    user,
    quizzes,
    quizAttempt,
  },
  plugins: [sessionStoragePlugin], // Use the custom sessionStorage plugin
});
