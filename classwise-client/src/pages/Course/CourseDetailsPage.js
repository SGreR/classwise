import {useParams} from "react-router-dom";
import {Col, Row} from "reactstrap";
import InfoCard from "../../components/Cards/InfoCard";
import GraphCard from "../../components/Cards/GraphCard";
import StripedList from "../../components/List/StripedList";
import {useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";

const CourseDetailsPage = () => {
    const {id} = useParams()
    const[course, setCourses] = useState(null);
    return (
        course == null ?
            (
                <CircularProgress color={"secondary"}/>
            ) : (
                <>
                    <div className="content">
                        <Row>
                            <Col md="4">
                                <InfoCard itemType={"courses"} item={course}/>
                            </Col>
                            <Col md="8">
                                <StripedList itemType={"students"} itemList={course.students}/>
                            </Col>
                        </Row>
                    </div>
                </>
            )
    );
};

export default CourseDetailsPage;
