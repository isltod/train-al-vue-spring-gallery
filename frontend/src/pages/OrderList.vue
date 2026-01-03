<script setup>
  import {onMounted, ref} from "vue";
  import axios from "axios";

  const orders = ref([])

  const getOrders = () => {
    axios.get("/api/orders").then(({data}) => {
      orders.value = data;
      for (let order of orders.value) {
        if (order.items) {
          // 이게 통 문자열로 와서, 밑에서 그대로 반복 돌리면 글자 하나마다 반복된다...
          // 그래서 여기서 [{}, {},] 배열로 바꿔준다...
          order.items = JSON.parse(order.items);
        }
      }
    })
  }

  onMounted(() => {getOrders()})
</script>

<template>
  <div class="orders">
    <div class="container">
      <!-- <p>{{orders}}</p> -->
      <table class="table table-bordered">
        <thead>
          <tr>
            <th>번호</th>
            <th>주문자명</th>
            <th>주소</th>
            <th>결제 수단</th>
            <th>구입 항목</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(order, index) in orders" :key="index">
            <td>{{ orders.length - index }}</td>
            <td>{{ order.name }}</td>
            <td>{{ order.address }}</td>
            <td>{{ order.payment }}</td>
            <td>
              <div v-for="(item, idx) in order.items" :key="idx">{{ item.name }}</div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>
  .table {
    margin-top: 30px;
  }
</style>