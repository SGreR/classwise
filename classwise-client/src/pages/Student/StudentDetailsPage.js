import {useParams} from "react-router-dom";
import {Col, Row} from "reactstrap";
import {useState} from "react";
import GraphCard from "components/Cards/GraphCard";
import StripedList from "components/List/StripedList";
import InfoCard from "components/Cards/InfoCard";
import CircularProgress from "@mui/material/CircularProgress";

const StudentDetailsPage = () => {
    const {id} = useParams()
    const [student, setStudent] = useState(null)

    return (
        student == null ?
            (
                <CircularProgress color={"secondary"}/>
            ) : (
                <>
                    <div className="content">
                        <Row>
                            <Col md="4">
                                <InfoCard itemType={"student"} item={student}/>
                                <GraphCard data={student.grades}/>
                            </Col>
                            <Col md="8">
                                <StripedList itemType={"courses"} itemList={student.courses}/>
                            </Col>
                        </Row>
                    </div>
                </>
            )
    );
};

export default StudentDetailsPage;
