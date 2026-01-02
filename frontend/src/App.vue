<script setup>
  import Header from "@/components/Header.vue";
  import Footer from "@/components/Footer.vue";
  import store from "@/scripts/store.js";
  import axios from "axios";
  import {useRoute} from "vue-router";
  import {watch} from "vue";

  const checkAccount = () => {
    axios.get("/api/account/check").then((response) => {
      store.commit("setUserId", response.data || 0);
    })
  }

  const route = useRoute();

  watch(route, (newRoute, oldRoute) => {
    checkAccount();
  });
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
