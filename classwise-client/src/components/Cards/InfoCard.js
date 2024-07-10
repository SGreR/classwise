import {Button, Card, CardBody, Col, Row} from "reactstrap";
import {useEffect, useState} from "react";

const InfoCard = ({itemType, item: initialItem, onItemChange, onDelete}) => {
    const[item, setItem] = useState(initialItem)
    const[cardContent, setCardContent] = useState(null)
    const[editing, setEditing] = useState(false)

    useEffect(() => {
        switch (itemType){
            case "students":
                buildStudentCard();
                break;
            case "teachers":
                buildTeacherCard();
                break;
            case "courses":
                buildCourseCard();
                break;
            case "semesters":
                buildSemesterCard();
                break;
            default:
                break;

        }
    }, [itemType, editing, item]);

    const handleTextChange = (event, fieldName) => {
        const value = event.target.value;
        setItem((prevItem) => ({
            ...prevItem,
            [fieldName]: value
        }))
        onItemChange({ ...item, [fieldName]: value });
    }

    const buildStudentCard = () => {
        setCardContent(
            <>
                <Row>
                    <Col className="text-right">
                        <Button color="primary" size={"sm"} onClick={() => setEditing(!editing)}>Edit</Button>
                        <Button color="danger" size={"sm"} onClick={() => onDelete()}>Delete</Button>
                    </Col>
                </Row>
                <div className="button-container">

                    <Row>
                        <Col className="mr-auto ml-auto">
                            {editing ?
                                (
                                    <input type={"text"} value={item.studentName} onChange={(event) => handleTextChange(event, "studentName")}  />
                                    ) : (
                                    <h5 className="title">{item.studentName}</h5>
                                )}
                        </Col>
                    </Row>
                    <hr/>
                    <Row>
                        <Col className="ml-auto" lg="3" md="6" xs="6">
                            <h5>
                                <small>Id</small><br/>
                                {item.studentId}

                            </h5>
                        </Col>
                        <Col className="ml-auto mr-auto" lg="4" md="6" xs="6">
                            <h5>
                                <small>Courses</small><br/>
                                {item.courses.length}

                            </h5>
                        </Col>
                    </Row>
                    <Row>
                        <Col className="mr-auto ml-auto">
                            <h5>
                                <small>Current Course</small><br/>
                                {item.courses.find(course => course.active === true)?.courseName || "No active course found"}
                            </h5>
                        </Col>
                    </Row>
                </div>
            </>
        )
    }

    const buildTeacherCard = () => {
        setCardContent(
            <>
                <h5 className="title">{item.teacherName}</h5>
                <hr/>
                <div className="button-container">
                    <Row>
                        <Col className="ml-auto" lg="3" md="6" xs="6">
                            <h5>
                                <small>Id</small><br/>
                                {item.teacherId}

                            </h5>
                        </Col>
                        <Col className="ml-auto mr-auto" lg="4" md="6" xs="6">
                            <h5>
                                <small>Courses</small><br/>
                                {item.courses.length}
                            </h5>
                        </Col>
                    </Row>
                    <Row>
                        <Col className="mr-auto ml-auto">
                            <h5>
                                <small>Current Course</small><br/>
                                {item.courses.find(course => course.active === true)?.courseName || "No active courses found"}
                            </h5>
                        </Col>
                    </Row>
                </div>
            </>
        )
    }

    const buildCourseCard = () => {
        setCardContent(
            <>
                <h5 className="title">{item.courseName}</h5>
                <hr/>
                <div className="button-container">
                    <Row>
                        <Col className="ml-auto" lg="3" md="6" xs="6">
                            <h5>
                                <small>Id</small><br/>
                                {item.courseId}

                            </h5>
                        </Col>
                        <Col className="ml-auto mr-auto" lg="4" md="6" xs="6">
                            <h5>
                                <small>Semester</small><br/>
                                {item.semester.schoolYear}-{item.semester.semesterNumber}

                            </h5>
                        </Col>
                        <Col className="mr-auto" lg="3">
                            <h5>
                                <small>Teacher</small><br/>
                                {item.teacher.teacherName}
                            </h5>
                        </Col>
                    </Row>
                </div>
            </>
        )
    }

    const buildSemesterCard = () => {
        setCardContent(
            <>
                <h5 className="title">{item.schoolYear}-{item.semesterNumber}</h5>
                <hr/>
                <div className="button-container">
                    <Row>
                        <Col className="ml-auto" lg="3" md="6" xs="6">
                            <h5>
                                <small>Id</small><br/>
                                {item.semesterId}
                            </h5>
                        </Col>
                        <Col className="ml-auto mr-auto" lg="4" md="6" xs="6">
                            <h5>
                                <small>Courses</small><br/>
                                {item.courses.length}
                            </h5>
                        </Col>
                    </Row>
                </div>
            </>
        )
    }

    return (
        <Card className="card-user">
            <CardBody>
                {cardContent}
            </CardBody>
        </Card>
    )
}
export default InfoCard