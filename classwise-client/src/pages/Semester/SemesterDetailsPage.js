import {useParams} from "react-router-dom";
import {Col, Row} from "reactstrap";
import InfoCard from "../../components/Cards/InfoCard";
import GraphCard from "../../components/Cards/GraphCard";
import StripedList from "../../components/List/StripedList";
import {useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";

const SemesterDetailsPage = () => {
    const {id} = useParams()
    const[semester, setSemester] = useState(null)
    return (
        semester == null ?
            (
                <CircularProgress color={"secondary"}/>
            ) : (
                <>
                    <div className="content">
                        <Row>
                            <Col md="4">
                                <InfoCard itemType={"semesters"} item={semester}/>
                            </Col>
                            <Col md="8">
                                <StripedList itemType={"courses"} itemList={semester.courses}/>
                            </Col>
                        </Row>
                    </div>
                </>
            )
    );
};

export default SemesterDetailsPage;
