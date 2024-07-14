import {useEffect, useState} from "react";
import {getAllCourses, getAllStudents} from "../APIService";
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

const AddExistingStudentModalContent = ({onItemSelected}) => {
    const [students, setStudents] = useState(null)
    const [newStudent, setNewStudent] = useState({
        "studentId" : null
    })

    useEffect(() => {
        fetchStudents()
    }, []);

    const fetchStudents = () => {
        getAllStudents().then(response => setStudents(response.data))
    }

    const getSelectedStudentText = () => {
        const selectedStudent = students ? students.find(student => student.studentId === newStudent.studentId) : null;
        return selectedStudent ? selectedStudent.studentName : "Select Student";
    };

    const handleSelectChange = (selectedItem) => {
        setNewStudent(selectedItem)
        onItemSelected(selectedItem)
    };

    return (
        <Row className="my-2 align-items-center">
            <Col md="4">
                <Label for="courseSelect">Students</Label>
            </Col>
            <Col md="8">
                <ButtonDropdown toggle={function noRefCheck() { }}>
                    <UncontrolledDropdown>
                        <DropdownToggle caret size="sm">
                            {getSelectedStudentText()}
                        </DropdownToggle>
                        <DropdownMenu>
                            <DropdownItem header>Courses</DropdownItem>
                            {students !== null && students.map(student => (
                                <DropdownItem
                                    key={student.studentId}
                                    onClick={() => handleSelectChange(student)}
                                >
                                    {student.studentName}
                                </DropdownItem>
                            ))}
                        </DropdownMenu>
                    </UncontrolledDropdown>
                </ButtonDropdown>
            </Col>
        </Row>
    )
}
export default  AddExistingStudentModalContent