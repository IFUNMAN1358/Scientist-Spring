<template>
  <div class="login-container">
    <h2 class="login-title">ВХОД</h2>
    <form @submit.prevent="onSubmit">
      <div class="form-group">
        <label for="email">Email</label>
        <input type="email" id="email" v-model="loginForm.email" required />
      </div>

      <div class="form-group">
        <label for="password">Пароль</label>
        <input type="password" id="password" v-model="loginForm.password" required />
      </div>

      <button type="submit" class="login-button">Войти</button>
    </form>
  </div>
</template>

<script>
import {handleLogin} from "@/js/service/authService";

export default {
  name: "LoginComponent",
  data() {
    return {
      loginForm: {
        email: '',
        password: ''
      }
    };
  },
  methods: {
    async onSubmit() {
      await handleLogin(this.loginForm);
    }
  }
};
</script>

<style>
.login-container {
  background-color: #F4F4F4;
  border-radius: 15px;
  padding: 20px;
  width: 100%;
  max-width: 400px;
  margin: 0 auto;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  box-sizing: border-box;
  text-align: center;
}

.login-title {
  font-size: 24px;
  font-weight: bold;
  color: #343434;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
  text-align: left;
}

.form-group label {
  color: #343434;
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}

.form-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
}

.login-button {
  width: 100%;
  padding: 10px;
  background-color: #E2E0E0;
  color: #343434;
  font-size: 16px;
  font-weight: bold;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 20px;
  transition: background-color 0.3s ease;
}

.login-button:hover {
  background-color: #C6C6C6;
}
</style>
