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
  setUserInfo(userInfo) {
    sessionStorage.setItem('userInfo', JSON.stringify(userInfo));
  },
  getUserInfo() {
    const userInfo = sessionStorage.getItem('userInfo');
    return userInfo ? JSON.parse(userInfo) : null;
  },
  clearUserInfo() {
    sessionStorage.removeItem('userInfo');
  },
};
