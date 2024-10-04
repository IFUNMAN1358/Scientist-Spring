<template>
  <header class="header">
    <div class="logo">ДЕЯТЕЛИ НАУКИ</div>
    <nav class="navigation">
      <router-link :to="{name: 'Search'}" class="navigation-button">ПОИСК</router-link>
      <router-link :to="{name: 'Science'}" class="navigation-button">НАУКИ</router-link>
      <router-link :to="{name: 'Location'}" class="navigation-button">РЕГИОНЫ</router-link>
    </nav>
    <div class="auth-buttons">
      <router-link v-if="hasManagerRole" :to="{name: 'ConfirmBiography'}" class="auth-button">БИОГРАФИИ</router-link>
      <router-link v-if="hasUserRole" :to="{name: 'Profile'}" class="auth-button">ЛИЧНЫЙ КАБИНЕТ</router-link>
      <router-link v-if="!hasUserRole" :to="{name: 'Login'}" class="auth-button">ВХОД</router-link>
      <router-link v-if="!hasUserRole" :to="{name: 'Register'}" class="auth-button">РЕГИСТРАЦИЯ</router-link>
    </div>
  </header>
</template>

<script>
import store from "@/js/config/store";

export default {
  name: "HeaderComponent",
  setup() {
    const hasUserRole = store.getters.hasRole('ROLE_USER');
    const hasManagerRole = store.getters.hasRole('ROLE_MANAGER');
    return { hasUserRole, hasManagerRole };
  },
}
</script>

<style>
.header {
  background-color: #CAC0CC;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  width: 100%;
  box-sizing: border-box;
}

.logo {
  font-family: 'Roboto', sans-serif;
  font-weight: bold;
  color: #343434;
  font-size: 24px;
}

.navigation {
  display: flex;
  gap: 20px;
}

.navigation-button {
  background: none;
  border: none;
  font-family: 'Roboto', sans-serif;
  font-weight: bold;
  color: #343434;
  font-size: 18px;
  cursor: pointer;
}

.auth-buttons {
  display: flex;
  gap: 10px;
}

.auth-button {
  background: none;
  border: none;
  font-family: 'Roboto', sans-serif;
  font-weight: bold;
  color: #343434;
  font-size: 20px;
  cursor: pointer;
}

a.navigation-button, a.auth-button {
  text-decoration: none;
}

a.navigation-button:hover, a.auth-button:hover {
  text-decoration: none;
}

@media (max-width: 768px) {
  .header {
    flex-direction: column;
    align-items: center;
  }

  .logo {
    font-size: 24px;
    margin-bottom: 10px;
  }

  .navigation {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 5px;
    margin-bottom: 10px;
  }

  .auth-buttons {
    display: flex;
    gap: 5px;
  }

  .navigation-button, .auth-button {
    font-size: 18px;
    padding: 10px 15px;
  }
}
</style>
