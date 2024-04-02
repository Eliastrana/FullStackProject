//index.js
import { createStore } from 'vuex';
import user from './modules/user';
import quizzes from './modules/quizzes';
import quizComponents from '@/store/modules/quizComponents.js'

export default createStore({
  modules: {
    user,
    quizzes,
    quizComponents
  }
});


