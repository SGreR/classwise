import {useEffect, useState} from "react";
import {Col, Input, Row} from "reactstrap";

const ClassPerformanceCard = ({item:initialItem, editing, onItemChange, onInvalidGrade}) => {
    const [item, setItem] = useState(null)

    useEffect(() => {
        setItem(initialItem)
    }, [initialItem]);

    useEffect(() => {
        if(item) {
            onInvalidGrade(
                isGradeInvalid(item?.presenceGrade) ||
                isGradeInvalid(item?.homeworkGrade) ||
                isGradeInvalid(item?.participationGrade) ||
                isGradeInvalid(item?.behaviorGrade),
                "classPerformance")
        }
    }, [item, item?.classPerformance]);

    const handleTextChange = (event, fieldName) => {
        const value = event.target.value;
        const updatedItem = {
            ...item,
            [fieldName]: value,
        };
        updatedItem.averageGrade = calculateAverageGrade(updatedItem.presenceGrade, updatedItem.homeworkGrade, updatedItem.participationGrade, updatedItem.behaviorGrade);
        setItem(updatedItem);
        onItemChange(updatedItem);
    }

    const calculateAverageGrade = (presenceGrade, homeworkGrade, participationGrade, behaviorGrade) => {
        const parsedGrades = [
            parseFloat(presenceGrade),
            parseFloat(homeworkGrade),
            parseFloat(participationGrade),
            parseFloat(behaviorGrade)
        ]
        let sum = 0
        parsedGrades.forEach(grade => sum += grade)
        return ((sum / 4) * 20)
    };

    const isGradeInvalid = (grade) => {
        const parsedGrade = parseInt(grade);
        return isNaN(parsedGrade) || parsedGrade < 0 || parsedGrade > 5;
    }

    return(
        item &&
        <>
            <div className="button-container">
                <Row>
                    <Col>
                        <h5 className="title">Class Performance Grades</h5>
                        <h5>
                            <small>Final Class Performance Grade</small><br/>
                            {item.averageGrade}%
                        </h5>
                    </Col>
                </Row>
                <hr/>
                <Row>
                    <Col className="ml-auto" lg="4" md="6" xs="6">
                        <h5>
                            <small>Presence</small><br/>
                            {editing ? (
                                <Input
                                    type={"text"}
                                    value={item.presenceGrade}
                                    onChange={(event) => handleTextChange(event, "presenceGrade")}
                                    invalid={isGradeInvalid(item.presenceGrade)}
                                />
                            ) : (
                                <>
                                    {item.presenceGrade}/5
                                </>
                            )}
                        </h5>
                    </Col>
                    <Col className="ml-auto mr-auto" lg="4" md="6" xs="6">
                        <h5>
                            <small>Homework</small><br/>
                            {editing ? (
                                <Input
                                    type={"text"}
                                    value={item.homeworkGrade}
                                    onChange={(event) => handleTextChange(event, "homeworkGrade")}
                                    invalid={isGradeInvalid(item.homeworkGrade)}
                                />
                            ) : (
                                <>
                                    {item.homeworkGrade}/5
                                </>
                            )}
                        </h5>
                    </Col>
                </Row>
                <Row>
                    <Col className="ml-auto" lg="4" md="6" xs="6">
                        <h5>
                            <small>Participation</small><br/>
                            {editing ? (
                                <Input
                                    type={"text"}
                                    value={item.participationGrade}
                                    onChange={(event) => handleTextChange(event, "participationGrade")}
                                    invalid={isGradeInvalid(item.participationGrade)}
                                />
                            ) : (
                                <>
                                    {item.participationGrade}/5
                                </>
                            )}
                        </h5>
                    </Col>
                    <Col className="ml-auto mr-auto" lg="4" md="6" xs="6">
                        <h5>
                            <small>Behavior</small><br/>
                            {editing ? (
                                <Input
                                    type={"text"}
                                    value={item.behaviorGrade}
                                    onChange={(event) => handleTextChange(event, "behaviorGrade")}
                                    invalid={isGradeInvalid(item.behaviorGrade)}
                                />
                            ) : (
                                <>
                                    {item.behaviorGrade}/5
                                </>
                            )}
                        </h5>
                    </Col>
                </Row>
            </div>
        </>
    )
}

export default ClassPerformanceCard