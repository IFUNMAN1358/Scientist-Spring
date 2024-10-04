<template>
  <div class="password-container">
    <h2 class="password-title">СМЕНА ПАРОЛЯ</h2>
    <form @submit.prevent="onSubmit">
      <div class="form-group">
        <label for="oldPassword">Старый пароль</label>
        <input type="password" id="oldPassword" v-model="formData.oldPassword" @blur="validateOldPassword" required />
        <span v-if="errors.oldPassword" class="error-message">{{ errors.oldPassword }}</span>
      </div>

      <div class="form-group">
        <label for="newPassword">Новый пароль</label>
        <input type="password" id="newPassword" v-model="formData.newPassword" @blur="validateNewPassword" required />
        <span v-if="errors.newPassword" class="error-message">{{ errors.newPassword }}</span>
      </div>

      <div class="form-group">
        <label for="confirmNewPassword">Повторите новый пароль</label>
        <input type="password" id="confirmNewPassword" v-model="formData.confirmNewPassword" @blur="validateConfirmNewPassword" required />
        <span v-if="errors.confirmNewPassword" class="error-message">{{ errors.confirmNewPassword }}</span>
      </div>

      <button type="submit" class="password-button" :disabled="hasErrors">Изменить пароль</button>
    </form>
  </div>
</template>

<script>
import {handleChangePassword} from "@/js/service/userService";

export default {
  name: "PasswordComponent",
  data() {
    return {
      formData: {
        oldPassword: '',
        newPassword: '',
        confirmNewPassword: ''
      },
      errors: {
        oldPassword: null,
        newPassword: null,
        confirmNewPassword: null
      }
    };
  },
  computed: {
    hasErrors() {
      return Object.values(this.errors).some(error => error !== null);
    }
  },
  methods: {
    validateOldPassword() {
      if (!this.formData.oldPassword) {
        this.errors.oldPassword = 'Введите старый пароль';
      } else {
        this.errors.oldPassword = null;
      }
    },
    validateNewPassword() {
      if (this.formData.newPassword.length < 6) {
        this.errors.newPassword = 'Новый пароль должен быть не менее 6 символов';
      } else {
        this.errors.newPassword = null;
      }
    },
    validateConfirmNewPassword() {
      if (this.formData.confirmNewPassword !== this.formData.newPassword) {
        this.errors.confirmNewPassword = 'Пароли не совпадают';
      } else {
        this.errors.confirmNewPassword = null;
      }
    },
    async onSubmit() {
      this.validateOldPassword();
      this.validateNewPassword();
      this.validateConfirmNewPassword();

      if (!this.hasErrors) {
        const passwordForm = {
          oldPassword: this.formData.oldPassword,
          newPassword: this.formData.newPassword
        }
        await handleChangePassword(passwordForm);
      }
    }
  }
};
</script>

<style scoped>
.password-container {
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

.password-title {
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

.password-button {
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

.password-button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.password-button:hover {
  background-color: #C6C6C6;
}
</style>