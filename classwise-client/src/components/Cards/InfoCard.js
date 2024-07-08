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
        console.log(item)
        setCardContent(
            <>
                <h5 className="title">{item.studentName}</h5>
                <hr/>
                <div className="button-container">
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
                                <small>Number of Students</small><br/>
                                {item.students.length}
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