import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

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
    }
  ]
})

export default router
