<template>
  <div class="biography-list-container">
    <h2 class="biography-list-title">Список биографий</h2>
    <ul v-if="biographies.length > 0">
      <li v-for="biography in sortedBiographies" :key="biography.userId" @click="openBiography(biography.userId)">
        <span>{{ biography.lastName }} {{ biography.firstName }} {{ biography.patronymic }}</span>
        <span class="attributes">{{ biography.biography.degree }}, {{ biography.biography.rank }}</span>
      </li>
    </ul>
    <p v-else>Биографии не найдены</p>
  </div>
</template>

<script>
import { handleGetAllBiographyByScienceId, handleGetAllBiographyByLocationId } from "@/js/service/biographyService";
import router from "@/js/config/router";

export default {
  name: "BiographyListComponent",
  data() {
    return {
      biographies: [],
    };
  },
  async mounted() {
    const { type, id } = this.$route.params;

    if (type === "science") {
      this.biographies = await handleGetAllBiographyByScienceId(id);
    } else if (type === "location") {
      this.biographies = await handleGetAllBiographyByLocationId(id);
    }
  },
  computed: {
    sortedBiographies() {
      return [...this.biographies].sort((a, b) => {
        const fullNameA = `${a.lastName} ${a.firstName} ${a.patronymic}`;
        const fullNameB = `${b.lastName} ${b.firstName} ${b.patronymic}`;
        return fullNameA.localeCompare(fullNameB);
      });
    },
  },
  methods: {
    openBiography(userId) {
      router.push({ name: 'ShowBiography', params: { id: userId } });
    },
  },
};
</script>

<style scoped>
.biography-list-container {
  background-color: #f9f9f9;
  border-radius: 10px;
  padding: 20px;
  max-width: 600px;
  margin: 0 auto;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.biography-list-title {
  font-size: 24px;
  font-weight: bold;
  color: #343434;
  margin-bottom: 20px;
  text-align: center;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
  cursor: pointer;
  font-family: "Roboto", sans-serif;
  color: #343434;
  border-bottom: 1px solid #E2E0E0;
}

li:hover {
  background-color: #E2E0E0;
}

.attributes {
  font-size: 14px;
  color: #777;
}
</style>