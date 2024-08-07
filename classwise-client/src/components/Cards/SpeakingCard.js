import {useEffect, useState} from "react";
import {Col, Input, Row} from "reactstrap";

const SpeakingCard = ({item:initialItem, editing, onItemChange, onInvalidGrade}) => {
    const [item, setItem] = useState(null)

    useEffect(() => {
        setItem(initialItem)
    }, [initialItem]);

    useEffect(() => {
        if(item) {
            onInvalidGrade(
                isGradeInvalid(item?.productionAndFluencyGrade) ||
                isGradeInvalid(item?.spokenInteractionGrade) ||
                isGradeInvalid(item?.languageRangeGrade) ||
                isGradeInvalid(item?.accuracyGrade) ||
                isGradeInvalid(item?.languageUse),
                "speaking")
        }
    }, [item, item?.speaking]);

    const handleTextChange = (event, fieldName) => {
        const value = event.target.value;
        const updatedItem = {
            ...item,
            [fieldName]: value,
        };
        updatedItem.averageGrade = calculateAverageGrade(updatedItem.productionAndFluencyGrade, updatedItem.spokenInteractionGrade, updatedItem.languageRangeGrade, updatedItem.accuracyGrade, updatedItem.languageUse);
        setItem(updatedItem);
        onItemChange(updatedItem);
    }

    const calculateAverageGrade = (productionAndFluencyGrade, spokenInteractionGrade, languageRangeGrade, accuracyGrade, languageUse) => {
            const parsedGrades = [
            parseFloat(productionAndFluencyGrade),
            parseFloat(spokenInteractionGrade),
            parseFloat(languageRangeGrade),
            parseFloat(accuracyGrade)
            ]
        const parsedLanguageUse = parseFloat(languageUse)
        let sum = 0
        parsedGrades.forEach(grade => sum += grade)
        const average = ((sum / 4) * 20)
        return average - (average * (5 - parsedLanguageUse) * 0.1);
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
                        <h5 className="title">Speaking Grades</h5>
                        <h5>
                            <small>Final Speaking Grade</small><br/>
                            {item.averageGrade}%
                        </h5>
                    </Col>
                </Row>
                <hr/>
                <Row>
                    <Col className="ml-auto" lg="4" md="6" xs="6">
                        <h5>
                            <small>Production and Fluency</small><br/>
                            {editing ? (
                                <Input
                                    type={"text"}
                                    value={item.productionAndFluencyGrade}
                                    onChange={(event) => handleTextChange(event, "productionAndFluencyGrade")}
                                    invalid={isGradeInvalid(item.productionAndFluencyGrade)}
                                />
                            ) : (
                                <>
                                    {item.productionAndFluencyGrade}/5
                                </>
                            )}
                        </h5>
                    </Col>
                    <Col className="ml-auto mr-auto" lg="4" md="6" xs="6">
                        <h5>
                            <small>Spoken Interaction</small><br/>
                            {editing ? (
                                <Input
                                    type={"text"}
                                    value={item.spokenInteractionGrade}
                                    onChange={(event) => handleTextChange(event, "spokenInteractionGrade")}
                                    invalid={isGradeInvalid(item.spokenInteractionGrade)}
                                />
                            ) : (
                                <>
                                    {item.spokenInteractionGrade}/5
                                </>
                            )}
                        </h5>
                    </Col>
                </Row>
                <Row>
                    <Col className="ml-auto" lg="4" md="6" xs="6">
                        <h5>
                            <small>Language Range</small><br/>
                            {editing ? (
                                <Input
                                    type={"text"}
                                    value={item.languageRangeGrade}
                                    onChange={(event) => handleTextChange(event, "languageRangeGrade")}
                                    invalid={isGradeInvalid(item.languageRangeGrade)}

                                />
                            ) : (
                                <>
                                    {item.languageRangeGrade}/5
                                </>
                            )}
                        </h5>
                    </Col>
                    <Col className="ml-auto mr-auto" lg="4" md="6" xs="6">
                        <h5>
                            <small>Accuracy</small><br/>
                            {editing ? (
                                <Input
                                    type={"text"}
                                    value={item.accuracyGrade}
                                    onChange={(event) => handleTextChange(event, "accuracyGrade")}
                                    invalid={isGradeInvalid(item.accuracyGrade)}
                                />
                            ) : (
                                <>
                                    {item.accuracyGrade}/5
                                </>
                            )}
                        </h5>
                    </Col>
                    <Col className="mr-auto ml-auto">
                        <h5>
                            <small>Language Use</small><br/>
                            {editing ? (
                                <Input
                                    type={"text"}
                                    value={item.languageUse}
                                    onChange={(event) => handleTextChange(event, "languageUse")}
                                    invalid={isGradeInvalid(item.languageUse)}
                                />
                            ) : (
                                <>{item.languageUse}/5</>
                            )}
                        </h5>
                    </Col>
                </Row>
            </div>
        </>
    )
}

export default SpeakingCard;