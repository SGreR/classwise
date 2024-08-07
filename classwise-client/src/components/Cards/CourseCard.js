import {useEffect, useState} from "react";
import {Col, UncontrolledDropdown, DropdownItem, DropdownMenu, Row, DropdownToggle} from "reactstrap";
import {getAllSemesters, getAllTeachers} from "../APIService";
import CircularProgress from "@mui/material/CircularProgress";

const CourseCard = ({item:initialItem, editing, onItemChange}) => {
    const [item, setItem] = useState(null)
    const [semesters, setSemesters] = useState([])
    const [teachers, setTeachers] = useState([])

    useEffect(() => {
        setItem(initialItem)
    }, [initialItem]);

    useEffect(() => {
        fetchSemesters();
        fetchTeachers();
    }, [editing]);

    const handleTextChange = (event, fieldName) => {
        const value = event.target.value;
        setItem((prevItem) => ({
            ...prevItem,
            [fieldName]: value
        }))
        onItemChange({...item, [fieldName]: value});
    }

    const handleSelectChange = (selectedItem, type) => {
        if (type === "semester") {
            setItem(prevItem => ({
                ...prevItem,
                semesterId: selectedItem.semesterId,
                semester: selectedItem
            }));
            onItemChange({
                ...item,
                semesterId: selectedItem.semesterId,
                semester: selectedItem
            });
        } else if (type === "teacher") {
            setItem(prevItem => ({
                ...prevItem,
                teacherId: selectedItem.teacherId,
                teacher: selectedItem
            }));
            onItemChange({
                ...item,
                teacherId: selectedItem.teacherId,
                teacher: selectedItem
            });
        }
    };

    const fetchSemesters = () => {
        getAllSemesters()
            .then(response => setSemesters(response.data))
    }

    const fetchTeachers = () => {
        getAllTeachers()
            .then(response => setTeachers(response.data))
    }

    return (
        item != null ? (
            <>
                <div className="button-container">
                    <Row>
                        <Col>
                            {editing ?
                                (
                                    <input type={"text"} value={item.courseName} onChange={(event) => handleTextChange(event, "courseName")}  />
                                ) : (
                                    <h5 className="title">{item.courseName}</h5>
                            )}
                        </Col>
                    </Row>
                    <hr/>
                    <Row>
                        <Col className="ml-auto" lg="3" md="6" xs="6">
                            <h6>
                                <small>Id</small><br/>
                                {item.courseId}

                            </h6>
                        </Col>
                        <Col className="ml-auto mr-auto" lg="4" md="6" xs="6">
                            <h6>
                                <small>Semester</small><br/>
                                {editing ? (
                                    <>
                                        <UncontrolledDropdown>
                                            <DropdownToggle caret size="sm">
                                                Select Semester
                                            </DropdownToggle>
                                            <DropdownMenu>
                                                <DropdownItem header>Semesters</DropdownItem>
                                                <DropdownItem onClick={() => handleSelectChange({}, "semester")}>None</DropdownItem>
                                                {semesters && semesters?.map(semester => (
                                                    <DropdownItem
                                                        key={semester.semesterId}
                                                        disabled={item.semester && semester.semesterId === item.semester.semesterId}
                                                        onClick={() => {handleSelectChange(semester, "semester")}}
                                                        className={item.semester && semester.semesterId === item.semester.semesterId ? "text-gray" : ""}
                                                    >
                                                        {semester.schoolYear}-{semester.semesterNumber}
                                                    </DropdownItem>
                                                ))}
                                            </DropdownMenu>
                                        </UncontrolledDropdown>
                                        {item.semester?.schoolYear !== undefined && item.semester?.semesterNumber !== undefined ? `${item.semester.schoolYear}-${item.semester.semesterNumber}` : "Unassigned"}
                                    </>
                                ) : (
                                    <>
                                        {item.semester?.schoolYear !== undefined && item.semester?.semesterNumber !== undefined ? `${item.semester.schoolYear}-${item.semester.semesterNumber}` : "Unassigned"}
                                    </>
                                )}
                            </h6>
                        </Col>
                        <Col className="mr-auto" lg="3">
                            <h6>
                                <small>Teacher</small><br/>
                                {editing ?
                                    (
                                    <>
                                        <UncontrolledDropdown>
                                            <DropdownToggle caret size="sm">
                                                Select Teacher
                                            </DropdownToggle>
                                            <DropdownMenu>
                                                <DropdownItem header>Teachers</DropdownItem>
                                                <DropdownItem onClick={() => handleSelectChange({}, "teacher")}>None</DropdownItem>
                                                {teachers && teachers?.map(teacher => (
                                                    <DropdownItem
                                                        key={teacher.teacherId}
                                                        disabled={item.teacher && teacher.teacherId === item.teacher.teacherId}
                                                        onClick={() => {handleSelectChange(teacher, "teacher")}}
                                                        className={item.teacher && teacher.teacherId === item.teacher.teacherId ? "text-gray" : ""}
                                                    >
                                                        {teacher.teacherName}
                                                    </DropdownItem>
                                                ))}
                                            </DropdownMenu>
                                        </UncontrolledDropdown>
                                        {item.teacher?.teacherName !== undefined ? item.teacher.teacherName : "Unassigned"}
                                    </>
                                ) : (
                                    <>
                                        {item.teacher?.teacherName !== undefined ? item.teacher.teacherName : "Unassigned"}
                                    </>
                                )}
                            </h6>
                        </Col>
                    </Row>
                </div>
            </>
        ) : (
            <CircularProgress color="secondary"/>
        )
    )
}

export default CourseCard