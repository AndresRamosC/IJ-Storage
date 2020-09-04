import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import DashBoard from "../views/DashBoard.vue"

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home
  },
  {
    path: "/dashboard",
    name: "DashBoard",
    component: DashBoard
  }
];

const router = new VueRouter({
  routes
});

export default router;
