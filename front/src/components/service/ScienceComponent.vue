<template>
  <div class="science-container">
    <h2 class="science-title">Список наук</h2>
    <ul>
      <li v-for="science in sciences" :key="science.science.scienceId" @click="getAllByScience(science.science.scienceId)">
        <span>{{ science.science.name }}</span>
        <span class="count">{{ science.count }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
import { handleGetAllScienceCount } from "@/js/service/biographyService";
import router from "@/js/config/router";

export default {
  name: "ScienceComponent",
  data() {
    return {
      sciences: []
    };
  },
  async created() {
    this.sciences = await handleGetAllScienceCount();
  },
  methods: {
    getAllByScience(scienceId) {
      router.push({ name: "BiographyList", params: { type: "science", id: scienceId } });
    }
  }
};
</script>

<style scoped>
.science-container {
  background-color: #F4F4F4;
  border-radius: 15px;
  padding: 20px;
  width: 100%;
  max-width: 600px;
  margin: 0 auto;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  box-sizing: border-box;
}

.science-title {
  font-size: 24px;
  font-weight: bold;
  color: #343434;
  margin-bottom: 20px;
  text-align: center;
}

ul {
  list-style: none;
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

.count {
  font-weight: bold;
}
</style>