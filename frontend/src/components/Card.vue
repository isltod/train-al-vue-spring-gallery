<script setup>
  import {computed, ref} from "vue";

  const props = defineProps({
    item: Object,
  })

  const realPrice = computed(() => {
    return (1 - props.item.discountPer / 100) * props.item.price
  })
</script>

<template>
  <div class="card shadow-sm">
    <span class="img" :style="{backgroundImage: `url(${item.imgPath})`}"/>
    <div class="card-body">
      <p class="card-text">
        <span>{{ item.name }} &nbsp;</span>
        <span class="discount badge bg-danger">{{ item.discountPer.toLocaleString() }}%</span>
      </p>
      <div class="d-flex justify-content-between align-items-center">
        <div class="btn-group">
          <button class="btn btn-primary">구입하기</button>
        </div>
        <small class="price text-muted">{{ item.price.toLocaleString() }} 원</small>
        <small class="real text-danger">{{ realPrice.toLocaleString() }} 원</small>
      </div>
    </div>
  </div>
</template>

<style scoped>
  .card .img {
    display: flex;
    width: 100%;
    height: 250px;
    background-size: cover;
    background-position: center;
  }

  .card .price {
    text-decoration: line-through;
  }
</style>