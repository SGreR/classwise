import {ButtonDropdown, Col, DropdownItem, DropdownMenu, DropdownToggle, Row, UncontrolledDropdown} from "reactstrap";
import {useEffect, useState} from "react";
import {getAllCourses, getAllStudents} from "../APIService";
import {capitalize} from "../../utils/utils";

const GradesCard = ({item:initialItem, editing, onItemChange}) => {
    const [item, setItem] = useState(null)
    const [courses, setCourses] = useState(null)
    const [students, setStudents] = useState(null)
    const [courseFilter] = useState({
        "filters": {
        },
        "inclusions": {
            "includeTeacher": true,
            "includeSemester": true,
        }
    })

    useEffect(() => {
        setItem(initialItem)
        calculateGrades(initialItem)
    }, [initialItem]);

    useEffect(() => {
        if(editing){
            fetchCourses()
            fetchStudents();
        }
    }, [editing]);

    const fetchCourses = () => {
        getAllCourses(courseFilter.filters, courseFilter.inclusions).then(response => setCourses(response.data))
    }

    const fetchStudents = () => {
        getAllStudents().then(response => setStudents(response.data))
    }

    const getSelectedCourseText = () => {
        const selectedCourse = courses ? courses.find(course => course.courseId === item.courseId) : null;
        return selectedCourse ? `${selectedCourse.courseName} ${selectedCourse.semester.schoolYear}-${selectedCourse.semester.semesterNumber}` : "None";
    };

    const getSelectedStudentText = () => {
        const selectedStudent = students ? students.find(student => student.studentId === item.studentId) : null;
        return selectedStudent ? `${selectedStudent.studentName}` : "None";
    };

    const handleSelectChange = (selectedItem, field) => {
        if(field === "courseId"){
            setItem({...item,[field]: selectedItem.courseId === undefined ? null : selectedItem.courseId});
            onItemChange({...item, [field]: selectedItem.courseId === undefined ? null : selectedItem.courseId})
        } else if(field === "testNumber"){
            setItem({...item, [field]: selectedItem})
            onItemChange({...item, [field]: selectedItem})
        } else if(field === "studentId"){
            setItem({...item, [field]: selectedItem.studentId === undefined ? null : selectedItem.studentId})
            onItemChange({...item, [field]: selectedItem.studentId === undefined ? null : selectedItem.studentId})
        }
    };

    const calculateGrades = (currentItem) => {
        let sum = 0
        currentItem.abilities.skills.forEach(skill => sum += parseFloat(skill.averageGrade))
        sum += parseFloat(currentItem.abilities.speaking.averageGrade)
        sum += parseFloat(currentItem.abilities.classPerformance.averageGrade)
        const updatedItem = {
            ...currentItem,
            abilities: {...currentItem.abilities, finalGrade: (sum / 6).toFixed(1)}
        }
        setItem(updatedItem)
    }

    return (
        item &&
                <>
                    <div className="button-container">
                        <Row>
                            <Col>
                                {editing ? (
                                    <ButtonDropdown toggle={function noRefCheck() {
                                    }}>
                                        <UncontrolledDropdown>
                                            <DropdownToggle caret size="sm">
                                                {getSelectedStudentText()}
                                            </DropdownToggle>
                                            <DropdownMenu>
                                                <DropdownItem header>Students</DropdownItem>
                                                <DropdownItem onClick={() => handleSelectChange({}, "studentId")}>None</DropdownItem>
                                                {students && students?.map(student => (
                                                    <DropdownItem
                                                        key={student.studentId}
                                                        onClick={() => handleSelectChange(student, "studentId")}
                                                    >
                                                        {student.studentName}
                                                    </DropdownItem>
                                                ))}
                                            </DropdownMenu>
                                        </UncontrolledDropdown>
                                    </ButtonDropdown>
                                ) : (
                                    <h5 className="title">{item?.student?.studentName + "'s grades"}</h5>
                                )}
                            </Col>
                        </Row>
                        <hr/>
                        <Row>
                            <Col>
                                <h5>
                                    <small>Id</small><br/>
                                    {item.gradesId}
                                </h5>
                            </Col>
                            <Col>
                                <h5>
                                    <small>Test Number</small><br/>
                                </h5>
                                    {editing ? (
                                        <ButtonDropdown toggle={function noRefCheck() {
                                        }}>
                                            <UncontrolledDropdown>
                                                <DropdownToggle caret size="sm">
                                                    {item.testNumber} Test
                                                </DropdownToggle>
                                                <DropdownMenu>
                                                    <DropdownItem header>Test Number</DropdownItem>
                                                    <DropdownItem key={1} onClick={() => handleSelectChange("FIRST", "testNumber")}>First Test</DropdownItem>
                                                    <DropdownItem key={2} onClick={() => handleSelectChange("SECOND", "testNumber")}>Second Test</DropdownItem>
                                                </DropdownMenu>
                                            </UncontrolledDropdown>
                                        </ButtonDropdown>
                                    ) : (
                                        <h5>{capitalize(item.testNumber.toLowerCase())}</h5>
                                    )}
                            </Col>
                            <Col>
                                <h5>
                                    <small>Final Grade</small><br/>
                                </h5>
                                    {item ? (
                                        <h5>{item.abilities.finalGrade}%</h5>
                                    ) : (
                                        <h5>0%</h5>
                                    )}
                            </Col>
                        </Row>
                        <Row>
                            <Col className="mr-auto ml-auto">
                                <h5>
                                    <small>Current Course</small><br/>
                                </h5>
                                    {editing ?
                                        (
                                            <ButtonDropdown toggle={function noRefCheck() {
                                            }}>
                                                <UncontrolledDropdown>
                                                    <DropdownToggle caret size="sm">
                                                        {getSelectedCourseText()}
                                                    </DropdownToggle>
                                                    <DropdownMenu>
                                                        <DropdownItem header>Courses</DropdownItem>
                                                        <DropdownItem onClick={() => handleSelectChange({}, "courseId")}>None</DropdownItem>
                                                        {courses && courses?.map(course => (
                                                            <DropdownItem
                                                                key={course.courseId}
                                                                onClick={() => handleSelectChange(course, "courseId")}
                                                            >
                                                                {course.courseName} {course.semester.schoolYear}-{course.semester.semesterNumber}
                                                            </DropdownItem>
                                                        ))}
                                                    </DropdownMenu>
                                                </UncontrolledDropdown>
                                            </ButtonDropdown>
                                        ) : (
                                            <h5>{item.course ? item.course?.courseName : "No Current Course"}</h5>
                                        )}
                            </Col>
                        </Row>
                    </div>
                </>
    )
}

export default GradesCard