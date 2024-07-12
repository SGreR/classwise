import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/classwise'; // Adjust as needed

export const getAllStudents = () => {
    return axios.get(`${API_BASE_URL}/students`, {
        auth: {
            username: "admin",
            password: "admin"
        },
    })
}

export const getStudentById = (id) => {
    return axios.get(`${API_BASE_URL}/students/${id}`, {
        auth: {
            username: "admin",
            password: "admin"
        },
        headers: {
            'Include-Courses': 'true',
            'Include-Grades': 'true',
        }
    })
}

export const postStudent = (student) => {
    return axios.post(`${API_BASE_URL}/students`, student, {
        auth: {
            username: "admin",
            password: "admin"
        }
    })
}

export const putStudent = (id, student) => {
    return axios.put(`${API_BASE_URL}/students/${id}`, student, {
        auth: {
            username: "admin",
            password: "admin"
        }
    })
}

export const deleteStudentById = (id) => {
    return axios.delete(`${API_BASE_URL}/students/${id}`, {
        auth: {
            username: "admin",
            password: "admin"
        },
    })
}

export const getAllCourses = () => {
    return axios.get(`${API_BASE_URL}/courses`, {
        auth: {
            username: 'admin',
            password: 'admin'
        },
        headers: {
            'Include-Semester': 'true',
            'Include-Teacher': 'true'
        }
    });
};

export const getCourseById = (id) => {
    return axios.get(`${API_BASE_URL}/courses/${id}`, {
        auth: {
            username: 'admin',
            password: 'admin'
        },
        headers: {
            'Include-Students': 'true',
            'Include-Semester': 'true',
            'Include-Teacher': 'true',
            'Include-Grades': 'true'
        }
    });
};

export const postCourse = (course) => {
    return axios.post(`${API_BASE_URL}/courses`, course, {
        auth: {
            username: "admin",
            password: "admin"
        }
    })
}

export const putCourse = (id, course) => {
    return axios.put(`${API_BASE_URL}/courses/${id}`, course, {
        auth: {
            username: 'admin',
            password: 'admin'
        }
    });
};

export const deleteCourseById = (id) => {
    return axios.delete(`${API_BASE_URL}/courses/${id}`, {
        auth: {
            username: 'admin',
            password: 'admin'
        }
    });
};

export const getAllTeachers = () => {
    return axios.get(`${API_BASE_URL}/teachers`, {
        auth: {
            username: 'admin',
            password: 'admin'
        },
    });
};

export const getTeacherById = (id) => {
    return axios.get(`${API_BASE_URL}/teachers/${id}`, {
        auth: {
            username: 'admin',
            password: 'admin'
        },
        headers: {
            'Include-Courses': 'true'
        }
    });
};

export const putTeacher = (id, teacher) => {
    return axios.put(`${API_BASE_URL}/teachers/${id}`, teacher, {
        auth: {
            username: 'admin',
            password: 'admin'
        }
    });
};

export const deleteTeacherById = (id) => {
    return axios.delete(`${API_BASE_URL}/teachers/${id}`, {
        auth: {
            username: 'admin',
            password: 'admin'
        }
    });
};

export const getAllGrades = () => {
    return axios.get(`${API_BASE_URL}/grades`, {
        auth: {
            username: 'admin',
            password: 'admin'
        },
        headers: {
            'Include-Course': 'true',
            'Include-Student': 'true'
        }
    });
};

export const getGradeById = (id) => {
    return axios.get(`${API_BASE_URL}/grades/${id}`, {
        auth: {
            username: 'admin',
            password: 'admin'
        }
    });
};

export const putGrade = (id, grade) => {
    return axios.put(`${API_BASE_URL}/grades/${id}`, grade, {
        auth: {
            username: 'admin',
            password: 'admin'
        }
    });
};

export const deleteGradeById = (id) => {
    return axios.delete(`${API_BASE_URL}/grades/${id}`, {
        auth: {
            username: 'admin',
            password: 'admin'
        }
    });
};

export const getAllSemesters = () => {
    return axios.get(`${API_BASE_URL}/semesters`, {
        auth: {
            username: 'admin',
            password: 'admin'
        }
    });
};

export const getSemesterById = (id) => {
    return axios.get(`${API_BASE_URL}/semesters/${id}`, {
        auth: {
            username: 'admin',
            password: 'admin'
        },
        headers: {
            'Include-Courses': 'true'
        }
    });
};

export const putSemester = (id, semester) => {
    return axios.put(`${API_BASE_URL}/semesters/${id}`, semester, {
        auth: {
            username: 'admin',
            password: 'admin'
        }
    });
};

export const deleteSemesterById = (id) => {
    return axios.delete(`${API_BASE_URL}/semesters/${id}`, {
        auth: {
            username: 'admin',
            password: 'admin'
        }
    });
};