<template>
  <div v-if="currentStudent" class="edit-form mt-3 mx-auto">
    <p class="headline">Edit Student</p>

    <v-form ref="form" lazy-validation>
      <v-text-field
          v-model="currentStudent.name"
          :rules="[(v) => !!v || 'Name is required']"
          label="Name"
          required
      ></v-text-field>

      <v-text-field
          v-model="currentStudent.ic_No"
          :rules="[(v) => !!v || 'IC Number is required', v => (v && v.length == 12) || 'IC Number must be 12 characters', v => /[0-9]+$/.test(v) || 'Field must only contain numbers',]"
          label="IC Number"
          error-count="3"
          required
      ></v-text-field>

      <v-text-field
          v-model="currentStudent.email"
          :rules="[(v) => !!v || 'Email is required', v => /.+@.+/.test(v) || 'E-mail must be valid']"
          label="Email"
          error-count="2"
          required
      ></v-text-field>

      <v-select
          v-model="selected"
          :items="courses"
          item-text="course_Name"
          item-value="course_Name"
          label="Course"
          dense
          return-object
          single-line
      >{{getSelection()}}</v-select>

      <v-btn :disabled="isButtonDisabled" color="error" small class="mr-2" @click="deleteStudent">
        Delete
      </v-btn>

      <v-btn :disabled="isButtonDisabled" color="success" small @click="updateStudent">
        Update
      </v-btn>
    </v-form>

    <p class="mt-3">{{ message }}</p>
  </div>

  <div v-else>
    <p>Please click on a Tutorial...</p>
  </div>
</template>

<script>
import StudentDataService from "@/Service/StudentDataService";

export default {
  name: "Student",
  data() {
    return {
      currentStudent: null,
      message: "",
      selected: [],
      courses: [],
    };
  },
  computed: {
    isButtonDisabled() {
      return !(this.currentStudent.name && this.currentStudent.ic_No && this.currentStudent.email);
      // disabled submit name, ic and email empty
    },
  },

  methods: {
    getStudent(id) {
      StudentDataService.getStudent(id)
          .then((response) => {
            this.currentStudent = response.data;
            console.log(response.data);
          })
          .catch((e) => {
            console.log(e);
          });
    },

    retreiveCourses() {
      StudentDataService.getAllCourses()
          .then((response) => {
            this.courses = response.data.map(this.getDisplayCourse);
            console.log(response.data);
          })
          .catch((e) => {
            console.log(e);
          })
    },

    getSelection() {
      console.log("Selected");
    },

    updateStudent() {
      var data = {
        id: this.currentStudent.id,
        name: this.currentStudent.name,
        ic_No: this.currentStudent.ic_No,
        email: this.currentStudent.email,
        courseName: this.selected.course_Name,
        courseID: this.selected.course_ID,
      };

      StudentDataService.updateStudent(this.currentStudent.id, data)
          .then((response) => {
            console.log(response.data);
            this.message = "The student was updated successfully!";
          })
          .catch((e) => {
            console.log(e);
          });
    },

    deleteStudent() {
      StudentDataService.deleteStudent(this.currentStudent.id)
          .then((response) => {
            console.log(response.data);
            this.$router.push({ name: "student" });
          })
          .catch((e) => {
            console.log(e);
          });
    },
    getDisplayCourse(course) {
      return {
        id: course.id,
        course_ID: course.course_ID,
        course_Name: course.course_Name,
      };
    },
  },
  mounted() {
    this.message = "";
    this.getStudent(this.$route.params.id);
    this.retreiveCourses();
  },
};
</script>

<style scoped>
.edit-form {
  max-width: 300px;
}
</style>