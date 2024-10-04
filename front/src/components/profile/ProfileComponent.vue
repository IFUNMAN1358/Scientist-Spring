<template>
  <div class="profile-container">
    <h2 class="profile-title">ПРОФИЛЬ</h2>
    <div class="button-group">
      <button @click="$router.push({name: 'Password'})" class="profile-button">Смена пароля</button>
      <button @click="$router.push({name: 'SelfBiography'})" class="profile-button">Биография</button>
      <button @click="submitLogout" class="profile-button">Выйти из аккаунта</button>
    </div>
  </div>
</template>

<script>
import {handleLogout} from "@/js/service/authService";

export default {
  name: "ProfileComponent",
  methods: {
    async submitLogout() {
      await handleLogout();
    }
  }
};
</script>

<style>
.profile-container {
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

.profile-title {
  font-size: 24px;
  font-weight: bold;
  color: #343434;
  margin-bottom: 20px;
}

.button-group {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.profile-button {
  width: 100%;
  padding: 10px;
  background-color: #E2E0E0;
  color: #343434;
  font-size: 16px;
  font-weight: bold;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.profile-button:hover {
  background-color: #C6C6C6;
}
</style>