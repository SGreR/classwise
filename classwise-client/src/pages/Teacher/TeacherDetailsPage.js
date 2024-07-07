import {useParams} from "react-router-dom";
import {Col, Row} from "reactstrap";
import InfoCard from "../../components/Cards/InfoCard";
import GraphCard from "../../components/Cards/GraphCard";
import StripedList from "../../components/List/StripedList";
import {useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";

const TeacherDetailsPage = () => {
    const {id} = useParams()
    const[teacher, setTeacher] = useState(null)
    return (
        teacher == null ?
            (
                <CircularProgress color={"secondary"}/>
            ) : (
                <>
                    <div className="content">
                        <Row>
                            <Col md="4">
                                <InfoCard itemType={"teachers"} item={teacher}/>
                            </Col>
                            <Col md="8">
                                <StripedList itemType={"courses"} itemList={teacher.courses}/>
                            </Col>
                        </Row>
                    </div>
                </>
            )
    );
};

export default TeacherDetailsPage;
