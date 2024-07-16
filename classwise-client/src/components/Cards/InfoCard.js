import {Button, Card, CardBody, Col, Row} from "reactstrap";
import {useEffect, useState} from "react";
import StudentCard from "./StudentCard";
import CourseCard from "./CourseCard";
import TeacherCard from "./TeacherCard";
import SemesterCard from "./SemesterCard";
import ModalCard from "../Modal/ModalCard";
import AbilitiesCard from "./AbilitiesCard";
import GradesCard from "./GradesCard";

const InfoCard = ({itemType, item: initialItem, onItemChange, onDelete}) => {
    const[item, setItem] = useState(initialItem)
    const[cardContent, setCardContent] = useState(null)
    const[editing, setEditing] = useState(false)

    useEffect(() => {
        switch (itemType){
            case "students":
                setCardContent(<StudentCard item={item} editing={editing} onItemChange={handleItemChange} />);
                break;
            case "teachers":
                setCardContent(<TeacherCard item={item} editing={editing} onItemChange={handleItemChange} />);
                break;
            case "courses":
                setCardContent(<CourseCard item={item} editing={editing} onItemChange={handleItemChange} />);
                break;
            case "semesters":
                setCardContent(<SemesterCard item={item} editing={editing} onItemChange={handleItemChange}/>)
                break;
            case "abilities":
                setCardContent(<AbilitiesCard item={item} editing={editing} onItemChange={handleItemChange}/>)
                break;
            case "grades":
                setCardContent(<GradesCard item={item} editing={editing} onItemChange={handleItemChange}/>)
            default:
                break;

        }
    }, [itemType, item, editing]);


    const handleItemChange = (item) => {
        setItem(item)
        onItemChange(item)
    }

    return (
        <Card className="text-center">
            <CardBody>
                <Row>
                    <Col className="text-right">
                        <Button color="primary" size={"sm"} onClick={() => setEditing(!editing)}>Edit</Button>
                        {itemType !== "abilities" && <ModalCard mode="delete" type={itemType} onDeleteConfirmed={onDelete}/>}
                    </Col>
                </Row>
                {cardContent}
            </CardBody>
        </Card>
    )
}
export default InfoCard