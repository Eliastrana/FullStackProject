import { SessionToken } from '@/features/SessionToken.js';
import { UserService } from '@/services/UserService.js';

export default {
  namespaced: true,
  state: () => ({
    token: SessionToken.getToken(),
    isAuthenticated: !!SessionToken.getToken(),
  }),
  mutations: {
    SET_TOKEN(state, token) {
      state.token = token;
      state.isAuthenticated = !!token;
    },
    clearToken(state) {
      state.token = null;
      state.isAuthenticated = false;
    },
  },
  actions: {
    async register({ commit }, userDetails) {
      // eslint-disable-next-line no-useless-catch
      try {
        const data = await UserService.register(userDetails);
        commit('SET_TOKEN', data.token); // Example mutation to set the user's token
        // Handle any additional logic upon successful registration here
      } catch (error) {
        throw error;
      }
    },
    async login({ commit }, credentials) {
      // eslint-disable-next-line no-useless-catch
      try {
        const data = await UserService.login(credentials);
        commit('SET_TOKEN', data.token); // Use the actual property name containing the token in your response data
        // Optionally, handle additional logic upon successful login
      } catch (error) {
        throw error; // Rethrow the error to catch it in the component and provide feedback to the user
      }
    },
    logout({ commit }) {
      UserService.logout();
      commit('clearToken');
    },
  },
};
