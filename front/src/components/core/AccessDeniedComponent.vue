<template>
  <div class="access-denied-container">
    <h2 class="access-denied-title">Доступ запрещен</h2>
    <p class="access-denied-text">
      У вас нет прав для доступа к этому ресурсу.
    </p>
  </div>
</template>

<script>
export default {
  name: "AccessDeniedComponent"
}
</script>

<style scoped>
.access-denied-container {
  background-color: #F8D7DA;
  border-radius: 15px;
  padding: 20px;
  max-width: 600px;
  margin: 0 auto;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  box-sizing: border-box;
  text-align: center;
  color: #721C24;
  border: 1px solid #F5C6CB;
}

.access-denied-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
}

.access-denied-text {
  font-size: 16px;
  line-height: 1.5;
  margin: 0;
  word-wrap: break-word;
}
</style>