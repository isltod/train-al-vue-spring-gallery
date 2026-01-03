<script setup>
  import {computed, onMounted, reactive, ref} from "vue";
  import axios from "axios";
  import router from "@/router/index.js";

  const order = reactive({
    items: [],
    customer: {
      name: "",
      address: "",
      payment: "",
      cardNumber: "",
      items: "",
    }
  })

  const getItems = () => {
    axios.get("/api/cart/items").then(({data}) => {
      order.items = data;
    })
  }

  onMounted(() => {
    getItems()
  })

  const realPrice = (item) => {
    return (1 - item.discountPer / 100) * item.price
  }

  const totalPrice = computed(() => {
    let sum = 0
    for (let item of order.items) {
      sum += realPrice(item)
    }
    return sum
  })

  const submit = () => {
    // 이해가 안가는데, JSON.parse 없어도 작동하는데, 참조 객체의 참조를 끊어? 새 복사본을 만든다는 얘기 같은데...왜 하지?
    const newOrder = JSON.parse(JSON.stringify(order.customer))
    // 이건 굳이 주문 항목들을 문자로 넣는 바람에 필요한 부분이고...
    newOrder.items = JSON.stringify(order.items)
    axios.post("/api/orders", newOrder).then(() => {
      alert("주문을 완료했습니다.")
      router.push("/orderlist")
    })
  }
</script>

<template>
  <div data-v-7a7a37b1="" class="order">
    <div class="container">
      <main>
        <div class="py-5 text-center"><h2>주문하기</h2>
          <p class="lead">Below is an example form built entirely with
            Bootstrap’s form controls. Each required form group has a validation state that can be triggered by
            attempting to submit the form without completing it.</p></div>
        <div class="row g-5">
          <div class="col-md-5 col-lg-4 order-md-last"><h4
              class="d-flex justify-content-between align-items-center mb-3"><span class="text-primary">구입 목록</span><span
              class="badge bg-primary rounded-pill">{{order.items.length}}</span></h4>
            <ul class="list-group mb-3">
              <li class="list-group-item d-flex justify-content-between lh-sm"
                  v-for="(item, index) in order.items" :key="index">
                <div>
                  <h6 class="my-0">{{item.name}}</h6>
                  <small class="text-muted">{{item.price.toLocaleString()}} 원</small>
                </div>
                <span class="text-muted">{{realPrice(item).toLocaleString()}} 원</span>
              </li>
              <li class="list-group-item d-flex justify-content-between bg-light">
                <span>합계 금액: </span>
                <strong>{{totalPrice.toLocaleString()}} 원</strong>
              </li>
            </ul>
          </div>
          <div class="col-md-7 col-lg-8">
            <h4 class="mb-3">주문자 정보</h4>
            <div class="needs-validation" novalidate="">
              <div class="row g-3">
                <div class="col-12">
                  <label for="username" class="form-label">이름</label>
                  <div class="input-group has-validation">
                    <span class="input-group-text">@</span>
                    <input type="text" class="form-control"
                           id="username" placeholder="홍길동" v-model="order.customer.name">
                    <div class="invalid-feedback"> Your username is required.</div>
                  </div>
                </div>
                <div class="col-12">
                  <label for="address" class="form-label">주소</label>
                  <input type="text" class="form-control"
                         id="address" placeholder="수원시" v-model="order.customer.address">
                  <div class="invalid-feedback"> Please enter your shipping address.</div>
                </div>
              </div>
              <hr class="my-4">
              <h4 class="mb-3">결제 수단</h4>
              <div class="my-3">
                <div class="form-check">
                  <input id="card" name="paymentMethod" type="radio" class="form-check-input"
                         value="card" v-model="order.customer.payment">
                  <label class="form-check-label" for="card">신용카드</label>
                </div>
                <div class="form-check">
                  <input id="bank" name="paymentMethod" type="radio" class="form-check-input"
                         value="bank" v-model="order.customer.payment">
                  <label class="form-check-label" for="bank">무통장 입금</label>
                </div>
              </div>
              <div class="row gy-3">
                <div class="col-md-6">
                  <label for="cc-number" class="form-label">카드 번호</label>
                  <input type="text" class="form-control" id="cc-number" v-model="order.customer.cardNumber">
                  <div class="invalid-feedback">Credit card number is required</div>
                </div>
              </div>
              <hr class="my-4">
              <button class="w-100 btn btn-primary btn-lg" @click="submit()">결제하기</button>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<style scoped>

</style>