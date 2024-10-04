import { reactive, computed } from 'vue';
import {jwtDecode} from 'jwt-decode';
import {removeCookies, setCookies} from "@/js/utils/cookie";

const state = reactive({
  accessToken: null,
  roles: [],
});

const getters = {
  hasRole: (role) => computed(() => state.roles.includes(role)),
};

const actions = {
  login(accessToken, refreshToken) {
    state.accessToken = accessToken;
    const decodedToken = jwtDecode(accessToken);

    if (decodedToken && decodedToken.roles) {
      state.roles = decodedToken.roles;
    }
    localStorage.setItem('accessToken', accessToken);
    localStorage.setItem('roles', JSON.stringify(state.roles));
    setCookies({
      'accessToken': accessToken,
      'refreshToken': refreshToken
    });
  },
  logout() {
    state.accessToken = null;
    state.roles = [];
    localStorage.removeItem('accessToken');
    localStorage.removeItem('roles');
    removeCookies('accessToken', 'refreshToken');
  },
  initializeStore() {
    const accessToken = localStorage.getItem('accessToken');
    const roles = localStorage.getItem('roles');

    if (accessToken && !actions.isTokenExpired(accessToken)) {
      state.accessToken = accessToken;
      if (roles) {
        state.roles = JSON.parse(roles);
      }
    } else {
      actions.logout();

    }
  },
  isTokenExpired(token) {
    try {
      const decoded = jwtDecode(token);
      const now = Math.floor(Date.now() / 1000);
      return decoded.exp < now;
    } catch (e) {
      return true;
    }
  }
};

export default {
  state,
  getters,
  actions,
};