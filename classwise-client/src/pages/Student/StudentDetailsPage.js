import {useParams} from "react-router-dom";
import {Col, Row} from "reactstrap";
import {useEffect, useState} from "react";
import GraphCard from "components/Cards/GraphCard";
import StripedList from "components/List/StripedList";
import InfoCard from "components/Cards/InfoCard";
import CircularProgress from "@mui/material/CircularProgress";
import axios from "axios";

const StudentDetailsPage = () => {
    const {id} = useParams()
    const [student, setStudent] = useState(null)

    useEffect(() => {
        axios({
            method: 'get',
            url: 'http://localhost:8080/classwise/students/' + id,
            auth: {
                username: "admin",
                password: "admin"
            },
            headers: {
                'Include-Courses' : 'true',
            }

        }).then(response => setStudent(response.data))
    }, []);

    return (
        <>
            <div className="content">
                {
                student == null ?
                (
                    <CircularProgress color={"secondary"}/>
                ) : (
                    <Row>
                        <Col md="4">
                            <InfoCard itemType={"students"} item={student}/>
                            <GraphCard data={student.grades}/>
                        </Col>
                        <Col md="8">
                            <StripedList itemType={"courses"} itemList={student.courses}/>
                        </Col>
                    </Row>
                )}
            </div>
        </>
    );
};

export default StudentDetailsPage;
