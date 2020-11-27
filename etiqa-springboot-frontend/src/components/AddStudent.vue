<template>
  <div class="submit-form mt-3 mx-auto">
    <p class="headline">Add Student</p>

    <div v-if="!submitted">
      <v-form ref="form" lazy-validation>
        <v-text-field
            v-model="student.name"
            :rules="[(v) => !!v || 'Name is required']"
            label="Name"
            required
        ></v-text-field>

        <v-text-field
            v-model="student.ic_No"
            v-bind:rules="[(v) => !!v || 'IC Number is required', (v) => (v && v.length == 12) || 'IC Number must be 12 characters', (v) => /[0-9]+$/.test(v) || 'Field must only contain numbers']"
            label="IC Number"
            error-count="3"
            required
        ></v-text-field>

        <v-text-field
            v-model="student.email"
            v-bind:rules="[(v) => !!v || 'Email is required', v => /.+@.+/.test(v) || 'E-mail must be valid']"
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

      </v-form>

      <v-btn :disabled="isSubmitButtonDisabled" color="primary" class="mt-3" @click="saveStudent">Submit</v-btn>
    </div>

    <div v-else>
      <v-card class="mx-auto">
        <v-card-title>
          Submitted successfully!
        </v-card-title>

        <v-card-subtitle>
          Click the button to add new Student.
        </v-card-subtitle>

        <v-card-actions>
          <v-btn color="success" @click="newStudent">Add</v-btn>
        </v-card-actions>
      </v-card>
    </div>
  </div>
</template>

<script>
import StudentDataService from "@/Service/StudentDataService";

export default {
  name: "AddStudent",
  data() {
    return {

      selected: [],
      courses: [],

      student: {
        id: "10",
        name: "",
        ic_No: "",
        email: "",
        courseID: "",
        courseName: "",
      },
      submitted: false,
    };

  },

  computed: {
    isSubmitButtonDisabled() {
      return !(this.student.name && this.student.ic_No && this.student.email);
      // disabled submit button name, ic and email empty
    },
  },

  methods: {
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

    saveStudent() {
      var data = {
        id: this.student.id,
        name: this.student.name,
        ic_No: this.student.ic_No,
        email: this.student.email,
        courseName: this.selected.course_Name,
        courseID: this.selected.course_ID,
      };

      StudentDataService.createStudent(data)
          .then((response) => {
            this.student.id = response.data.id;
            console.log(response.data);
            this.submitted = true;
          })
          .catch((e) => {
            console.log(e);
          });
    },

    newStudent() {
      this.submitted = false;
      this.student = {};
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
    this.retreiveCourses();
  },
};
</script>

<style scoped>
.submit-form {
  max-width: 300px;
}
</style>