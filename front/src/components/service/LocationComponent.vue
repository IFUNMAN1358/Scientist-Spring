<template>
  <div class="location-container">
    <h2 class="location-title">Россия</h2>
    <ul v-if="russianRegions.length > 0">
      <li v-for="region in russianRegions" :key="region.location.locationId" @click="getAllByLocation(region.location.locationId)">
        <span>{{ region.location.region }}</span>
        <span class="count">{{ region.count }}</span>
      </li>
    </ul>

    <h2 class="location-title">Другие страны</h2>
    <ul v-if="otherCountries.length > 0">
      <li v-for="country in otherCountries" :key="country.location.locationId" @click="getAllByLocation(country.location.locationId)">
        <span>{{ country.location.country }}</span>
        <span class="count">{{ country.count }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
import {handleGetAllLocationCount} from "@/js/service/biographyService";
import router from "@/js/config/router";

export default {
  name: "LocationComponent",
  data() {
    return {
      locations: [],
      russianRegions: [],
      otherCountries: []
    };
  },
  async mounted() {
    try {
      this.locations = await handleGetAllLocationCount();
      this.filterLocations();
    } catch (error) {
      console.error("Failed to load locations", error);
    }
  },
  methods: {
    filterLocations() {
      this.russianRegions = this.locations.filter(
        (location) => location.location.country === "Россия"
      );
      this.otherCountries = this.locations.filter(
        (location) => location.location.country !== "Россия"
      );
    },
    getAllByLocation(locationId) {
      router.push({ name: "BiographyList", params: { type: "location", id: locationId } });
    }
  }
};
</script>

<style scoped>
.location-container {
  background-color: #f9f9f9;
  border-radius: 10px;
  padding: 20px;
  max-width: 600px;
  margin: 0 auto;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.location-title {
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

.count {
  font-weight: bold;
}
</style>
