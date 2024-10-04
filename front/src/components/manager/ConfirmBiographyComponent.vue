<template>
  <div class="biography-confirm-container">
    <div v-if="loading" class="loading">Загрузка...</div>
    <div v-if="!loading && unconfirmedBiographies.length === 0" class="no-biographies">
      Нет неподтвержденных биографий
    </div>

    <div v-for="user in unconfirmedBiographies" :key="user.biography.biographyId" class="biography-card">
      <img v-if="user.biographyImage" :src="user.biographyImage" alt="Фотография" class="biography-image" />
      <h3>ФИО:</h3>
      <p>{{ user.lastName }} {{ user.firstName }} {{ user.patronymic }}</p>
      <h3>Ученая степень:</h3>
      <p>{{ user.biography.degree }}</p>
      <h3>Ученое звание:</h3>
      <p>{{ user.biography.rank }}</p>
      <h3>Научное направление:</h3>
      <p>{{ user.biography.science.name }}</p>
      <h3>Страна:</h3>
      <p>{{ user.biography.location.country }}</p>
      <h3 v-if="user.biography.location.region">Регион:</h3>
      <p v-if="user.biography.location.region">{{ user.biography.location.region }}</p>
      <h3>Описание:</h3>
      <p>{{ user.biography.content }}</p>
      <h3>Публикации:</h3>
      <p>{{ user.biography.publications }}</p>

      <div class="action-buttons">
        <button @click="openModal(user, 'confirm')" class="confirm-button">Подтвердить</button>
        <button @click="openModal(user, 'delete')" class="delete-button">Удалить</button>
      </div>
    </div>

    <div v-if="showModal" class="modal-overlay">
      <div class="modal">
        <h3>{{ modalTitle }}</h3>
        <p>{{ modalMessage }}</p>
        <button @click="handleAction" class="cancel-button">{{ modalActionText }}</button>
        <button @click="closeModal" class="cancel-button">Отмена</button>
      </div>
    </div>
  </div>
</template>

<script>
import {
  handleGetAllUnconfirmedBiography,
  handleSetBiographyConfirm,
  handleDeleteBiographyByUserId,
  handleGetBiographyImage
} from "@/js/service/biographyService";

export default {
  name: "ConfirmBiographyComponent",
  data() {
    return {
      unconfirmedBiographies: [],
      loading: true,
      showModal: false,
      currentBiography: null,
      actionType: null,
      modalTitle: '',
      modalMessage: '',
      modalActionText: '',
    };
  },
  async mounted() {
    try {
      const data = await handleGetAllUnconfirmedBiography();
      if (Array.isArray(data)) {
        this.unconfirmedBiographies = data;
        await this.loadBiographyImages();
      } else {
        console.error("Ожидался массив биографий", data);
      }
    } catch (error) {
      console.error("Не удалось загрузить неподтверждённые биографии", error);
    } finally {
      this.loading = false;
    }
  },
  methods: {
    async loadBiographyImages() {
      const promises = this.unconfirmedBiographies.map(async (user) => {
        if (user.biography.imageName) {
          try {
            user.biographyImage = await handleGetBiographyImage(user.biography.imageName);
          } catch (error) {
            console.error(`Не удалось загрузить изображение для пользователя ${user.userId}`, error);
            user.biographyImage = null;
          }
        } else {
          user.biographyImage = null;
        }
      });
      await Promise.all(promises);
    },
    openModal(biography, action) {
      this.currentBiography = biography;
      this.actionType = action;
      if (action === 'confirm') {
        this.modalTitle = "Подтверждение биографии";
        this.modalMessage = "Вы уверены, что хотите подтвердить эту биографию?";
        this.modalActionText = "Подтвердить";
      } else if (action === 'delete') {
        this.modalTitle = "Удаление биографии";
        this.modalMessage = "Вы уверены, что хотите удалить эту биографию? Это действие необратимо.";
        this.modalActionText = "Удалить";
      }
      this.showModal = true;
    },
    closeModal() {
      this.showModal = false;
      this.currentBiography = null;
      this.actionType = null;
    },
    async handleAction() {
      if (!this.currentBiography || !this.actionType) {
        return;
      }

      if (this.actionType === 'confirm') {
        try {
          await handleSetBiographyConfirm(this.currentBiography.userId);
          this.unconfirmedBiographies = this.unconfirmedBiographies.filter(b => b.userId !== this.currentBiography.userId);
          this.closeModal();
        } catch (error) {
          console.error("Не удалось подтвердить биографию", error);
        }
      } else if (this.actionType === 'delete') {
        try {
          await handleDeleteBiographyByUserId(this.currentBiography.userId);
          this.unconfirmedBiographies = this.unconfirmedBiographies.filter(b => b.userId !== this.currentBiography.userId);
          this.closeModal();
        } catch (error) {
          console.error("Не удалось удалить биографию", error);
        }
      }
    }
  }
};
</script>

<style scoped>
.biography-confirm-container {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.loading {
  text-align: center;
  font-size: 18px;
}

.no-biographies {
  text-align: center;
  font-size: 16px;
  color: #666;
}

.biography-card {
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

h3 {
  font-family: "Roboto", sans-serif;
  color: #343434;
  text-align: left;
  margin: 5px 0 10px;
}

p {
  font-family: "Roboto", sans-serif;
  color: #343434;
  text-align: left;
  margin-bottom: 20px;
  word-wrap: break-word;
  white-space: pre-wrap;
  overflow-wrap: break-word;
}

.biography-image {
  width: 250px;
  height: 300px;
  object-fit: cover;
  border-radius: 10px;
  display: block;
  margin: 0 auto;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.action-buttons {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.confirm-button, .delete-button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-weight: bold;
  transition: background-color 0.3s ease;
}

.confirm-button {
  background-color: #4CAF50;
  color: white;
}

.confirm-button:hover {
  background-color: #45a049;
}

.delete-button {
  background-color: #F44336;
  color: white;
}

.delete-button:hover {
  background-color: #e53935;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal {
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  width: 400px;
  text-align: center;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.modal h3 {
  margin-bottom: 10px;
  font-size: 20px;
  color: #333;
}

.modal p {
  margin-bottom: 20px;
  font-size: 16px;
  color: #666;
}

.cancel-button {
  background-color: #ccc;
  color: #333;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-right: 10px;
  transition: background-color 0.3s ease;
}

.cancel-button:hover {
  background-color: #bbb;
}
</style>