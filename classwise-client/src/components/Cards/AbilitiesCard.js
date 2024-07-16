import {useEffect, useState} from "react";
import {Col, Row} from "reactstrap";
import {capitalize} from "../../utils/utils";

const AbilitiesCard = ({item:initialItem, editing, onItemChange}) => {
    const [item, setItem] = useState(null)

    useEffect(() => {
        setItem(initialItem)
    }, [initialItem]);

    return(
        item &&
        <>
            <div className="button-container">
                <Row>
                    <Col>
                        {item.skillName === "USEOFENGLISH" ?
                            <h5 className="title">Use of English Grades</h5>
                            :
                            <h5 className="title">{capitalize(item.skillName.toLowerCase())} Grades</h5>

                        }

                    </Col>
                </Row>
                <hr/>
                <Row>
                    <Col className="ml-auto" lg="4" md="6" xs="6">
                        <h5>
                            <small>Teacher Grade</small><br/>
                            {item.teacherGrade}%

                        </h5>
                    </Col>
                    <Col className="ml-auto mr-auto" lg="4" md="6" xs="6">
                        <h5>
                            <small>Test Grade</small><br/>
                            {item.testGrade}%
                        </h5>
                    </Col>
                </Row>
                <Row>
                    <Col className="mr-auto ml-auto">
                        <h5>
                            <small>Final Skill Grade</small><br/>
                            {item.averageGrade}%
                        </h5>
                    </Col>
                </Row>
            </div>
        </>
    )
}

export default AbilitiesCard