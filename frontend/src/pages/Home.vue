<script setup>
import Card from "@/components/Card.vue";
import axios from "axios";
import {ref} from "vue";
const items = ref([])
axios.get("/api/items").then(({data}) => {
  items.value = data;
});
</script>

<template>
  <div class="home">
    <div class="album py-5 bg-light">
      <div class="container">
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
          <div class="col" v-for="(item, index) in items" key="index">
            <Card :item="item" />
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<style scoped>

</style>