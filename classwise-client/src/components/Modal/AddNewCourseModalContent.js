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

const AddNewCourseModalContent = ({onItemChange}) => {
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
                semesterId: selectedItem.semesterId === undefined ? null : selectedItem.semesterId
            }));
            onItemChange({
                ...course,
                semesterId: selectedItem.semesterId === undefined ? null : selectedItem.semesterId
            });
        } else if (type === "teacher") {
            setCourse(prevItem => ({
                ...prevItem,
                teacherId: selectedItem.teacherId === undefined ? null : selectedItem.teacherId
            }));
            onItemChange({
                ...course,
                teacherId: selectedItem.teacherId === undefined ? null : selectedItem.teacherId
            });
        }
    };

    const getSelectedSemesterText = () => {
        const selectedSemester = semesters ? semesters?.find(semester => semester.semesterId === course.semesterId) : null;
        return selectedSemester ? `${selectedSemester.schoolYear}-${selectedSemester.semesterNumber}` : "None";
    };

    const getSelectedTeacherText = () => {
        const selectedTeacher = teachers ? teachers.find(teacher => teacher.teacherId === course.teacherId) : null;
        return selectedTeacher ? selectedTeacher.teacherName : "None";
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
                                <DropdownItem onClick={() => handleSelectChange({}, "semester")}>None</DropdownItem>
                                {semesters && semesters?.map(semester => (
                                    <DropdownItem
                                        key={semester.semesterId}
                                        onClick={() => handleSelectChange(semester, "semester")}
                                    >
                                        {semester.semesterId ? `${semester.schoolYear}-${semester.semesterNumber}` : "None"}
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
                                <DropdownItem onClick={() => handleSelectChange({}, "teacher")}>None</DropdownItem>
                                {teachers && teachers?.map(teacher => (
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

export default AddNewCourseModalContent