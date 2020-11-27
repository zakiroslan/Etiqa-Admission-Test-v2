<template>
  <div class="submit-form mt-3 mx-auto">
    <p class="headline">Add Course</p>

    <div v-if="!submitted">
      <v-form ref="form" lazy-validation>
        <v-text-field
            v-model="course.course_ID"
            :rules="[(v) => !!v || 'Course ID is required']"
            label="Course ID"
            required
        ></v-text-field>

        <v-text-field
            v-model="course.course_Name"
            :rules="[(v) => !!v || 'Course Name is required']"
            label="Course Name"
            required
        ></v-text-field>
      </v-form>

      <v-btn :disabled="isSubmitButtonDisabled" color="primary" class="mt-3" @click="saveCourse" >Submit</v-btn>
    </div>

    <div v-else>
      <v-card class="mx-auto">
        <v-card-title>
          Submitted successfully!
        </v-card-title>

        <v-card-subtitle>
          Click the button to add new Course.
        </v-card-subtitle>

        <v-card-actions>
          <v-btn color="success" @click="newCourse">Add</v-btn>
        </v-card-actions>
      </v-card>
    </div>
  </div>
</template>

<script>
import StudentDataService from "@/Service/StudentDataService";

export default {
  name: "AddCourse",
  data() {
    return {
      course: {
        id: "10",
        course_ID: "",
        course_Name: "",
      },
      submitted: false,
    };
  },
  computed: {
    isSubmitButtonDisabled() {
      return !(this.course.course_ID && this.course.course_Name);
      // disabled submit when course id & course name empty
    },
  },
  methods: {
    saveCourse() {
      var data = {
        id: this.course.id,
        course_ID: this.course.course_ID,
        course_Name: this.course.course_Name,
      };

      StudentDataService.createCourse(data)
          .then((response) => {
            this.course.id = response.data.id;
            console.log(response.data);
            this.submitted = true;
          })
          .catch((e) => {
            console.log(e);
          });
    },

    newCourse() {
      this.submitted = false;
      this.course = {};
    },
  },
};
</script>

<style scoped>
.submit-form {
  max-width: 300px;
}
</style>