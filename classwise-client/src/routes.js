/*!

=========================================================
* Paper Dashboard React - v1.3.2
=========================================================

* Product Page: https://www.creative-tim.com/product/paper-dashboard-react
* Copyright 2023 Creative Tim (https://www.creative-tim.com)

* Licensed under MIT (https://github.com/creativetimofficial/paper-dashboard-react/blob/main/LICENSE.md)

* Coded by Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/
import HomePage from "pages/HomePage";
import StudentListPage from "pages/Student/StudentListPage";
import CourseListPage from "pages/Course/CourseListPage";
import GradesListPage from "pages/Grades/GradesListPage";
import TeacherListPage from "./pages/Teacher/TeacherListPage";
import SemesterListPage from "./pages/Semester/SemesterListPage";
import {FaBook, FaCalendarAlt, FaChalkboardTeacher, FaChartBar, FaGraduationCap, FaHome} from "react-icons/fa";

var routes = [
  {
    path: "/",
    name: "Home",
    icon: <FaHome size={32} />,
    component: <HomePage />,
    layout: "",
  },
  {
    path: "/students",
    name: "Students",
    icon: <FaGraduationCap size={32}/>,
    component: <StudentListPage />,
    layout: "",
  },
  {
    path: "/courses",
    name: "Courses",
    icon: <FaBook size={28}/>,
    component: <CourseListPage />,
    layout: "",
  },
  {
    path: "/grades",
    name: "Grades",
    icon: <FaChartBar size={32}/>,
    component: <GradesListPage />,
    layout: "",
  },
  {
    path: "/teachers",
    name: "Teachers",
    icon: <FaChalkboardTeacher size={34}/>,
    component: <TeacherListPage />,
    layout: "",
  },
  {
    path: "/semesters",
    name: "Semesters",
    icon: <FaCalendarAlt size={28} />,
    component: <SemesterListPage />,
    layout: "",
  }
];
export default routes;
