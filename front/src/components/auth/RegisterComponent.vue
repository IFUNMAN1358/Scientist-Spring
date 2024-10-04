<template>
  <div class="register-container">
    <h2 class="register-title">РЕГИСТРАЦИЯ</h2>
    <form @submit.prevent="onSubmit">
      <div class="form-group">
        <label for="email">Email</label>
        <input type="email" id="email" v-model="formData.email" @blur="validateEmail" required />
        <span v-if="errors.email" class="error-message">{{ errors.email }}</span>
      </div>

      <div class="form-group">
        <label for="password">Пароль</label>
        <input type="password" id="password" v-model="formData.password" @blur="validatePassword" required />
        <span v-if="errors.password" class="error-message">{{ errors.password }}</span>
      </div>

      <div class="form-group">
        <label for="confirmPassword">Повторите пароль</label>
        <input type="password" id="confirmPassword" v-model="formData.confirmPassword" @blur="validateConfirmPassword" required />
        <span v-if="errors.confirmPassword" class="error-message">{{ errors.confirmPassword }}</span>
      </div>

      <div class="form-group">
        <label for="firstName">Имя</label>
        <input type="text" id="firstName" v-model="formData.firstName" @blur="validateFirstName" required />
        <span v-if="errors.firstName" class="error-message">{{ errors.firstName }}</span>
      </div>

      <div class="form-group">
        <label for="lastName">Фамилия</label>
        <input type="text" id="lastName" v-model="formData.lastName" @blur="validateLastName" required />
        <span v-if="errors.lastName" class="error-message">{{ errors.lastName }}</span>
      </div>

      <div class="form-group">
        <label for="patronymic">Отчество</label>
        <input type="text" id="patronymic" v-model="formData.patronymic" />
      </div>

      <button type="submit" class="register-button" :disabled="hasErrors">Зарегистрироваться</button>
    </form>
  </div>
</template>

<script>
import { handleRegister } from "@/js/service/authService";

export default {
  name: "RegisterComponent",
  data() {
    return {
      formData: {
        email: '',
        password: '',
        confirmPassword: '',
        firstName: '',
        lastName: '',
        patronymic: ''
      },
      errors: {
        email: null,
        password: null,
        confirmPassword: null,
        firstName: null,
        lastName: null
      }
    };
  },
  computed: {
    hasErrors() {
      return Object.values(this.errors).some(error => error !== null);
    }
  },
  methods: {
    validateEmail() {
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!this.formData.email) {
        this.errors.email = 'Введите email';
      } else if (!emailPattern.test(this.formData.email)) {
        this.errors.email = 'Некорректный email';
      } else {
        this.errors.email = null;
      }
    },
    validatePassword() {
      if (this.formData.password.length < 6) {
        this.errors.password = 'Пароль должен быть не менее 6 символов';
      } else {
        this.errors.password = null;
      }
    },
    validateConfirmPassword() {
      if (this.formData.confirmPassword !== this.formData.password) {
        this.errors.confirmPassword = 'Пароли не совпадают';
      } else {
        this.errors.confirmPassword = null;
      }
    },
    validateFirstName() {
      if (!this.formData.firstName) {
        this.errors.firstName = 'Введите имя';
      } else {
        this.errors.firstName = null;
      }
    },
    validateLastName() {
      if (!this.formData.lastName) {
        this.errors.lastName = 'Введите фамилию';
      } else {
        this.errors.lastName = null;
      }
    },
    async onSubmit() {
      this.validateEmail();
      this.validatePassword();
      this.validateConfirmPassword();
      this.validateFirstName();
      this.validateLastName();

      if (!this.hasErrors) {
        const registerForm = {
          email: this.formData.email,
          password: this.formData.password,
          firstName: this.formData.firstName,
          lastName: this.formData.lastName,
          patronymic: this.formData.patronymic
        };
        await handleRegister(registerForm);
      }
    }
  }
};
</script>

<style>
.register-container {
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

.register-title {
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

.error-message {
  color: #ff4d4d;
  font-size: 12px;
  margin-top: 5px;
}

.register-button {
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

.register-button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.register-button:hover {
  background-color: #C6C6C6;
}
</style>