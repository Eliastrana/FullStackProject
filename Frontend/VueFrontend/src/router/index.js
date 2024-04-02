import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import store from '@/store/index.js'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/create',
      name: 'create',
      component: () => import('../views/createView/CreateView.vue')
    },
    {
      path: '/quizzes',
      name: 'quizzes',
      component: () => import('../views/quizzesView/QuizzesView.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/loginView/LoginView.vue')
    },
    {
      path: '/create-user',
      name: 'CreateUser',
      component: () => import('../views/loginView/CreateUserView.vue') // Adjust the path as necessary
    },
    {
      path: '/quizcreator',
      name: 'Quizcreator',
      component: () => import('../components/createPage/QuizcreatortoolView.vue')

    },
    {
      path: '/QuizcreatorTool',
      name: 'QuizcreatorTool',
      component: () => import('../components/createPage/QuizcreatortoolView.vue')
    },
    {
      path: '/Account',
      name: 'Account',
      component: () => import('../views/accountView/AccountView.vue')
    },
    {
      path: '/QuizDisplayer',
      name: 'QuizDisplayer',
      component: () => import('../components/displayPage/QuizDisplayer.vue')
    },

    {
      path: '/MyAccount',
      name: 'MyAccount',
      component: () => import('../views/userPage/MyAccount.vue')
    },

    // {
    //   path: '/my-account',
    //   name: 'MyAccount',
    //   component: () => import('@/views/userPage/MyAccount.vue'), // Adjust the path to your MyAccount component
    //   meta: { requiresAuth: true }, // Custom flag to indicate authentication is required
    //   beforeEnter: (to, from, next) => {
    //     if (!store.getters['user/isAuthenticated']) {
    //       // Redirect to the login page if the user is not authenticated
    //       next({ name: 'Login' }); // Adjust with your actual login route name or path
    //     } else {
    //       next(); // Proceed to the route if the user is authenticated
    //     }
    //   },
    // },

  ]
})

export default router
