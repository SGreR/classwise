import {useParams} from "react-router-dom";
import {Col, Row} from "reactstrap";
import InfoCard from "../../components/Cards/InfoCard";
import StripedList from "../../components/List/StripedList";
import {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import axios from "axios";

const SemesterDetailsPage = () => {
    const {id} = useParams()
    const[semester, setSemester] = useState(null)

    useEffect(() => {
        axios({
            method: 'get',
            url: 'http://localhost:8080/classwise/semesters/' + id,
            auth: {
                username: "admin",
                password: "admin"
            },
            headers: {
                'Include-Courses' : 'true',
            }
        }).then(response => {
            const semester = response.data;

            const updatedCourses = semester.courses.map(course => {
                return {
                    ...course,
                    semester: { ...semester, courses: null }
                };
            });

            setSemester({ ...semester, courses: updatedCourses });
        }).catch(error => {
            console.error("There was an error fetching the semester data!", error);
        });
    }, [id]);

    return (
        <>
            <div className="content">
                {
                    semester == null ?
                    (
                        <CircularProgress color={"secondary"}/>
                    ) : (
                        <Row>
                            <Col md="4">
                                <InfoCard itemType={"semesters"} item={semester}/>
                            </Col>
                            <Col md="8">
                                <StripedList itemType={"courses"} itemList={semester.courses}/>
                            </Col>
                        </Row>
                    )
                }
            </div>
        </>
    );
};

export default SemesterDetailsPage;
