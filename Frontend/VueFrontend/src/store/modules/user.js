//user.js
import { SessionToken } from '@/features/SessionToken.js';
import { UserService } from '@/services/UserService.js';

export default {
  namespaced: true,
  state: () => ({
    token: SessionToken.getToken(),
    isAuthenticated: !!SessionToken.getToken(),
    userInfo: null, // Add this line to store user information

  }),
  mutations: {
    SET_TOKEN(state, token) {
      state.token = token;
      state.isAuthenticated = !!token;
    },
    setUser(state, user) { // Ensure this mutation is present
      state.userInfo = user;
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
        commit('setUser', data.user); // Assuming `data` includes the user info

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

// Assuming you have a Vuex setup like this in user.js

export const state = () => ({
  userInfo: null, // Store user information here
});

export const mutations = {
  setUser(state, user) {
    state.userInfo = user;
  },
};

// export const actions = {
//   async login({ commit }, { username, password }) {
//     try {
//       // Here you would typically make an API call to authenticate the user
//       // For demonstration, let's assume this is the response
//       const response = await fakeLoginApiCall(username, password);
//       // Once authenticated, store the user's information
//       commit('setUser', response.data.user);
//       return response.data.user;
//     } catch (error) {
//       console.error('Login error:', error);
//       throw error; // Rethrow or handle as needed
//     }
//   },
//   logout({ commit }) {
//     commit('setUser', null); // Reset user info on logout
//     // Here, you'd also handle cleanup like clearing tokens
//   },
// };

export const getters = {
  isAuthenticated(state) {
    return !!state.userInfo; // Boolean conversion to check if userInfo exists
  },
  userName(state) {
    return state.userInfo ? state.userInfo.name : '';
  },
};

// This is a fake login function to simulate an API call
// async function fakeLoginApiCall(username, password) {
//   // Simulate network delay
//   await new Promise(resolve => setTimeout(resolve, 1000));
//   // Simulate a successful login response with user information
//   return {
//     data: {
//       user: {
//         name: 'John Doe', // Example user name
//         username: username,
//         // other user information
//       },
//       // you would typically include more data like tokens
//     }
//   };
// }

