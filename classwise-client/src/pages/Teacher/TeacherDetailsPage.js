import {useParams} from "react-router-dom";
import {Col, Row} from "reactstrap";
import InfoCard from "../../components/Cards/InfoCard";
import GraphCard from "../../components/Cards/GraphCard";
import StripedList from "../../components/List/StripedList";
import {useEffect, useState} from "react";
import CircularProgress from "@mui/material/CircularProgress";
import axios from "axios";

const TeacherDetailsPage = () => {
    const {id} = useParams()
    const[teacher, setTeacher] = useState(null)

    useEffect(() => {
        axios({
            method: 'get',
            url: 'http://localhost:8080/classwise/teachers/' + id,
            auth: {
                username: "admin",
                password: "admin"
            },
            headers: {
                'Include-Courses' : 'true',
            }

        }).then(response => {
            const teacher = response.data;

            const updatedCourses = teacher.courses.map(course => {
                return {
                    ...course,
                    teacher: { ...teacher, courses: null }
                };
            });

            setTeacher({ ...teacher, courses: updatedCourses });
        }).catch(error => {
            console.error("There was an error fetching the semester data!", error);
        });
    }, [id]);

    return (
            <>
                <div className="content">
                {
                    teacher == null ?
                    (
                        <CircularProgress color={"secondary"}/>
                    ) : (
                        <Row>
                            <Col md="4">
                                <InfoCard itemType={"teachers"} item={teacher}/>
                            </Col>
                            <Col md="8">
                                <StripedList itemType={"courses"} itemList={teacher.courses}/>
                            </Col>
                        </Row>
                    )
                }
                </div>
            </>
    );
};

export default TeacherDetailsPage;
