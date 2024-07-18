import {Button, Card, CardBody, Col, Row} from "reactstrap";
import {useEffect, useState} from "react";
import StudentCard from "./StudentCard";
import CourseCard from "./CourseCard";
import TeacherCard from "./TeacherCard";
import SemesterCard from "./SemesterCard";
import ModalCard from "../Modal/ModalCard";
import AbilitiesCard from "./AbilitiesCard";
import GradesCard from "./GradesCard";
import SpeakingCard from "./SpeakingCard";
import ClassPerformanceCard from "./ClassPerformanceCard";

const InfoCard = ({itemType, item, onItemChange, onDelete}) => {
    const[cardContent, setCardContent] = useState(null)
    const[editing, setEditing] = useState(false)

    useEffect(() => {
        switch (itemType){
            case "students":
                setCardContent(<StudentCard item={item} editing={editing} onItemChange={onItemChange} />);
                break;
            case "teachers":
                setCardContent(<TeacherCard item={item} editing={editing} onItemChange={onItemChange} />);
                break;
            case "courses":
                setCardContent(<CourseCard item={item} editing={editing} onItemChange={onItemChange} />);
                break;
            case "semesters":
                setCardContent(<SemesterCard item={item} editing={editing} onItemChange={onItemChange}/>)
                break;
            case "abilities":
                setCardContent(<AbilitiesCard item={item} editing={editing} onItemChange={onItemChange}/>)
                break;
            case "grades":
                setCardContent(<GradesCard item={item} editing={editing} onItemChange={onItemChange}/>)
                break;
            case "speaking":
                setCardContent(<SpeakingCard item={item} editing={editing} onItemChange={onItemChange}/>)
                break;
            case "class performance":
                setCardContent(<ClassPerformanceCard item={item} editing={editing} onItemChange={onItemChange}/>)
                break;
            default:
                break;

        }
    }, [itemType, item, editing]);

    return (
        <Card className="text-center">
            <CardBody>
                <Row>
                    <Col className="text-right">
                        <Button color="primary" size={"sm"} onClick={() => setEditing(!editing)}>Edit</Button>
                        {itemType !== "abilities" && itemType !== "speaking" && itemType !== "class performance" && <ModalCard mode="delete" type={itemType} onDeleteConfirmed={onDelete}/>}
                    </Col>
                </Row>
                {cardContent}
            </CardBody>
        </Card>
    )
}
export default InfoCard