import { createRouter, createWebHistory } from 'vue-router'
import Home from "@/pages/Home.vue";
import Login from "@/pages/Login.vue";
import About from "@/pages/About.vue";

const routes = [
    {path: '/', component: Home},
    {path: '/login', component: Login},
    {path: '/about', component: About},
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: routes,
})

export default router
