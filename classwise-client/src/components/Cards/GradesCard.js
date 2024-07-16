import {Col, Row} from "reactstrap";
import CircularProgress from "@mui/material/CircularProgress";
import {useEffect, useState} from "react";

const GradesCard = ({item:initialItem, editing, onItemChange}) => {
    const [item, setItem] = useState(null)

    useEffect(() => {
        setItem(initialItem)
    }, [initialItem]);

    return (
        item &&
                <>
                    <div className="button-container">
                        <Row>
                            <Col>
                                <h5 className="title">{item.studentId+"'s grades"}</h5>
                            </Col>
                        </Row>
                        <hr/>
                        <Row>
                            <Col className="ml-auto" lg="3" md="6" xs="6">
                                <h5>
                                    <small>Id</small><br/>
                                    {item.gradesId}

                                </h5>
                            </Col>
                            <Col className="ml-auto mr-auto" lg="4" md="6" xs="6">
                                <h5>
                                    <small>Final Grade</small><br/>
                                    {item.abilities.finalGrade}%
                                </h5>
                            </Col>
                        </Row>
                        <Row>
                            <Col className="mr-auto ml-auto">
                                <h5>
                                    <small>Current Course</small><br/>
                                    Placeholder
                                </h5>
                            </Col>
                        </Row>
                    </div>
                </>
    )
}

export default GradesCard