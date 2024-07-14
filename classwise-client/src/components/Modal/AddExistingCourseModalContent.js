import {getAllCourses} from "../APIService";
import {useEffect, useState} from "react";
import {
    ButtonDropdown,
    Col,
    DropdownItem,
    DropdownMenu,
    DropdownToggle,
    Label,
    Row,
    UncontrolledDropdown
} from "reactstrap";

const AddExistingCourseModalContent = ({onItemSelected}) => {
    const [courses, setCourses] = useState(null)
    const [newCourse, setNewCourse] = useState({
        "courseId" : null
    })

    useEffect(() => {
        fetchCourses()
    }, []);

    const fetchCourses = () => {
        getAllCourses().then(response => setCourses(response.data))
    }

    const getSelectedCourseText = () => {
        const selectedCourse = courses ? courses.find(course => course.courseId === newCourse.courseId) : null;
        return selectedCourse ? `${selectedCourse.courseName} ${selectedCourse.semester.schoolYear}-${selectedCourse.semester.semesterNumber}` : "Select Course";
    };

    const handleSelectChange = (selectedItem) => {
        setNewCourse(selectedItem)
        onItemSelected(selectedItem)
    };

    return (
        <Row className="my-2 align-items-center">
            <Col md="4">
                <Label for="courseSelect">Courses</Label>
            </Col>
            <Col md="8">
                <ButtonDropdown toggle={function noRefCheck() { }}>
                    <UncontrolledDropdown>
                        <DropdownToggle caret size="sm">
                            {getSelectedCourseText()}
                        </DropdownToggle>
                        <DropdownMenu>
                            <DropdownItem header>Courses</DropdownItem>
                            {courses !== null && courses.map(course => (
                                <DropdownItem
                                    key={course.courseId}
                                    onClick={() => handleSelectChange(course)}
                                >
                                    {course.courseName} {course.semester.schoolYear}-{course.semester.semesterNumber}
                                </DropdownItem>
                            ))}
                        </DropdownMenu>
                    </UncontrolledDropdown>
                </ButtonDropdown>
            </Col>
        </Row>
    )
}
export default  AddExistingCourseModalContent