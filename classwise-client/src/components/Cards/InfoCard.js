import {Card, CardBody, Col, Row} from "reactstrap";
import {useEffect, useState} from "react";

const InfoCard = ({itemType, item}) => {
    const[cardContent, setCardContent] = useState(null)

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
    }, [itemType]);

    const buildStudentCard = () => {
        setCardContent(
            <>
                <h5 className="title">{item.studentName}</h5>
                <hr/>
                <div className="button-container">
                    <Row>
                        <Col className="ml-auto" lg="3" md="6" xs="6">
                            <h5>
                                {item.studentId} <br/>
                                <small>Id</small>
                            </h5>
                        </Col>
                        <Col className="ml-auto mr-auto" lg="4" md="6" xs="6">
                            <h5>
                                {item.courses.length} <br/>
                                <small>Courses</small>
                            </h5>
                        </Col>
                        <Col className="mr-auto" lg="3">
                            <h5>
                                {item.courses.find(course => course.active === true).courseName} <br/>
                                <small>Current Course</small>
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
                                {item.teacherId} <br/>
                                <small>Id</small>
                            </h5>
                        </Col>
                        <Col className="ml-auto mr-auto" lg="4" md="6" xs="6">
                            <h5>
                                {item.courses.length} <br/>
                                <small>Courses</small>
                            </h5>
                        </Col>
                        <Col className="mr-auto" lg="3">
                            <h5>
                                {item.courses.find(course => course.active === true).courseName} <br/>
                                <small>Current Course</small>
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
                                {item.courseId} <br/>
                                <small>Id</small>
                            </h5>
                        </Col>
                        <Col className="ml-auto mr-auto" lg="4" md="6" xs="6">
                            <h5>
                                {item.semester.schoolYear}-{item.semester.semesterNumber} <br/>
                                <small>Semester</small>
                            </h5>
                        </Col>
                        <Col className="mr-auto" lg="3">
                            <h5>
                                {item.students.length} <br/>
                                <small>Number of Students</small>
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
                <h5 className="title">{item.semester.schoolYear}-{item.semester.semesterNumber}</h5>
                <hr/>
                <div className="button-container">
                    <Row>
                        <Col className="ml-auto" lg="3" md="6" xs="6">
                            <h5>
                                {item.semesterId} <br/>
                                <small>Id</small>
                            </h5>
                        </Col>
                        <Col className="ml-auto mr-auto" lg="4" md="6" xs="6">
                            <h5>
                                {item.courses.length} <br/>
                                <small>Courses</small>
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