// store/modules/user.js
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

const actions = {
  async register({ commit }, userDetails) {
    const response = await UserService.register(userDetails);
    commit('SET_TOKEN', response.token);
    commit('SET_USER_INFO', response.user); // Assuming `user` is part of the response for registration
    // No need to dispatch 'fetchUserDetails' if registration already includes necessary user info
  },
  async login({ commit, dispatch }, credentials) {
    const response = await UserService.login(credentials);
    commit('SET_TOKEN', response.token);
    // Directly setting user info upon login if the API response includes it, otherwise, dispatch 'fetchUserDetails'
    if (response.name) {
      commit('SET_USER_INFO', { name: response.name });
    } else {
      // Assuming the token is needed to fetch user details separately
      await dispatch('fetchUserDetails');
    }
  },
  async fetchUserDetails({ commit }) {
    // The token is automatically attached via Axios interceptor, no need to pass it
    const userDetails = await UserService.getUserDetails();
    commit('SET_USER_INFO', userDetails);
  },
  logout({ commit }) {
    UserService.logout(); // Handle server-side logout if needed
    commit('CLEAR_USER');
    // Assuming 'quizzes/CLEAR_QUIZZES' is managed in another module, keep this action to clear quizzes upon logout
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
