import {getCookie} from "@/js/utils/cookie";
import router from "@/js/config/router";
import store from "@/js/config/store";
import axios from "axios";

export async function handleRegister(registerForm) {
    try {
        const registerResponse = await axios.post('/api/auth/registration', registerForm);
        await store.actions.login(
            registerResponse.data.jwt.accessToken,
            registerResponse.data.jwt.refreshToken
        );
        await router.push({ name: 'Profile' });
    } catch (error) {
        console.error('Registration failed:', error);
    }
}

export async function handleLogin(loginForm) {
    try {
        const loginResponse = await axios.post('/api/auth/login', loginForm);
        await store.actions.login(
            loginResponse.data.jwt.accessToken,
            loginResponse.data.jwt.refreshToken
        );
        await router.push({ name: 'Profile' });
    } catch (error) {
        console.error('Login failed:', error);
    }
}

export async function handleLogout() {
    try {
        const accessToken = getCookie("accessToken");
        await axios.post('/api/auth/logout', null, {
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        });
        await store.actions.logout();
        await router.push({ name: 'Search' });
    } catch (error) {
        console.error('Logout failed:', error);
    }
}