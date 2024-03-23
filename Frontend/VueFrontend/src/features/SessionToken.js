export const SessionToken = {
  setToken(token) {
    sessionStorage.setItem('sessionToken', token);
  },
  getToken() {
    return sessionStorage.getItem('sessionToken');
  },
  clearToken() {
    sessionStorage.removeItem('sessionToken');
  },
};
