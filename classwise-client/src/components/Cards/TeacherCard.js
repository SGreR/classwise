import {useState} from "react";
import {Col, Row} from "reactstrap";

const TeacherCard = ({item:initialItem, editing, onItemChange}) => {
    const [item, setItem] = useState(initialItem)

    const handleTextChange = (event, fieldName) => {
        const value = event.target.value;
        setItem((prevItem) => ({
            ...prevItem,
            [fieldName]: value
        }))
        onItemChange({ ...item, [fieldName]: value });
    }

    return(
        <>
            <div className="button-container">
                <Row>
                    <Col>
                        {editing ?
                            (
                                <input type={"text"} value={item.teacherName} onChange={(event) => handleTextChange(event, "teacherName")}  />
                            ) : (
                                <h5 className="title">{item.teacherName}</h5>
                            )}
                    </Col>
                </Row>
                <hr/>
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
                            {item.courses.find(course => course.active === true)?.courseName || "No active course found"}
                        </h5>
                    </Col>
                </Row>
            </div>
        </>
    )

}

export default TeacherCard