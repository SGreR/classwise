import {useEffect, useState} from "react";
import {Col, Input, Row} from "reactstrap";
import {capitalize} from "../../utils/utils";

const AbilitiesCard = ({item:initialItem, editing, onItemChange, onInvalidGrade}) => {
    const [item, setItem] = useState(null)

    useEffect(() => {
        setItem(initialItem)
    }, [initialItem]);

    useEffect(() => {
        if(item){
            onInvalidGrade(isGradeInvalid(item?.teacherGrade) || isGradeInvalid(item?.testGrade), item?.skillName?.toLowerCase())
        }
    }, [item?.teacherGrade, item?.testGrade]);

    const handleTextChange = (event, fieldName) => {
        const value = event.target.value;
        const updatedItem = {
            ...item,
            [fieldName]: value,
        };
        updatedItem.averageGrade = calculateAverageGrade(updatedItem.teacherGrade, updatedItem.testGrade);
        setItem(updatedItem);
        onItemChange(updatedItem);
    }

    const calculateAverageGrade = (teacherGrade, testGrade) => {
        const parsedTeacherGrade = parseFloat(teacherGrade);
        const parsedTestGrade = parseFloat(testGrade);
        if (isNaN(parsedTeacherGrade) || isNaN(parsedTestGrade)) return "";
        return ((parsedTeacherGrade + parsedTestGrade) / 2).toFixed(1).toString();
    };

    const isGradeInvalid = (grade) => {
        const parsedGrade = parseFloat(grade);
        return isNaN(parsedGrade) || parsedGrade < 0.0 || parsedGrade > 100.0;
    }

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
                    <Col>
                        {item.skillName === "USEOFENGLISH" ?
                            (
                                <h5>
                                    <small>Final Use of English Grade</small>
                                    <br/>
                                    {item.averageGrade}%
                                </h5>
                            ) : (
                            <h5>
                                <small>Final {capitalize(item.skillName.toLowerCase())} Grade</small>
                                <br/>
                                {item.averageGrade}%
                            </h5>)}
                        </Col>
                </Row>
                <Row>
                    <Col className="ml-auto" lg="4" md="6" xs="6">
                        <h5>
                            <small>Teacher Grade</small><br/>
                            {editing ? (
                                <Input
                                    type={"text"}
                                    value={item.teacherGrade}
                                    onChange={(event) => handleTextChange(event, "teacherGrade")}
                                    invalid={isGradeInvalid(item.teacherGrade)}
                                />
                            ) : (
                                <>
                                    {item.teacherGrade}%
                                </>
                            )}
                        </h5>
                    </Col>
                    <Col className="ml-auto mr-auto" lg="4" md="6" xs="6">
                        <h5>
                            <small>Test Grade</small><br/>
                            {editing ? (
                                <Input
                                    type={"text"}
                                    value={item.testGrade}
                                    onChange={(event) => handleTextChange(event, "testGrade")}
                                    invalid={isGradeInvalid(item.testGrade)}
                                />
                            ) : (
                                <>
                                    {item.testGrade}%
                                </>
                            )}
                        </h5>
                    </Col>
                </Row>
            </div>
        </>
    )
}

export default AbilitiesCard