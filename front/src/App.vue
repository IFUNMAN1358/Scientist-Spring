<template>
  <div class="main-wrapper">
    <HeaderComponent />
    <div class="content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import HeaderComponent from "@/components/core/HeaderComponent.vue";

export default {
  name: 'App',
  components: {
    HeaderComponent
  }
};
</script>

<style>
body, html {
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
  font-family: 'Roboto', sans-serif;
  background-color: #E2E0E0;
  overflow-x: hidden;
}

.main-wrapper {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  width: 100%;
  box-sizing: border-box;
}

.content {
  flex: 1;
  padding: 30px;
  width: 100%;
  box-sizing: border-box;
}

@media (max-width: 768px) {
  .content {
    padding: 15px;
  }
}
</style>
