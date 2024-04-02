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
};

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token;
    SessionToken.setToken(token); // Persist token changes to the session
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

const actions = {
  async register({ dispatch }, userDetails) {
    try {
      const { token } = await UserService.register(userDetails);
      dispatch('fetchUserDetails', token);
    } catch (error) {
      console.error('Registration error:', error);
      throw error;
    }
  },
  async login({ dispatch }, credentials) {
    try {
      const { token } = await UserService.login(credentials);
      dispatch('fetchUserDetails', token);
    } catch (error) {
      console.error('Login error:', error);
      throw error;
    }
  },
  async fetchUserDetails({ commit }, token) {
    try {
      const userDetails = await UserService.getUserDetails(token);
      commit('SET_TOKEN', token);
      commit('SET_USER_INFO', userDetails);
    } catch (error) {
      commit('CLEAR_USER');
      throw error;
    }
  },
  logout({ commit }) {
    UserService.logout();
    commit('CLEAR_USER');
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
