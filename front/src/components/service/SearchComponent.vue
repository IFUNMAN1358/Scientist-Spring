<template>
  <div class="search-container">
    <h2 class="search-title">Поиск биографии</h2>
    <input
      v-model="searchQuery"
      type="text"
      placeholder="ФИО ученого"
      class="search-input"
    />
    <button @click="searchBiography" class="create-button">Поиск</button>

    <ul v-if="biographies.length > 0" class="biography-list">
      <li v-for="biography in biographies" :key="biography.userId" @click="openBiography(biography.userId)">
        <span>{{ biography.lastName }} {{ biography.firstName }} {{ biography.patronymic }}</span>
        <span class="attributes">{{ biography.biography.degree }}, {{ biography.biography.rank }}</span>
      </li>
    </ul>
    <p v-if="!biographies.length && !loading && searchPerformed">Ничего не найдено</p>
  </div>
</template>

<script>
import {handleGetAllUserBiographyByFio} from "@/js/service/biographyService";
import router from "@/js/config/router";

export default {
  name: "SearchComponent",
  data() {
    return {
      searchQuery: "",
      biographies: [],
      loading: false,
      searchPerformed: false,
    };
  },
  methods: {
    async searchBiography() {
      if (this.searchQuery.trim() === "") {
        return;
      }

      this.loading = true;
      this.searchPerformed = false;

      try {
        this.biographies = await handleGetAllUserBiographyByFio(this.searchQuery);
        this.searchPerformed = true;
      } catch (error) {
        console.error("Ошибка при поиске биографий:", error);
      } finally {
        this.loading = false;
      }
    },
    openBiography(userId) {
      router.push({name: 'ShowBiography', params: {id: userId}});
    }
  }
};
</script>

<style scoped>
.search-container {
  background-color: #F4F4F4;
  border-radius: 15px;
  padding: 20px;
  width: 100%;
  max-width: 600px;
  margin: 0 auto;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  box-sizing: border-box;
}

.search-title {
  font-size: 24px;
  font-weight: bold;
  color: #343434;
  margin-bottom: 20px;
  text-align: center;
}

.search-input {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 10px;
  box-sizing: border-box;
  transition: border-color 0.3s ease;
}

.search-input:focus {
  outline: none;
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

.biography-list {
  list-style: none;
  padding: 0;
}

.biography-list li {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
  border-bottom: 1px solid #ccc;
  cursor: pointer;
}

.attributes {
  font-size: 14px;
  color: #777;
}
</style>