import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

const router = new Router({
  mode: "history",
  routes: [
    {
      path: "/",
      alias: "/student",
      name: "student",
      component: () => import("./components/StudentsList")
    },
    {
      path: "/student/:id",
      name: "student-details",
      component: () => import("./components/Student")
    },
    {
      path: "/add",
      name: "add",
      component: () => import("./components/AddStudent")
    },
    {
      path: "/addCourse",
      name: "addCourse",
      component: () => import("./components/AddCourse")
    }

  ]
});
export default router;