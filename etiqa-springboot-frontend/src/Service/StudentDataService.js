import axios from 'axios'

const STUDENT_API_URL = 'http://localhost:8080'

class StudentDataService {

    getAllStudents() {
        return axios.get(`${STUDENT_API_URL}/student`);
    }

    getStudent(id) {
        return axios.get(`${STUDENT_API_URL}/student/${id}`);
    }

    createStudent(student) {
        console.log('executed service')
        return axios.post(`${STUDENT_API_URL}/student/`, student);
    }

    updateStudent(id, student) {
        console.log('executed service')
        return axios.put(`${STUDENT_API_URL}/student/${id}`, student);
    }

    deleteStudent(id) {
        console.log('executed service')
        return axios.delete(`${STUDENT_API_URL}/student/${id}`);
    }

    deleteAllStudent() {
        console.log('executed service')
        return axios.delete(`${STUDENT_API_URL}/student`);
    }

    getStudentByCourseID(courseID) {
        console.log(courseID);
        return axios.get(`${STUDENT_API_URL}/student?courseID=${courseID}`);
    }

    getAllCourses() {
        return axios.get(`${STUDENT_API_URL}/course`);
    }

    createCourse(course) {
        console.log('executed service')
        console.log(course)
        return axios.post(`${STUDENT_API_URL}/addCourse/`, course);
    }
}

export default new StudentDataService();