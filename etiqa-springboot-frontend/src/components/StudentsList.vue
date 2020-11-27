<template>
  <v-row align="center" class="list px-3 mx-auto">
    <v-col cols="12" md="8">
      <v-text-field v-model="title" label="Search by Course ID"></v-text-field>
    </v-col>

    <v-col cols="12" md="4">
      <v-btn small @click="searchStudent">
        Search
      </v-btn>
      <v-btn small @click="refreshList">
        Clear Search
      </v-btn>
    </v-col>

    <v-col cols="12" sm="12">
      <v-card class="mx-auto" tile>
        <v-card-title>Students</v-card-title>

        <v-data-table
            :headers="headers"
            :items="students"
            disable-pagination
            :hide-default-footer="true"
        >
          <template v-slot:[`item.actions`]="{ item }">
            <v-icon small class="mr-2" @click="editStudent(item.id)">mdi-pencil</v-icon>
            <v-icon small @click="deleteStudent(item.id)">mdi-delete</v-icon>
          </template>
        </v-data-table>

        <v-card-actions v-if="students.length > 0">
          <v-btn small color="error" @click="removeAllStudents">
            Remove All
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import StudentDataService from "../Service/StudentDataService";
//import Student from "@/components/Student";

export default {
  name: "students-list",
  data() {
    return {
      students: [],
      title: "",
      headers: [
        { text: "ID", align: "start", sortable: false, value: "id" },
        { text: "Name", value: "name", sortable: false },
        { text: "IC Number", value: "ic_No", sortable: false },
        { text: "Email", value: "email", sortable: false },
        { text: "Course ID", value: "courseID", sortable: false },
        { text: "Course Name", value: "courseName", sortable: false },
        { text: "Actions", value: "actions", sortable: false },
      ],
    };
  },
  methods: {
    retreiveStudents() {
      StudentDataService.getAllStudents()
          .then((response) => {
            console.log(response.data);
            this.students = response.data.map(this.getDisplayStudent)
            console.log(response.data);
          })
          .catch((e) => {
            console.log(e);
          })
    },

    refreshList() {
      this.retreiveStudents();
      this.title = " ";
    },

    removeAllStudents() {
      StudentDataService.deleteAllStudent()
          .then((response) => {
            console.log(response.data);
            this.refreshList();
          })
          .catch((e) => {
            console.log(e);
          });
    },

    searchStudent() {
      console.log("course id: "+this.title);
      StudentDataService.getStudentByCourseID(this.title)
          .then((response) => {
            console.log("response "+response.data);
            this.students = response.data.map(this.getDisplayStudent);
            console.log(response.data);
          })
          .catch((e) => {
            console.log(e);
          });
    },

    editStudent(id) {
      this.$router.push({ name: "student-details", params: { id: id } });
    },

    deleteStudent(id) {
      StudentDataService.deleteStudent(id)
          .then(() => {
            this.refreshList();
          })
          .catch((e) => {
            console.log(e);
          });
    },

    getDisplayStudent(student) {
      return {

        id: student.id,
        name: student.name,
        ic_No: student.ic_No,
        email: student.email,
        courseID: student.courseID,
        courseName: student.courseName,
      };
    },
  },

  mounted() {
    this.retreiveStudents();
  },
};
</script>

<style scoped>

</style>