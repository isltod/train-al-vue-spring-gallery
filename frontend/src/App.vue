<script setup>
  import Header from "@/components/Header.vue";
  import Footer from "@/components/Footer.vue";
  import store from "@/scripts/store.js";
  // 어차피sessionStorage 밖에는 믿을 수 없는데 store는 왜 쓰는 거지?
  const userId = sessionStorage.getItem("userId");
  if (userId) {
    store.commit("setUserId", userId);
  }
</script>

<template>
  <Header/>
  <RouterView/>
  <Footer/>
</template>

<style scoped>
  .bd-placeholder-img {
    font-size: 1.125rem;
    text-anchor: middle;
    -webkit-user-select: none;
    -moz-user-select: none;
    user-select: none;
  }

  @media (min-width: 768px) {
    .bd-placeholder-img-lg {
      font-size: 3.5rem;
    }
  }
</style>
