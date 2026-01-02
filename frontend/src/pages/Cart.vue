<script setup>
  import axios from "axios";
  import {computed, onMounted, ref} from "vue";

  const items = ref([])

  const getItems = () => {
    axios.get("/api/cart/items").then(({data}) => {
      items.value = data;
    })
  }

  onMounted(() => {
    getItems()
  })

  const realPrice = (item) => {
    return (1 - item.discountPer / 100) * item.price
  }

  const removeItem = (itemId) => {
    axios.delete("/api/cart/items/" + itemId).then(({data}) => {
      getItems()
    })
  }

</script>

<template>
  <div class="cart">
    <div class="container">
      <ul>
        <li v-for="(item, index) in items" :key="index">
          <img :src="item.imgPath" />
          <span class="name">{{item.name}}</span>
          <span class="price">{{realPrice(item).toLocaleString()}} 원</span>
          <i class="fa fa-trash" @click="removeItem(item.id)"></i>
        </li>
      </ul>
      <RouterLink to="/order" class="btn btn-primary">구입하기</RouterLink>
    </div>
  </div>
</template>

<style scoped>
  .cart ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
  }

  .cart ul li {
    border: 1px solid #eee;
    margin-top: 25px;
    margin-bottom: 25px;
  }

  .cart ul img {
    width: 150px;
    height: 150px;
  }

  .cart ul .name {
    margin-left: 25px;
  }

  .cart ul .price {
    margin-left: 25px;
  }

  .cart ul i {
    float: right;
    font-size: 30px;
    margin-top: 55px;
    margin-right: 40px;
  }

  .cart .btn {
    width: 300px;
    display: block;
    margin: 0 auto;
    padding: 30px 50px;
    font-size: 20px;
  }
</style>