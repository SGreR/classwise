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

var routes = [
  {
    path: "/",
    name: "Home",
    icon: "nc-icon nc-bank",
    component: <HomePage />,
    layout: "",
  },
  {
    path: "/students",
    name: "Students",
    icon: "nc-icon nc-bank",
    component: <StudentListPage />,
    layout: "",
  },
  {
    path: "/courses",
    name: "Courses",
    icon: "nc-icon nc-bank",
    component: <CourseListPage />,
    layout: "",
  },
  {
    path: "/grades",
    name: "Grades",
    icon: "nc-icon nc-bank",
    component: <GradesListPage />,
    layout: "",
  },
  {
    path: "/teachers",
    name: "Teachers",
    icon: "nc-icon nc-bank",
    component: <TeacherListPage />,
    layout: "",
  },
  {
    path: "/semesters",
    name: "Semesters",
    icon: "nc-icon nc-bank",
    component: <SemesterListPage />,
    layout: "",
  }
];
export default routes;
