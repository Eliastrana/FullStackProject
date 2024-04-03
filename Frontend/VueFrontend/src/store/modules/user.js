// user.js
import { SessionToken } from '@/features/SessionToken.js';
import { UserService } from '@/services/UserService.js';

const state = () => ({
  token: SessionToken.getToken(),
  userInfo: SessionToken.getUserInfo(),
});

const getters = {
  isAuthenticated: (state) => !!state.token,
  userName: (state) => state.userInfo?.username || '',
  userId: (state) => state.userInfo?.id || null,
};

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token;
    SessionToken.setToken(token); // Persist token changes to the session
  },
  SET_USER_INFO: (state, userInfo) => {
    state.userInfo = userInfo;
    SessionToken.setUserInfo(userInfo); // Persist user info changes to the session
  },
  CLEAR_USER: (state) => {
    state.token = null;
    state.userInfo = null;
    SessionToken.clearToken(); // Clear the token from the session storage
    SessionToken.clearUserInfo(); // Clear the user info from the session storage
  },
};

const actions = {
  async register({ dispatch }, userDetails) {
    try {
      const response = await UserService.register(userDetails);
      if (response.token) {
        dispatch('fetchUserDetails', response.token);
      }
    } catch (error) {
      console.error('Registration error:', error);
      throw error;
    }
  },
  async login({ dispatch }, credentials) {
    try {
      const response = await UserService.login(credentials);
      if (response.token) {
        dispatch('fetchUserDetails', response.token);
      }
    } catch (error) {
      console.error('Login error:', error);
      throw error;
    }
  },
  async fetchUserDetails({ commit }, token) {
    try {
      const userDetails = await UserService.getUserDetails(token);
      if (!userDetails.id) {
        console.log('User ID not found in response:', userDetails);
      }
      console.log('User details:', userDetails);
      commit('SET_TOKEN', token);
      commit('SET_USER_INFO', userDetails);
    } catch (error) {
      console.error('Fetching user details failed:', error);
      commit('CLEAR_USER');
    }
  },
  logout({ commit }) {
    UserService.logout(); // Logout the user on the server-side (if applicable)
    commit('CLEAR_USER'); // Clear user information from the state
    commit('quizzes/CLEAR_QUIZZES', null, { root: true }); // Clears quizzes associated with the user
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
