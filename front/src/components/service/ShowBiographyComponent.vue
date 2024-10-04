<template>
  <div class="biography-details-container">
    <div v-if="loading" class="loading">Загрузка...</div>
    <div v-else-if="biography">
      <div class="biography-card">
        <img v-if="biography.biography.imageName" :src="biography.biographyImage" alt="Фотография" class="biography-image" />
        <h3>ФИО:</h3>
        <p>{{ biography.lastName }} {{ biography.firstName }} {{ biography.patronymic }}</p>
        <h3>Ученая степень:</h3>
        <p>{{ biography.biography.degree }}</p>
        <h3>Ученое звание:</h3>
        <p>{{ biography.biography.rank }}</p>
        <h3>Научное направление:</h3>
        <p>{{ biography.biography.science.name }}</p>
        <h3>Страна:</h3>
        <p>{{ biography.biography.location.country }}</p>
        <h3 v-if="biography.biography.location.region">Регион:</h3>
        <p v-if="biography.biography.location.region">{{ biography.biography.location.region }}</p>
        <h3>Описание:</h3>
        <p>{{ biography.biography.content }}</p>
        <h3>Публикации:</h3>
        <p>{{ biography.biography.publications }}</p>
      </div>
    </div>
    <div v-else class="no-biography">Биография не найдена</div>
  </div>
</template>

<script>
import { handleGetUserBiographyByUserId, handleGetBiographyImage } from "@/js/service/biographyService";

export default {
  name: "ShowBiographyComponent",
  data() {
    return {
      biography: null,
      loading: true,
    };
  },
  async mounted() {
    const userId = this.$route.params.id;
    try {
      this.biography = await handleGetUserBiographyByUserId(userId);

      if (this.biography.biography.imageName) {
        this.biography.biographyImage = await handleGetBiographyImage(this.biography.biography.imageName);
      } else {
        this.biography.biographyImage = null;
      }
    } catch (error) {
      console.error("Ошибка при загрузке биографии:", error);
    } finally {
      this.loading = false;
    }
  },
};
</script>

<style scoped>
.biography-details-container {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.loading {
  text-align: center;
  font-size: 18px;
}

.no-biography {
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
</style>