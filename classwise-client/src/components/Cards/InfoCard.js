import {Button, Card, CardBody, Col, Row} from "reactstrap";
import {useEffect, useState} from "react";
import StudentCard from "./StudentCard";
import CourseCard from "./CourseCard";
import TeacherCard from "./TeacherCard";
import SemesterCard from "./SemesterCard";

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
                        <Button color="danger" size={"sm"} onClick={() => onDelete()}>Delete</Button>
                    </Col>
                </Row>
                {cardContent}
            </CardBody>
        </Card>
    )
}
export default InfoCard