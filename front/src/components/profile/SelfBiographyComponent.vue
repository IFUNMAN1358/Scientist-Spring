<template>
  <div class="biography-container">
    <h2 class="biography-title">Биография</h2>

    <div v-if="showDeleteModal" class="modal-overlay">
      <div class="modal">
        <h3>Вы уверены, что хотите удалить биографию?</h3>
        <p>Это действие необратимо.</p>
        <button @click="confirmDeleteBiography" class="confirm-button">Удалить</button>
        <button @click="cancelDeleteBiography" class="cancel-button">Отмена</button>
      </div>
    </div>

    <div v-if="biographyExists">
      <img v-if="biographyImage" :src="biographyImage" alt="Фотография" class="biography-image" />
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

      <button @click="onEditBiography" class="create-button">Обновить</button>
      <button @click="showDeleteBiography" class="create-button">Удалить</button>
    </div>

    <form v-else @submit.prevent="onSubmit">
      <div class="form-group">
        <label for="image">Фото</label>
        <input type="file" id="image" @change="onImageChange" />
        <div v-if="imagePreview" class="image-preview">
          <img :src="imagePreview" alt="Предпросмотр изображения" />
        </div>
      </div>

      <div class="form-group">
        <label for="degree">Ученая степень</label>
        <input type="text" id="degree" v-model="formData.degree" required />
      </div>

      <div class="form-group">
        <label for="rank">Ученое звание</label>
        <input type="text" id="rank" v-model="formData.rank" required />
      </div>

      <div class="form-group">
        <label for="scienceName">Научное направление</label>
        <select id="scienceName" v-model="formData.scienceName" required>
          <option v-for="science in sciences" :key="science.scienceId" :value="science.name">
            {{ science.name }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label for="country">Страна</label>
        <select id="country" v-model="formData.country" @change="onCountryChange" required>
          <option v-for="location in uniqueCountries" :key="location" :value="location">
            {{ location }}
          </option>
        </select>
      </div>

      <div class="form-group" v-if="isRussia && availableRegions.length > 0">
        <label for="region">Регион</label>
        <select id="region" v-model="formData.region" required>
          <option v-for="region in availableRegions" :key="region" :value="region">
            {{ region }}
          </option>
        </select>
      </div>
      <div v-else>
        <small v-if="formData.country && !isRussia">Регион вводится только для России.</small>
      </div>

      <div class="form-group">
        <label for="content">Описание</label>
        <textarea id="content" v-model="formData.content" rows="5" required></textarea>
      </div>

      <div class="form-group">
        <label for="publications">Публикации</label>
        <textarea id="publications" v-model="formData.publications" rows="5"></textarea>
      </div>

      <div class="warning-message">
        <p>После создания биография будет проверена менеджером. В случае одобрения она будет опубликована.</p>
      </div>

      <button type="submit" class="create-button" :disabled="hasErrors">{{ isEditing ? 'Обновить' : 'Создать' }} биографию</button>
    </form>
  </div>
</template>

<script>
import {
  handleCreateBiography,
  handleDeleteSelfBiography,
  handleGetBiographyImage,
  handleGetSelfUserBiography,
  handleUpdateBiography
} from "@/js/service/biographyService";
import {getAllScience} from "@/js/service/scienceService";
import {getAllLocation} from "@/js/service/locationService";

export default {
  name: "SelfBiographyComponent",
  data() {
    return {
      user: null,
      biographyExists: false,
      isEditing: false,
      biographyImage: null,
      sciences: [],
      locations: [],
      formData: {
        image: null,
        degree: '',
        rank: '',
        scienceName: '',
        country: '',
        region: '',
        content: '',
        publications: ''
      },
      imagePreview: null,
      isRussia: false,
      errors: {},
      showDeleteModal: false,
      availableRegions: []
    };
  },
  async mounted() {
    try {
      this.sciences = await getAllScience();
      this.locations = await getAllLocation();
      const userBiography = await handleGetSelfUserBiography();
      if (userBiography.biography) {
        this.user = userBiography;
        this.biographyExists = true;
        this.biographyImage = await handleGetBiographyImage(userBiography.biography.imageName);
      }
    } catch (error) {
      console.error("Failed to load biography", error);
    }
  },
  computed: {
    uniqueCountries() {
      return [...new Set(this.locations.map(location => location.country))];
    },
    hasErrors() {
      return Object.values(this.errors).some(error => error !== null);
    }
  },
  methods: {
    onImageChange(event) {
      const file = event.target.files[0];
      if (file) {
        this.formData.image = file;
        const reader = new FileReader();
        reader.onload = (e) => {
          this.imagePreview = e.target.result;
        };
        reader.readAsDataURL(file);
      }
    },
    checkCountry() {
      this.isRussia = this.formData.country.toLowerCase() === 'россия';
      if (!this.isRussia) {
        this.formData.region = '';
        this.availableRegions = [];
      }
    },
    onCountryChange() {
      this.checkCountry();
      if (this.isRussia) {
        this.availableRegions = this.locations
          .filter(location => location.country === this.formData.country)
          .map(location => location.region);
      } else {
        this.availableRegions = [];
      }
    },
    async onSubmit() {
      if (!this.hasErrors) {
        const biographyForm = new FormData();
        if (this.formData.image) {
          biographyForm.append('image', this.formData.image);
        }
        biographyForm.append('degree', this.formData.degree);
        biographyForm.append('rank', this.formData.rank);
        biographyForm.append('scienceName', this.formData.scienceName);
        biographyForm.append('country', this.formData.country);
        if (this.isRussia) {
          biographyForm.append('region', this.formData.region || null);
        } else {
          biographyForm.append('region', null);
        }
        biographyForm.append('content', this.formData.content);
        biographyForm.append('publications', this.formData.publications);

        if (this.isEditing) {
          await handleUpdateBiography(biographyForm);
        } else {
          await handleCreateBiography(biographyForm);
        }
      }
    },
    onEditBiography() {
      this.formData = {
        image: null,
        degree: this.user.biography.degree,
        rank: this.user.biography.rank,
        scienceName: this.user.biography.science.name,
        country: this.user.biography.location.country,
        region: this.user.biography.location.region,
        content: this.user.biography.content,
        publications: this.user.biography.publications
      };
      this.imagePreview = this.biographyImage;
      this.isEditing = true;
      this.biographyExists = false;
      this.checkCountry();
    },
    showDeleteBiography() {
      this.showDeleteModal = true;
    },
    cancelDeleteBiography() {
      this.showDeleteModal = false;
    },
    async confirmDeleteBiography() {
      await handleDeleteSelfBiography();
      this.biographyExists = false;
      this.showDeleteModal = false;
    }
  }
};
</script>

<style scoped>
.biography-container {
  background-color: #F4F4F4;
  border-radius: 15px;
  padding: 20px;
  width: 100%;
  max-width: 600px;
  margin: 0 auto;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  box-sizing: border-box;
  text-align: center;
}

.biography-title {
  font-size: 24px;
  font-weight: bold;
  color: #343434;
  margin-bottom: 20px;
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

.form-group input, .form-group select, .form-group textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
}

.image-preview img {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  object-fit: cover;
  margin-top: 10px;
  margin-bottom: 20px;
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

.warning-message {
  color: #ff4d4d;
  font-size: 12px;
  margin-top: 10px;
}

.create-button {
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

.create-button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.create-button:hover {
  background-color: #C6C6C6;
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

.confirm-button {
  background-color: #ff4d4d;
  color: #fff;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-right: 10px;
}

.cancel-button {
  background-color: #ccc;
  color: #333;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.confirm-button:hover {
  background-color: #e60000;
}

.cancel-button:hover {
  background-color: #bbb;
}
</style>
