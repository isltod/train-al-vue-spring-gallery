<script setup>
  import store from "@/scripts/store.js";
  import router from "@/router/index.js";
  import axios from "axios";

  const logOut = () => {
    axios.post("/api/account/logout").then((response) => {
      store.commit("setUserId", 0);
      router.push({path: "/"});
    })
  }
</script>

<template>
  <header>
    <div class="collapse bg-dark" id="navbarHeader">
      <div class="container">
        <div class="row">
          <div class="col-sm-4 py-4">
            <h4 class="text-white">사이트 맵</h4>
            <ul class="list-unstyled">
              <li><RouterLink to="/" class="text-white">메인 화면</RouterLink></li>
              <li><RouterLink to="/about" class="text-white">About</RouterLink></li>
              <li>
                <RouterLink to="/login" class="text-white" v-if="!$store.state.userId">로그인</RouterLink>
                <RouterLink to="/login" class="text-white" @click="logOut" v-else>로그아웃</RouterLink>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <div class="navbar navbar-dark bg-dark shadow-sm">
      <div class="container">
        <RouterLink to="/" class="navbar-brand d-flex align-items-center">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="none" stroke="currentColor"
               stroke-linecap="round" stroke-linejoin="round" stroke-width="2" aria-hidden="true" class="me-2"
               viewBox="0 0 24 24">
            <path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"/>
            <circle cx="12" cy="13" r="4"/>
          </svg>
          <strong>Gallery</strong>
        </RouterLink>
        <RouterLink to="/cart" class="cart btn" v-if="$store.state.userId">
          <i class="fa fa-cart-plus" aria-hidden="true"></i>
        </RouterLink>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarHeader"
                aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
      </div>
    </div>
  </header>
</template>

<style scoped>
  header .navbar .cart {
    margin-left: auto;
    color: white;
  }
</style>