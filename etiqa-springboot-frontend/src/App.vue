<template>
  <v-app>
    <v-app-bar app dark>
      <div class="d-flex align-center mr-2">
        Etiqa
      </div>

      <v-btn to="/student" text>
        Student
      </v-btn>

      <v-btn to="/add" text>
        Add Student
      </v-btn>

      <v-btn to="/addCourse" text>
        Add Course
      </v-btn>
    </v-app-bar>

    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "app",
};
</script>