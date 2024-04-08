// store/modules/user.js
import { SessionToken } from '@/features/SessionToken.js';
import { UserService } from '@/services/UserService.js';
import router from '@/router/index.js'

/**
 * User Module
 * @returns {{userInfo: (*|null), token: string}}
 */
const state = () => ({
  token: SessionToken.getToken(),
  userInfo: SessionToken.getUserInfo(),
});

/**
 *
 * @type {{isAuthenticated: (function(*): boolean), userName: (function(*): *), userId: (function(*): *)}}
 */

const getters = {
  isAuthenticated: (state) => !!state.token,
  userName: (state) => state.userInfo?.username || '',
  userId: (state) => state.userInfo?.id || null,
};

/**
 *
 * @type {{SET_TOKEN: mutations.SET_TOKEN, CLEAR_USER: mutations.CLEAR_USER, SET_USER_INFO: mutations.SET_USER_INFO}}
 */

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token;
    SessionToken.setToken(token);
  },
  SET_USER_INFO: (state, userInfo) => {
    state.userInfo = userInfo;
    SessionToken.setUserInfo(userInfo);
  },
  CLEAR_USER: (state) => {
    state.token = null;
    state.userInfo = null;
    SessionToken.clearToken();
    SessionToken.clearUserInfo();
  },
};

/**
 *
 * @type {{logout({commit: *, dispatch: *}): Promise<void>, fetchUserDetails({commit: *}): Promise<void>, clearQuizzes({commit: *}): void, login({commit: *, dispatch: *}, *): Promise<void>, register({commit: *, dispatch: *}, *): Promise<void>}}
 */

const actions = {
  async register({ commit, dispatch }, userDetails) {
    const response = await UserService.register(userDetails);
    commit('SET_TOKEN', response.token);
    await dispatch('fetchUserDetails');
  },
  async login({ commit, dispatch }, credentials) {
    const response = await UserService.login(credentials);
    commit('SET_TOKEN', response.token);
    if (response.name) {
      commit('SET_USER_INFO', { name: response.name });
    } else {
      await dispatch('fetchUserDetails');
    }
  },

  async fetchUserDetails({ commit }) {
    // The token is automatically attached via Axios interceptor, no need to pass it
    const userDetails = await UserService.getUserDetails();
    commit('SET_USER_INFO', userDetails);
  },

  clearQuizzes({ commit }) {
    commit('CLEAR_QUIZZES');
  },


  logout({ commit, dispatch }) {
    return new Promise((resolve, reject) => {
      try {
        commit('CLEAR_USER');
        SessionToken.clearToken();
        dispatch('quizzes/clearQuizzes', null, { root: true }); // Adjusted to dispatch to an action
        resolve();
      } catch (error) {
        console.error('Logout failed:', error);
        reject(error);
      }
    }).then(() => {
      router.push({ name: 'home' });
    }).catch(error => {
      console.error('Logout failed:', error);
    });
  },


};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
