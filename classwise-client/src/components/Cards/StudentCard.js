import {Button, Col, Row} from "reactstrap";
import {useState} from "react";

const StudentCard = ({item:initialItem, editing, onItemChange}) => {
    const[item, setItem] = useState(initialItem)


    const handleTextChange = (event, fieldName) => {
        const value = event.target.value;
        setItem((prevItem) => ({
            ...prevItem,
            [fieldName]: value
        }))
        onItemChange({ ...item, [fieldName]: value });
    }

    return (
        <>
            <div className="button-container">
                <Row>
                    <Col>
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

export default StudentCard