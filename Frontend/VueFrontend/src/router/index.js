import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import store from '@/store/index.js'
import NotFound from '../views/NotFound.vue';
import { RoleService } from '@/services/RoleService.js'




const router = createRouter({


  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/:pathMatch(.*)*', // This will catch all unmatched routes
      name: 'NotFound',
      component: NotFound,
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
      path: '/reset-password',
      name: 'ResetPassword',
      component: () => import('../views/loginView/ResetPasswordView.vue') // Adjust the path as necessary
    },
    {
      path: '/quizcreator/:quizId?',
      name: 'Quizcreator',
      component: () => import('../components/createPage/QuizcreatortoolView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/QuizcreatorTool/:quizId?',
      name: 'QuizcreatorTool',
      component: () => import('../components/createPage/QuizcreatortoolView.vue'),
      meta: { requiresAuth: true }
    },

    // {
    //   path: '/Account',
    //   name: 'Account',
    //   component: () => import('../views/accountView/AccountView.vue')
    // },
    {
      path: '/QuizDisplayer',
      name: 'QuizDisplayer',
      component: () => import('../components/displayPage/QuizDisplayer.vue'),
      meta: { requiresAuth: true }
    },

    {
      path: '/MyAccount',
      name: 'MyAccount',
      component: () => import('../views/userPage/MyAccount.vue')
    },

    {
      path: '/Admin',
      name: 'Admin',
      component: () => import('../views/adminPage/AdminView.vue'),
      meta: { requiresAuth: true, requiresAdmin: true }
    },
    {
      path: '/Contact',
      name: 'Contact',
      component: () => import('../views/contactView/ContactView.vue') 
    }

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

router.beforeEach(async (to, from, next) => {
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
  const requiresAdmin = to.matched.some(record => record.meta.requiresAdmin);
  const isAuthenticated = store.getters['user/isAuthenticated'];
  const username = store.getters['user/userName'];

  if (requiresAuth && !isAuthenticated) {
    next({ name: 'login' });
    return;
  }

  if (requiresAdmin) {
    const hasAdminRole = await RoleService.userHasRoleAdmin(username);
    if (!hasAdminRole) {
      next({ name: 'home' });
      return;
    }
  }

  next();
});


export default router
