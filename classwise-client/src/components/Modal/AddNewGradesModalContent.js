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
    const[students, setStudents] = useState(null)
    const[courses, setCourses] = useState(null)
    const[courseFilter, setCourseFilter] = useState({
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
        const selectedStudent = students?.find(student => student.studentId === grades.studentId);
        return selectedStudent ? `${selectedStudent.studentName}` : "Select Student";
    }

    const getSelectedCourseText = () => {
        const selectedCourse = courses?.find(course => course.courseId === grades.courseId);
        return selectedCourse ? `${selectedCourse.courseName} ${selectedCourse.semester.schoolYear}-${selectedCourse.semester.semesterNumber}` : "Select Course";
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
                            <DropdownItem header>Semesters</DropdownItem>
                            {students?.map(student => (
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
                            {courses?.map(course => (
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