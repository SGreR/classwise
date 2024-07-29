import {useEffect, useState} from "react";
import {getAllCourses, getAllStudents} from "../APIService";
import {
    ButtonDropdown,
    Col,
    DropdownItem,
    DropdownMenu,
    DropdownToggle,
    InputGroup,
    UncontrolledDropdown
} from "reactstrap";
import {capitalize} from "../../utils/utils";

const AddNewGradesModalContent = ({onItemChange}) =>{
    const[students, setStudents] = useState([])
    const[courses, setCourses] = useState([])
    const[courseFilter] = useState({
        "filters": {
            "byName": null,
            "byYear": null,
            "bySemester": null,
            "byTeacher": null,
        },
        "inclusions": {
            "includeStudents": false,
            "includeTeacher": false,
            "includeSemester": true,
            "includeGrades": false
        }
    })
    const[grades, setGrades] = useState({
        "studentId": null,
        "courseId": null,
        "testNumber": null,
        "abilities": {
            "skills":[],
            "speaking":null,
            "classPerformance":null
        }
    })

    useEffect(() => {
        getAllStudents().then(response => setStudents(response.data))
        getAllCourses(courseFilter.filters, courseFilter.inclusions).then(response => setCourses(response.data))
    }, []);

    const handleSelectChange = (value, field) => {
        setGrades({...grades, [field]: value})
        onItemChange({...grades, [field]: value});
    };

    const getSelectedStudentText = () => {
        const selectedStudent = students ? students?.find(student => student.studentId === grades.studentId) : null;
        return selectedStudent ? `${selectedStudent.studentName}` : "None";
    }

    const getSelectedCourseText = () => {
        const selectedCourse = courses ? courses?.find(course => course.courseId === grades.courseId) : null;
        return selectedCourse ? buildCourseName(selectedCourse) : "None";
    }

    const buildCourseName = (course) => {
        if(!course.semester){
            return course.courseName
        } else {
            return `${course.courseName} ${course.semester.schoolYear}-${course.semester.semesterNumber}`
        }
    }

    return(
        <Col>
            <InputGroup>
                <ButtonDropdown toggle={function noRefCheck() { }}>
                    <UncontrolledDropdown>
                        <DropdownToggle caret size="sm">
                            {getSelectedStudentText()}
                        </DropdownToggle>
                        <DropdownMenu>
                            <DropdownItem header>Students</DropdownItem>
                            <DropdownItem onClick={() => handleSelectChange(null, "studentId")}>None</DropdownItem>
                            {students && students?.map(student => (
                                <DropdownItem
                                    key={student.studentId}
                                    onClick={() => handleSelectChange(student.studentId, "studentId")}
                                >
                                    {student.studentName}
                                </DropdownItem>
                            ))}
                        </DropdownMenu>
                    </UncontrolledDropdown>
                </ButtonDropdown>
            </InputGroup>
            <InputGroup>
                <ButtonDropdown toggle={function noRefCheck() { }}>
                    <UncontrolledDropdown>
                        <DropdownToggle caret size="sm">
                            {getSelectedCourseText()}
                        </DropdownToggle>
                        <DropdownMenu>
                            <DropdownItem header>Semesters</DropdownItem>
                            <DropdownItem onClick={() => handleSelectChange(null, "courseId")}>None</DropdownItem>
                            {courses && courses?.map(course => (
                                <DropdownItem
                                    key={course.courseId}
                                    onClick={() => handleSelectChange(course.courseId, "courseId")}
                                >
                                    {buildCourseName(course)}
                                </DropdownItem>
                            ))}
                        </DropdownMenu>
                    </UncontrolledDropdown>
                </ButtonDropdown>
            </InputGroup>
            <InputGroup>
                <ButtonDropdown toggle={function noRefCheck() { }}>
                    <UncontrolledDropdown>
                        <DropdownToggle caret size="sm">
                            {grades.testNumber !== null ? <>Test Number: {capitalize(grades.testNumber.toLowerCase())} Test</> : "Select Test"}
                        </DropdownToggle>
                        <DropdownMenu>
                            <DropdownItem header>Semester Number</DropdownItem>
                            <DropdownItem key={1} onClick={() => handleSelectChange("FIRST", "testNumber")}>First Test</DropdownItem>
                            <DropdownItem key={2} onClick={() => handleSelectChange("SECOND", "testNumber")}>Second Test</DropdownItem>
                        </DropdownMenu>
                    </UncontrolledDropdown>
                </ButtonDropdown>
            </InputGroup>
        </Col>


    )

}
export default AddNewGradesModalContent