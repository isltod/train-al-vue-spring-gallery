import { createRouter, createWebHistory } from 'vue-router'
import Home from "@/pages/Home.vue";
import Login from "@/pages/Login.vue";
import About from "@/pages/About.vue";
import Cart from "@/pages/Cart.vue";
import Order from "@/pages/Order.vue";
import OrderList from "@/pages/OrderList.vue";

const routes = [
    {path: '/', component: Home},
    {path: '/login', component: Login},
    {path: '/about', component: About},
    {path: '/cart', component: Cart},
    {path: '/order', component: Order},
    {path: '/orderlist', component: OrderList},
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: routes,
})

export default router
