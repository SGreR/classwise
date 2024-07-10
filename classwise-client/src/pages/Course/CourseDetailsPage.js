import {useParams} from "react-router-dom";
import {Col, Row} from "reactstrap";
import InfoCard from "../../components/Cards/InfoCard";
import GraphCard from "../../components/Cards/GraphCard";
import StripedList from "../../components/List/StripedList";
import {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import axios from "axios";

const CourseDetailsPage = () => {
    const {id} = useParams()
    const[course, setCourse] = useState(null);

    useEffect(() => {
        axios({
            method: 'get',
            url: 'http://localhost:8080/classwise/courses/' + id,
            auth: {
                username: "admin",
                password: "admin"
            },
            headers: {
                'Include-Students' : 'true',
                'Include-Semester' : 'true',
                'Include-Teacher' : 'true'
            }

        }).then(response => setCourse(response.data))
    }, []);

    return (
        <>
            <div className="content">
                {
                    course == null ?
                (
                    <CircularProgress color={"secondary"}/>
                ) : (
                    <Col>
                        <Col md="8">
                            <InfoCard itemType={"courses"} item={course}/>
                        </Col>
                        <StripedList itemType={"students"} itemList={course.students}/>
                    </Col>
                )
                }
            </div>
        </>
    );
};

export default CourseDetailsPage;
