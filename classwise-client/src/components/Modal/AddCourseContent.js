import {
    ButtonDropdown, Col,
    DropdownItem,
    DropdownMenu,
    DropdownToggle,
    Input,
    InputGroup, Row,
    UncontrolledDropdown,
    Label
} from "reactstrap";
import {useEffect, useState} from "react";
import {getAllSemesters, getAllTeachers} from "../APIService";

const AddCourseContent = ({onItemChange}) => {
    const [course, setCourse] = useState({
        "courseName" : "",
        "active" : false,
        "semesterId": null,
        "teacherId": null,
        "studentIds" : []
    })
    const [semesters, setSemesters] = useState([])
    const [teachers, setTeachers] = useState([])

    useEffect(() => {
        getAllSemesters().then(response => setSemesters(response.data))
        getAllTeachers().then(response => setTeachers(response.data))
    }, []);

    useEffect(() => {
        console.log(course)
    }, [course]);

    const handleTextChange = (event, fieldName) => {
        const value = event.target.value;
        setCourse((prevItem) => ({
            ...prevItem,
            [fieldName]: value
        }))
        onItemChange({...course, [fieldName]: value});
    }

    const handleSelectChange = (selectedItem, type) => {
        if (type === "semester") {
            setCourse(prevItem => ({
                ...prevItem,
                semesterId: selectedItem.semesterId,
            }));
            onItemChange({
                ...course,
                semesterId: selectedItem.semesterId,
            });
        } else if (type === "teacher") {
            setCourse(prevItem => ({
                ...prevItem,
                teacherId: selectedItem.teacherId,
            }));
            onItemChange({
                ...course,
                teacherId: selectedItem.teacherId,
            });
        }
    };

    const getSelectedSemesterText = () => {
        const selectedSemester = semesters.find(semester => semester.semesterId === course.semesterId);
        return selectedSemester ? `${selectedSemester.schoolYear}-${selectedSemester.semesterNumber}` : "Select Semester";
    };

    const getSelectedTeacherText = () => {
        const selectedTeacher = teachers.find(teacher => teacher.teacherId === course.teacherId);
        return selectedTeacher ? selectedTeacher.teacherName : "Select Teacher";
    };

    return(
        <>
            <InputGroup>
                <Input placeholder="Course Name" onChange={(event) => handleTextChange(event, "courseName")}/>
            </InputGroup>
            <Row className="my-2 align-items-center">
                <Col md="4">
                    <Label for="semesterSelect">Semester</Label>
                </Col>
                <Col md="8">
                    <ButtonDropdown toggle={function noRefCheck() { }}>
                        <UncontrolledDropdown>
                            <DropdownToggle caret size="sm">
                                {getSelectedSemesterText()}
                            </DropdownToggle>
                            <DropdownMenu>
                                <DropdownItem header>Semesters</DropdownItem>
                                {semesters.map(semester => (
                                    <DropdownItem
                                        key={semester.semesterId}
                                        onClick={() => handleSelectChange(semester, "semester")}
                                    >
                                        {semester.schoolYear}-{semester.semesterNumber}
                                    </DropdownItem>
                                ))}
                            </DropdownMenu>
                        </UncontrolledDropdown>
                    </ButtonDropdown>
                </Col>
            </Row>
            <Row className="my-2 align-items-center">
                <Col md="4">
                    <Label for="teacherSelect">Teacher</Label>
                </Col>
                <Col md="8">
                    <ButtonDropdown toggle={function noRefCheck() { }}>
                        <UncontrolledDropdown>
                            <DropdownToggle caret size="sm">
                                {getSelectedTeacherText()}
                            </DropdownToggle>
                            <DropdownMenu>
                                <DropdownItem header>Teachers</DropdownItem>
                                {teachers.map(teacher => (
                                    <DropdownItem
                                        key={teacher.teacherId}
                                        onClick={() => handleSelectChange(teacher, "teacher")}
                                    >
                                        {teacher.teacherName}
                                    </DropdownItem>
                                ))}
                            </DropdownMenu>
                        </UncontrolledDropdown>
                    </ButtonDropdown>
                </Col>
            </Row>

        </>
    )
}

export default AddCourseContent